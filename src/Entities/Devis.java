package Entities;

import java.time.LocalDate;

public class Devis {
    private Integer id;
    private Double estimatedPrice;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private Boolean accepted;
    private Integer project_id;

    public Devis(Integer id, Double estimatedPrice, LocalDate issueDate, LocalDate validityDate, Boolean accepted, Integer project) {
        this.id = id;
        this.estimatedPrice = estimatedPrice;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.project_id = project;
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

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }
}
