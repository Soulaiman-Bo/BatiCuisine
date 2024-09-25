package Domain.Entities;

import java.time.LocalDate;

public class Devis {
    private Integer id;
    private Double estimatedPrice;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private Boolean accepted;
    private Projet projet;

    public Devis(Integer id, Double estimatedPrice, LocalDate issueDate, LocalDate validityDate, Boolean accepted, Projet projet) {
        this.id = id;
        this.estimatedPrice = estimatedPrice;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.projet = projet;
    }

    public Devis() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
