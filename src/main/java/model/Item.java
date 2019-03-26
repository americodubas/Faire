package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "created_at")
    private String createdAt;
    @XmlElement(name = "updated_at")
    private String updatedAt;
    @XmlElement(name = "order_id")
    private String orderId;
    @XmlElement(name = "product_id")
    private String productId;
    @XmlElement(name = "product_option_id")
    private String productOptionId;
    @XmlElement(name = "quantity")
    private Integer quantity;
    @XmlElement(name = "sku")
    private String sku;
    @XmlElement(name = "price_cents")
    private Integer priceCents;
    @XmlElement(name = "product_name")
    private String productName;
    @XmlElement(name = "product_option_name")
    private String productOptionName;
    @XmlElement(name = "includes_tester")
    private Boolean includesTester;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductOptionName() {
        return productOptionName;
    }

    public void setProductOptionName(String productOptionName) {
        this.productOptionName = productOptionName;
    }

    public Boolean getIncludesTester() {
        return includesTester;
    }

    public void setIncludesTester(Boolean includesTester) {
        this.includesTester = includesTester;
    }
}
