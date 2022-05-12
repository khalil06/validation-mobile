/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author hp
 */
public class HomeFront extends Form{
    Form current;
     public HomeFront() {
        current=this; //Back 
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAddRestau = new Button("Afiicher Restau ");
       // Button btnListResetau = new Button("List Restau");
       // Button btnList =  new Button("Front Restau");

        btnAddRestau.addActionListener(e-> new ListFrontRestau(current).show());
      /*  btnListResetau.addActionListener(e-> new ListRestauuForm(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());
        btnList.addActionListener(e-> new ListFrontRestau(current).show());*/

        // btnMapRestau.addActionListener(e->  new MapForm());
      //  btnMapRestau.addActionListener(e-> new MapForm (current).show());

        addAll(btnAddRestau);
        
         
    }
 
}
