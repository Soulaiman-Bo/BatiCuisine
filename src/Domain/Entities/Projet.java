package Domain.Entities;

import Domain.Enums.EtatProject;

import java.util.ArrayList;
import java.util.List;


public class Projet {
    private Integer id;
    private String projectName;
    private Double profit;
    private Double totalCost;
    private Double discount;
    private EtatProject projectStatus;
    private Client client;
    private List<Composants> composants;

    public Projet(Integer id, String projectName, Double profit, Double totalCost, Double discount, EtatProject projectStatus) {
        this.id = id;
        this.projectName = projectName;
        this.profit = profit;
        this.totalCost = totalCost;
        this.discount = discount;
        this.projectStatus = projectStatus;
        this.client = new Client();
        this.composants = new ArrayList<>();
    }

    public Projet() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Composants> getComposants() {
        return composants;
    }

    public void setComposants(List<Composants> composants) {
        this.composants = composants;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
