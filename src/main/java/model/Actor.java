package main.java.model;

import java.util.ArrayList;

public class Actor {

    private String name;
    private String surname;
    private long dateOfBirth;
    private ArrayList<Product> listOfVideos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Product> getListOfVideos() {
        return listOfVideos;
    }

    public void setListOfVideos(ArrayList<Product> listOfVideos) {
        this.listOfVideos = listOfVideos;
    }
}
