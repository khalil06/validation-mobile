package com.company.myapp.Entities;


import java.util.Date;
import com.company.myapp.utils.*;

public class Reservation {

    private int id;
    private Client client;
    private Vol vol;
    private String type;
    private String classe;
    private float finalPrice;

    public Reservation() {}

    public Reservation(int id, Client client, Vol vol, String type, String classe, float finalPrice) {
        this.id = id;
        this.client = client;
        this.vol = vol;
        this.type = type;
        this.classe = classe;
        this.finalPrice = finalPrice;
    }

    public Reservation(Client client, Vol vol, String type, String classe, float finalPrice) {
        this.client = client;
        this.vol = vol;
        this.type = type;
        this.classe = classe;
        this.finalPrice = finalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }



}
