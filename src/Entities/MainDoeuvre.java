package Entities;

import Enums.TypeComposant;


public class MainDoeuvre extends Composants {
    private Integer id;
    private Double hourlyRate;
    private Double workHoursCount;
    private Double productivityRate;

    public MainDoeuvre(String name, Double taxRate, TypeComposant componentType, Integer id, Double hourlyRate, Double workHoursCount, Double productivityRate) {
        super(name, taxRate, componentType);
        this.id = id;
        this.hourlyRate = hourlyRate;
        this.workHoursCount = workHoursCount;
        this.productivityRate = productivityRate;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getWorkHoursCount() {
        return workHoursCount;
    }

    public void setWorkHoursCount(Double workHoursCount) {
        this.workHoursCount = workHoursCount;
    }

    public Double getProductivityRate() {
        return productivityRate;
    }

    public void setProductivityRate(Double productivityRate) {
        this.productivityRate = productivityRate;
    }
}
