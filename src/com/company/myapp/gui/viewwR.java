/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.company.myapp.Entities.Resteau;
import com.company.myapp.services.ServiceRestau;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class viewwR {/*
      private Database db;
    private EncodedImage enc;
    private Image img;
    private Image coeur;
    private Image achetez;
    private Image coeur2;
       // int idUser= userid;
            ConnectionRequest con = new ConnectionRequest();


//    int id=Product.getId_courant();
    


    private void afficheFav(Image arrowDown, Product p, int color, boolean first, int i) {
        Container Cnom = new Container(BoxLayout.x());
        Container Cimage = new Container(BoxLayout.x());
        Container Cprix = new Container(BoxLayout.x());
        Container Cdesc = new Container(BoxLayout.y());
        Container rateContainer = new Container(BoxLayout.y());
        Container Cachat = new Container(BoxLayout.x());
        Container all = new Container(BoxLayout.y());
        Container videC = new Container(BoxLayout.y());
        try {
            enc = EncodedImage.create("/giphy.gif");
        } catch (IOException ex) {

        }
        img = URLImage.createToStorage(enc, p.getImg(), "http://localhost/medahed/web" + p.getImg(), URLImage.RESIZE_SCALE_TO_FILL);
        img.fill(50, 50);
        coeur = URLImage.createToStorage(enc, "coeur.png" + i, "http://localhost/medahed/web/images/coeur.png", URLImage.RESIZE_SCALE_TO_FILL);
        coeur.fill(50, 50);
        achetez = URLImage.createToStorage(enc, "a.png" + i, "http://localhost/medahed/web/images/achete.png", URLImage.RESIZE_SCALE_TO_FILL);
achetez.fill(50, 50);

        coeur2 = URLImage.createToStorage(enc, "coeur2.png", "http://localhost/medahed/web/images/coeur2.png", URLImage.RESIZE_SCALE_TO_FILL);
        coeur2.fill(50, 50);
        //image1.add(coeur.scaledWidth(30));
        Label nom = new Label();
        nom.setText(p.getNom());
        
        Label desct = new Label("Description :");
        Label desc = new Label();
        desc.setText(p.getDescription());
        
        Label prix = new Label();
        prix.setText(String.valueOf(p.getPrice()) + " TND");
        prix.getAllStyles().setFgColor(0xF69602);
        Button fav = new Button("fav");
        Button acheter = new Button("acheter");
        acheter.addActionListener(e -> {

            if (Product.getPanier().contains(p)) {
                for (int m = 0; m < Product.getPanier().size(); m++) {
                    if (Product.getPanier().get(m).getId() == p.getId()) {
                        if (Product.getPanier().get(m).getQuantite() < p.getStock()) {
                            // Verif=Produit.setPanier(p);
                            update(Product.getPanier().get(m), Product.getPanier().get(m).getId());

                            //getShowPane();
                        } else {
                            System.out.println("depassez stock");
                        }
                    }
                }
            } else {
                if (p.getStock() > 0) {  //Verif=Produit.setPanier(p);
                    acheter(p);
                    //  getShowPane();             
                } else {
                    System.out.println("depasser");
                }
            }
        });
        Container image1 = new Container(BoxLayout.x());
        Container image2 = new Container(BoxLayout.x());
        Container image5 = new Container(BoxLayout.x());
        image1.add(coeur.scaledWidth(25));
        image2.add(achetez.scaled(200,140));
        image5.add(coeur2.scaledWidth(25));
        if (verifFavoris(p)) {
            Cachat.addAll(image5, image2);
        } else {
            Cachat.addAll(image1, image2);
        }

        fav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (verifFavoris(p)) {
                    try {
                        db.execute("DELETE FROM favoris WHERE id = " +  idUser*1000+p.getId() + "");
                        Cachat.replace(image5, image1, null);
                        refreshTheme();
                    } catch (IOException ex) {
                    }
                } else {
                    favoris(p);
                    Cachat.replace(image1, image5, null);
                    refreshTheme();
                }
            }
        });

        image1.setLeadComponent(fav);
        image2.setLeadComponent(acheter);
        image5.setLeadComponent(fav);
        Slider s = createStarRankSlider();
        s.setProgress(2);
        Button rate=new Button("valider rating");
        rate.addActionListener((ActionListener) (ActionEvent evt) -> {
            int note= s.getProgress();                
                String Url = "http://localhost/pidev/web/app_dev.php/boutique/mobilesetrating?rating=" + note + "&nomP=" + p.getNom() + "&idP=" + p.getId() + "&idc=" + idUser + "";
                        con.setUrl(Url);
                        con.addResponseListener((e) -> {
                            String str = new String(con.getResponseData());
                                //System.out.println(str);
                        });
                NetworkManager.getInstance().addToQueueAndWait(con);
                System.out.println("La note attribuée est : "+String.valueOf(note));
            });
        
        Cnom.add(nom);
        Cimage.add(img.scaledWidth(250));
        Cprix.add(prix);
        Cdesc.addAll(desct,desc);
       // rateContainer.setWidth(300);
        rateContainer.addAll(s,rate);
        all.addAll(Cnom,Cimage,Cprix,Cdesc,rateContainer,Cachat);
        //all.getStyle().setBorder(Border.createLineBorder(1));
        
        add(FlowLayout.encloseIn(all));
//        }

    }

    @Override
    protected void showOtherForm(Resources res) {
        new afficheProduit(res).show();
    }

    @Override
    protected void showOtherForm2(Resources res) {
        new s_favoris(res).show();
    }

    @Override
    protected void showOtherForm3(Resources res) {
        new s_Panier(res).show();
    }
    */
    
}
