package Entities;

import Enums.TypeComposant;


public class Matériaux extends Composants {
    private Integer id;
    private TypeComposant typeComposant;
    private Double coutUnitaire;
    private Double quantite;
    private Double coutTransport;
    private Double coefficientQualite;

    public Matériaux(String nom, Double tauxTVA, Integer id, TypeComposant typeComposant, Double coutUnitaire, Double quantite, Double coutTransport, Double coefficientQualite) {
        super(nom, tauxTVA);
        this.id = id;
        this.typeComposant = typeComposant;
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
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

    public Double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(Double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(Double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public Double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(Double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }
}
