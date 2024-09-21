package Domain.Entities;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isProfessional;
    private List<Projet> projets;

    public Client(Integer id, String name, String address, String phoneNumber, Boolean isProfessional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isProfessional = isProfessional;
        this.projets = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public void setProfessional(Boolean professional) {
        isProfessional = professional;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
}
