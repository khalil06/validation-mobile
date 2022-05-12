/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.Entities;

/**
 *
 * @author Fayechi
 */
public class Activite {
    private int id_activite;
    private String nom_activite;
    private String adresse;
    private float prix;
    private String type_activite;
    private String pays;
    private String imgSrc;




    public Activite(){

    }
    public Activite(int id_activite){
        this.id_activite=id_activite;
    }
    public Activite(String type_activite){
        this.type_activite=type_activite;
    }

    public Activite(String nom_activite,String type_activite,float prix, String adresse, String pays, String imgSrc) {
        this.nom_activite = nom_activite;
        this.adresse = adresse;
        this.type_activite = type_activite;
        this.prix = prix;
        this.pays = pays;
        this.imgSrc=imgSrc;
    }

    public Activite(int id_activite, String nom_activite,String type_activite,float prix, String adresse,  String pays,String imgSrc) {
        this.id_activite = id_activite;
        this.nom_activite = nom_activite;
        this.adresse = adresse;
        this.prix = prix;
        this.type_activite = type_activite;
        this.pays = pays;
        this.imgSrc=imgSrc;
    }
    public Activite(String nom_activite,String type_activite,float prix, String adresse,  String pays) {
        this.nom_activite = nom_activite;
        this.adresse = adresse;
        this.prix = prix;
        this.type_activite = type_activite;
        this.pays = pays;
    }


    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }



    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * @return the id_activite
     */
    public int getId_activite() {
        return id_activite;
    }

    /**
     * @param id_activite the id_activite to set
     */
    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }

    /**
     * @return the nom_activite
     */
    public String getNom_activite() {
        return nom_activite;
    }

    /**
     * @param nom_activite the nom_activite to set
     */
    public void setNom_activite(String nom_activite) {
        this.nom_activite= nom_activite;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getType_activite() {
        return type_activite;
    }

    public void setType_activite(String type_activite) {
        this.type_activite = type_activite;
    }

    @Override
    public String toString() {
        return "Activité{" + "id_etablissement=" + id_activite + ", nom_activite=" + nom_activite + ","+type_activite+", prix ="+prix+",   adresse=" + adresse + " pays = "+pays+ " path = "+imgSrc+"}'";
    }
    
    
}
