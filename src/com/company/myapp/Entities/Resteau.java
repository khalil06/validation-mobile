/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.Entities;



/**
 *
 * @author hp
 */
public class Resteau {
      private String adressr;
    private String typer;
    private String nomr; 
    private String paysr; 
    private String telr; 
    private String imgr;
    private int idr;

public Resteau (){

}
    public Resteau(String typer, String nomr, String adressr, String paysr, String telr,int idr) {
        
        this.idr = idr;
        this.adressr = adressr;
        this.paysr = paysr;
        this.telr = telr;
        this.typer = typer;
        this.nomr = nomr;
    }

   
 

    public String getImgR() {
        return imgr;
    }

    public void setImgR(String imgr) {
        this.imgr = imgr;
    }

    

    public Resteau( String typer, String nomr, String adressr, String paysr, String telr,String imgr,int idr) {
          
        this.idr = idr;
        this.adressr = adressr;
        this.paysr = paysr;
        this.telr = telr;
        this.typer = typer;
        this.imgr=imgr;
        this.nomr = nomr;


    }
  public Resteau( String typer,String nomr, String adressr,String paysr,String telr,String imgr) {
           this.adressr = adressr;
        this.paysr = paysr;
        this.telr = telr;
        this.typer = typer;
        this.imgr=imgr;
        this.nomr = nomr;

    }
  
    public Resteau(int idr){
       this.idr=idr;
       
    }

   
 
    public String getAdressR() {
        return adressr;
    }

    public void setPaysR(String paysr) {
        this.paysr = paysr;
    }

    public void setTelR(String telr) {
        this.telr = telr;
    }

    public String getPaysR() {
        return paysr;
    }

    public String getTelR() {
        return telr;
    }

    public void setNomR(String nomr) {
        this.nomr = nomr;
    }

    public void setIdR(int idr) {
        this.idr = idr;
    }

    public String getNomR() {
        return nomr;
    }

    public int getIdR() {
        return idr;
    }

    public void setAdressR(String adressr) {
        this.adressr = adressr;
    }
  
    public String getTypeR() {
        return typer;
    }

    public void setTypeR(String typer) {
        this.typer = typer;
    }
   

    @Override
    public String toString() {
        return "Resteau{" + "adressR=" + adressr + ", typeR=" + typer + ", nomR=" + nomr + ", paysR=" + paysr + ", telR=" + telr + ", imgR=" + imgr + ", idR=" + idr +  '}';
    }
    
}
