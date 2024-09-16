package Entities;

import Enums.TypeComposant;


public class Materiaux extends Composants {
    private Integer id;
    private TypeComposant typeComposant;
    private Double unitCost;
    private Double quantity;
    private Double transportCost;
    private Double qualityCoefficient;

    public Materiaux(String name, Double taxRate, Integer id, TypeComposant typeComposant, Double unitCost, Double quantity, Double transportCost, Double qualityCoefficient) {
        super(name, taxRate);
        this.id = id;
        this.typeComposant = typeComposant;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(Double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }
}
