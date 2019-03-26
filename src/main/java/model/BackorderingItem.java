package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BackorderingItem {

    @XmlElement(name = "available_quantity")
    private int availableQuantity;
    @XmlElement(name = "discontinued")
    private boolean discontinued;

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public BackorderingItem(int availableQuantity, boolean discontinued) {
        this.availableQuantity = availableQuantity;
        this.discontinued = discontinued;
    }
}
