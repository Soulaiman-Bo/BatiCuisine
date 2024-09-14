package Entities;

import Enums.TypeComposant;

import java.util.UUID;

public class MainDœuvre {
    private UUID id;
    private String nom;
    private TypeComposant typeComposant;
    private Double tauxTVA;
    private Double tauxHoraire;
    private Double heuresTravail;
    private Double productiviteOuvrier;

    public MainDœuvre(UUID id, String nom, TypeComposant typeComposant, Double tauxTVA, Double tauxHoraire, Double heuresTravail, Double productiviteOuvrier) {
        this.id = id;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public Double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
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
