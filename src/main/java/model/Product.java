package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Product {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "brand_id")
    private String brandId;
    @XmlElement(name = "short_description")
    private String shortDescription;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "wholesale_price_cents")
    private Integer wholesalePriceCents;
    @XmlElement(name = "retail_price_cents")
    private Integer retailPriceCents;
    @XmlElement(name = "active")
    private Boolean active;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "unit_multiplier")
    private Integer unitMultiplier;
    @XmlElement(name = "options")
    private List<Option> options = null;
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWholesalePriceCents() {
        return wholesalePriceCents;
    }

    public void setWholesalePriceCents(Integer wholesalePriceCents) {
        this.wholesalePriceCents = wholesalePriceCents;
    }

    public Integer getRetailPriceCents() {
        return retailPriceCents;
    }

    public void setRetailPriceCents(Integer retailPriceCents) {
        this.retailPriceCents = retailPriceCents;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(Integer unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
