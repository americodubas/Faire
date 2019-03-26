import model.Order;
import model.Product;
import service.FaireService;

import java.util.List;

import static util.FaireKeys.API_KEY_VALUE;

class Challenge {

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            //SINGLE COMMAND PARAMETER TO BE SENT IN ALL REQUESTS ON THE HEADER
            API_KEY_VALUE = args[0];
        }

        FaireService faireService = new FaireService();
        List<Product> products = faireService.getAllProductsFromGivenBrand();
        List<Order> orders = faireService.getAllOrders();
        faireService.processNewOrders(orders, products);
        faireService.printStatistics(orders);

    }

}
