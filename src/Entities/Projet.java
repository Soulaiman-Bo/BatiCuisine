package Entities;

import Enums.EtatProject;


public class Projet {
    private Integer id;
    private String projectName;
    private Double profit;
    private Double totalCost;
    private EtatProject projectStatus;

    public Projet(Integer id, String projectName, Double profit, Double totalCost, EtatProject projectStatus) {
        this.id = id;
        this.projectName = projectName;
        this.profit = profit;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public EtatProject getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(EtatProject projectStatus) {
        this.projectStatus = projectStatus;
    }
}
