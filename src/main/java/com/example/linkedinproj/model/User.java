package com.example.linkedinproj.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String id;
    private String name;
    private String dateOfBirth;
    private String universityLocation;
    private String field;
    private String workplace;
    private List<String> specialties = new ArrayList<>();
    private List<String> connectionId = new ArrayList<>();
    private Image image;


    public User(String id) {
        this.id = id;

    }


    // getter and setter

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public List<String> getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(List<String> connectionId) {
        this.connectionId = connectionId;
    }
    public void setAll(String id,String name,String dateOfBirth,String universityLocation,String field,String workplace,List<String> specialties,List<String> connectionId){
        this.setSpecialties(specialties);
        this.setField(field);
        this.setName(name);
        this.setUniversityLocation(universityLocation);
        this.setDateOfBirth(dateOfBirth);
        this.setConnectionId(connectionId);
        this.setWorkplace(workplace);
    }


    @Override
    public String toString(){
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", universityLocation='" + universityLocation + '\'' +
                ", field='" + field + '\'' +
                ", workplace='" + workplace + '\'' +
                ", specialties=" + specialties.toString() + "\n" +
                ", connectionId=" + connectionId.toString() +
                '}';
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
