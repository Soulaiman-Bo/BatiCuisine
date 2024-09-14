package Entities;

import java.util.UUID;

public class Client {
    private UUID id;
    private String nom;
    private String adresse;
    private String telephone;
    private Boolean estProfessionnel;

    public Client(UUID id, String nom, String adresse, String telephone, Boolean estProfessionnel) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.estProfessionnel = estProfessionnel;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getEstProfessionnel() {
        return estProfessionnel;
    }

    public void setEstProfessionnel(Boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }
}
