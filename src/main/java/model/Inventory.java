package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Inventory {

    @XmlElement(name = "sku")
    private String sku;
    @XmlElement(name = "current_quantity")
    private Integer currentQuantity;
    @XmlElement(name = "discontinued")
    private Boolean discontinued;
    @XmlElement(name = "backordered_until")
    private String backorderedUntil;

    public Inventory(String sku, Integer currentQuantity) {
        this.sku = sku;
        this.currentQuantity = currentQuantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getBackorderedUntil() {
        return backorderedUntil;
    }

    public void setBackorderedUntil(String backorderedUntil) {
        this.backorderedUntil = backorderedUntil;
    }
}
