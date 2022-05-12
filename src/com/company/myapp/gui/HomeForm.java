/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 *
 * @author hp
 */
public class HomeForm extends Form{
Form current;
Resources res;
     public HomeForm() {
        current=this; //Back 
        setTitle("PACK & GO");
        setLayout(BoxLayout.y());
        
        add(new Label("DASHBOARD"));
        Button btnAddRestau = new Button("Add Restau");
        Button btnListResetau = new Button("List Restau");
        Button btnList =  new Button("Front Restau");
        Button btnHotel =  new Button("Gestion Hotels");
        Button btnPersonnalite=new Button("Personnalité");
        Button btnActivite=new Button("Activite");
Button btnEmir = new Button("Vols");
        btnAddRestau.addActionListener(e-> new AddRestauForm(current).show());
        btnListResetau.addActionListener(e-> new ListRestauuForm(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnHotel.addActionListener(e-> new HomeHotelForm().show());
        btnPersonnalite.addActionListener(evt -> PersonalityTest.createPersonalityTestForm().show());
        btnActivite.addActionListener(evt -> new HomeFormActivite().show());
        btnEmir.addActionListener(evt ->   new Login().show());
      /*  btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());*/

        // btnMapRestau.addActionListener(e->  new MapForm());
      //  btnMapRestau.addActionListener(e-> new MapForm (current).show());

        addAll(btnAddRestau,btnListResetau,btnList, btnHotel,btnPersonnalite,btnActivite,btnEmir);
        
         
    }
 
}