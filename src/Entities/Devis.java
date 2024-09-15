package Entities;

import java.time.LocalDate;
import java.util.UUID;

public class Devis {
    private Integer id;
    private Double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private Boolean accepte;

    public Devis(Boolean accepte, LocalDate dateValidite, LocalDate dateEmission, Double montantEstime, Integer id) {
        this.accepte = accepte;
        this.dateValidite = dateValidite;
        this.dateEmission = dateEmission;
        this.montantEstime = montantEstime;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(Double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public Boolean getAccepte() {
        return accepte;
    }

    public void setAccepte(Boolean accepte) {
        this.accepte = accepte;
    }
}
