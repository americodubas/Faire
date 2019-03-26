package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class Shipment {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "order_id")
    private String orderId;
    @XmlElement(name = "maker_cost_cents")
    private Integer makerCostCents;
    @XmlElement(name = "carrier")
    private String carrier;
    @XmlElement(name = "tracking_code")
    private String trackingCode;
    @XmlElement(name = "created_at")
    private String createdAt;
    @XmlElement(name = "updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getMakerCostCents() {
        return makerCostCents;
    }

    public void setMakerCostCents(Integer makerCostCents) {
        this.makerCostCents = makerCostCents;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
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
}
