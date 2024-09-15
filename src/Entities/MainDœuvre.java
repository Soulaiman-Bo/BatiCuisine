package Entities;

import Enums.TypeComposant;

import java.util.UUID;

public class MainDœuvre extends Composants {
    private Integer id;
    private TypeComposant typeComposant;
    private Double tauxHoraire;
    private Double heuresTravail;
    private Double productiviteOuvrier;

    public MainDœuvre(String nom, Double tauxTVA, Integer id, TypeComposant typeComposant, Double tauxHoraire, Double heuresTravail, Double productiviteOuvrier) {
        super(nom, tauxTVA);
        this.id = id;
        this.typeComposant = typeComposant;
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
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

    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public Double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(Double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public Double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(Double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }
}
