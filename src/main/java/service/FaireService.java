package service;

import model.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static util.FaireKeys.*;

public class FaireService {


    public List<Product> getAllProductsFromGivenBrand() {
        //recommend using Tea Drops
        String GIVEN_BRAND = "b_d2481b88";
        String PRODUCT_PATH = "products";
        WebTarget webTarget = ClientBuilder.newClient().target(API_URL).path(PRODUCT_PATH);
        ProductPage productPage = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(API_KEY_NAME, API_KEY_VALUE)
                .get(ProductPage.class);
        List<Product> products = productPage.getProducts();
        while (productPage.getLimit() == productPage.getProducts().size()) {
            productPage = webTarget
                    .queryParam(PAGE_PARAMETER, productPage.getPage() + 1)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(API_KEY_NAME, API_KEY_VALUE)
                    .get(ProductPage.class);
            products.addAll(productPage.getProducts());
        }
        return products
                .stream()
                .filter(product -> product.getBrandId().equals(GIVEN_BRAND))
                .collect(Collectors.toList());
    }

    private Map<String, List<Option>> getProductsActiveOptionsMap(List<Product> products) {
        return products
                .stream()
                .collect(Collectors.toMap(
                        Product::getId,
                    product -> product.getOptions()
                        .stream()
                        .filter(Option::isActive)
                        .collect(Collectors.toList())));
    }

    public List<Order> getAllOrders() {
        String ORDER_PATH = "orders";
        WebTarget webTarget = ClientBuilder.newClient().target(API_URL).path(ORDER_PATH);
        OrderPage orderPage = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(API_KEY_NAME, API_KEY_VALUE)
                .get(OrderPage.class);
        List<Order> orders = orderPage.getOrders();
        while (orderPage.getLimit() == orderPage.getOrders().size()) {
            orderPage = webTarget
                    .queryParam(PAGE_PARAMETER, orderPage.getPage() + 1)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(API_KEY_NAME, API_KEY_VALUE)
                    .get(OrderPage.class);
            orders.addAll(orderPage.getOrders());
        }
        return orders;
    }

    public void printStatistics(List<Order> orders) {
        Order highestOrderPrice = null;
        int highestTotalPrice = 0;
        Map<String, Integer> productSellingCountMap = new HashMap<>();
        Map<String, Integer> stateOrderCountMap = new HashMap<>();
        for (Order order: orders) {
            int totalPrice = 0;
            for (Item item: order.getItems()) {
                totalPrice += item.getPriceCents();

                if (productSellingCountMap.containsKey(item.getProductId())) {
                    productSellingCountMap.put(
                            item.getProductId(),
                            productSellingCountMap.get(item.getProductId()) +1);
                } else {
                    productSellingCountMap.put(item.getProductId(), 1);
                }

            }

            if (stateOrderCountMap.containsKey(order.getAddress().getState())) {
                stateOrderCountMap.put(
                        order.getAddress().getState(),
                        stateOrderCountMap.get(order.getAddress().getState()) + 1);
            } else {
                stateOrderCountMap.put(order.getAddress().getState(), 1);
            }

            if (totalPrice > highestTotalPrice) {
                highestTotalPrice = totalPrice;
                highestOrderPrice = order;
            }
        }

        System.out.format("Largest order by dollar id:%s value:%s \n", highestOrderPrice.getId(), highestTotalPrice);

        int bestSellingProduct = 0, worstSellingProduct = 999;
        String bestSellingProductId = null, worstSellingProductId = null;
        for (String id: productSellingCountMap.keySet()) {
            if (productSellingCountMap.get(id) > bestSellingProduct) {
                bestSellingProduct = productSellingCountMap.get(id);
                bestSellingProductId = id;
            }
            if (productSellingCountMap.get(id) < worstSellingProduct) {
                worstSellingProduct = productSellingCountMap.get(id);
                worstSellingProductId = id;
            }
        }

        System.out.format("Best selling product id:%s \n", bestSellingProductId);
        System.out.format("Worst selling product id:%s \n", worstSellingProductId);

        int stateWithMostOrders = 0, stateWithLessOrders = 999;
        String stateWithMostOrdersId = null, stateWithLessOrdersId = null;
        for (String id: stateOrderCountMap.keySet()) {
            if (stateOrderCountMap.get(id) > stateWithMostOrders) {
                stateWithMostOrders = stateOrderCountMap.get(id);
                stateWithMostOrdersId = id;
            }
            if (stateOrderCountMap.get(id) < stateWithLessOrders) {
                stateWithLessOrders = stateOrderCountMap.get(id);
                stateWithLessOrdersId = id;
            }
        }

        System.out.format("State with most orders %s \n", stateWithMostOrdersId);
        System.out.format("State with less order %s \n", stateWithLessOrdersId);

    }

