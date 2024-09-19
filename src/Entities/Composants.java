package Entities;

import Enums.TypeComposant;

public class Composants {
    private String name;
    private Double taxRate;
    private TypeComposant componentType;
    private Projet projet;

    public Composants(String name, Double taxRate, TypeComposant componentType) {
        this.name = name;
        this.taxRate = taxRate;
        this.componentType = componentType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public TypeComposant getComponentType() {
        return componentType;
    }

    public void setComponentType(TypeComposant componentType) {
        this.componentType = componentType;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
