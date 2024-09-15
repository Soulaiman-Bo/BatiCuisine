package Entities;

public class Composants {
    private String nom;
    private Double tauxTVA;

    public Composants(String nom, Double tauxTVA) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
}
