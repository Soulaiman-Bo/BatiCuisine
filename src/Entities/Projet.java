package Entities;

import Enums.EtatProject;


public class Projet {
    private Integer id;
    private String nomProjet;
    private Double margeBeneficiaire;
    private Double coutTotal;
    private EtatProject etatProjet;

    public Projet(Integer id, String nomProjet, Double margeBeneficiaire, Double coutTotal, EtatProject etatProjet) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(Double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProject getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProject etatProjet) {
        this.etatProjet = etatProjet;
    }

    public Double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(Double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }
}