    public void processNewOrders(List<Order> orders, List<Product> products) {
        Map<String, List<Option>> productOptionsMap = getProductsActiveOptionsMap(products);
        filterNewOrders(orders).forEach(order -> processOrder(order, productOptionsMap));
    }

    private void processOrder(Order order, Map<String, List<Option>> productOptionsMap) {
        int countItemsAvailable = 0;
        List<Option> updateOptionsQuantity = new ArrayList<>();
        for(Item item : order.getItems()) {
            List<Option> options = productOptionsMap.get(item.getProductId());
            for (Option option: options) {
                if (item.getQuantity() <= option.getAvailableQuantity()) {
                    option.setAvailableQuantity(option.getAvailableQuantity() - item.getQuantity());
                    updateOptionsQuantity.add(option);
                    countItemsAvailable++;
                    break;
                }
            }
        }
        if (countItemsAvailable == order.getItems().size()) {
            acceptOrder(order);
            updateInventory(updateOptionsQuantity);
        } else {
            backOrderItems(order);
        }
    }

    private void backOrderItems(Order order) {
        String BACK_ORDER_PATH = "orders/%s/items/availability";
        ClientBuilder
                .newClient()
                .target(API_URL)
                .path(String.format(BACK_ORDER_PATH, order.getId()))
                .request(MediaType.APPLICATION_JSON_TYPE).header(API_KEY_NAME, API_KEY_VALUE)
                .post(Entity.entity(convertOrderItemsForBackordering(order.getItems()), MediaType.APPLICATION_JSON));
    }

    private List<BackorderingItem> convertOrderItemsForBackordering(List<Item> items) {
        return items
                .stream()
                .map(item -> new BackorderingItem(item.getQuantity(), true))
                .collect(Collectors.toList());
    }

    private void updateInventory(List<Option> updateOptionsQuantity) {
        String UPDATE_INVENTORY_PATH = "products/options/inventory-levels";
        ClientBuilder
                .newClient()
                .target(API_URL)
                .path(UPDATE_INVENTORY_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE).header(API_KEY_NAME, API_KEY_VALUE)
                .post(Entity.entity(convertOptionToInventory(updateOptionsQuantity), MediaType.APPLICATION_JSON));
    }

    private List<Inventory> convertOptionToInventory(List<Option> updateOptionsQuantity) {
        return updateOptionsQuantity
                .stream()
                .map(option -> new Inventory(option.getSku(), option.getAvailableQuantity()))
                .collect(Collectors.toList());
    }

    private void acceptOrder(Order order) {
        String ACCEPT_ORDER_PATH = "orders/%s/processing";
        ClientBuilder
                .newClient()
                .target(API_URL)
                .path(String.format(ACCEPT_ORDER_PATH, order.getId()))
                .request(MediaType.APPLICATION_JSON_TYPE).header(API_KEY_NAME, API_KEY_VALUE)
                .put(Entity.text(""));
    }

    private List<Order> filterNewOrders(List<Order> orders) {
        return orders.stream().filter(order -> order.getState() == OrderState.NEW).collect(Collectors.toList());
    }
}
