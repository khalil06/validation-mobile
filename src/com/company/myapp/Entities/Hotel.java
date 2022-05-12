/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.Entities;



/**
 *
 * @author dorsaf
 */
public class Hotel {
    public static String pathfile; 
    public static String filename="";
    
    private int idHotel;
    private String nomHotel;
    private int nbrEtoiles;
    private int nbrChambres;
    private String adresse;
    private String pays;
    private int tel;
    private String email;
    private String image;



    public Hotel() {
    }

    public String getImage() {
        return image;
    }


    
    public void setImage(String image) {
        this.image = image;
    }

    
    public int getIdHotel() {
        return idHotel;
    }
    
    public String getNomHotel() {
        return nomHotel;
    }

    public String getPays() {
        return pays;
    }

    public int getNbrEtoiles() {
        return nbrEtoiles;
    }

    public int getNbrChambres() {
        return nbrChambres;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }
    

    public void setNomHotel(String nom_hotel) {
        this.nomHotel = nom_hotel;
    }

    public void setNbrEtoiles(int nbr_etoiles) {
        this.nbrEtoiles = nbr_etoiles;
    }

    public void setNbrChambres(int nbr_chambres) {
        this.nbrChambres = nbr_chambres;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id_hotel=" + idHotel + ", nom_hotel=" + nomHotel+ ", nbr_etoiles=" + nbrEtoiles + ", nbr_chambres=" + nbrChambres + ", adresse=" + adresse + ", pays=" + pays + ", tel=" + tel + ", email=" + email + ", image=" + image + '}';
    }

    public Hotel(int idHotel) {
        this.idHotel = idHotel;
    }
    

    public Hotel(String nom_hotel, int nbr_etoiles, int nbr_chambres, String adresse, String pays, int tel, String email, String image) {
        this.nomHotel = nom_hotel;
        this.nbrEtoiles = nbr_etoiles;
        this.nbrChambres = nbr_chambres;
        this.adresse = adresse;
        this.pays = pays;
        this.tel = tel;
        this.email = email;
        this.image = image;
    }

    public Hotel(int id_hotel, String nom_hotel, int nbr_etoiles, int nbr_chambres, String adresse, String pays, int tel, String email, String image) {
        this.idHotel = id_hotel;
        this.nomHotel = nom_hotel;
        this.nbrEtoiles = nbr_etoiles;
        this.nbrChambres = nbr_chambres;
        this.adresse = adresse;
        this.pays = pays;
        this.tel = tel;
        this.email = email;
        this.image = image;
    }
    public Hotel(int id_hotel, String nom_hotel, int nbr_etoiles, int nbr_chambres, String adresse, String pays, int tel, String email) {
        this.idHotel = id_hotel;
        this.nomHotel = nom_hotel;
        this.nbrEtoiles = nbr_etoiles;
        this.nbrChambres = nbr_chambres;
        this.adresse = adresse;
        this.pays = pays;
        this.tel = tel;
        this.email = email;
    }

    public Hotel(String nom_hotel, int nbr_etoiles, int nbr_chambres, String adresse, String pays, int tel, String email) {
        this.nomHotel = nom_hotel;
        this.nbrEtoiles = nbr_etoiles;
        this.nbrChambres = nbr_chambres;
        this.adresse = adresse;
        this.pays = pays;
        this.tel = tel;
        this.email = email;
    }

    public void setIdHotel(int id_hotel) {
        this.idHotel = id_hotel;
    }


   

}
