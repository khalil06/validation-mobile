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
import com.codename1.ui.util.Resources;

/**
 *
 * @author dorsaf
 */
public class HomeHotelForm extends Form{
Form current;
Resources res;
    public HomeHotelForm() {
        current=this; //Back 
        setTitle("Pack & Go");
        setLayout(BoxLayout.y());
        
        add(new Label("Gestion Hotels"));
        Button btnAddTask = new Button("Add Hotel");
        Button btnListTasks = new Button("List Hotels");
        
        btnAddTask.addActionListener(e-> new AddHotelForm(current, res).show());
        btnListTasks.addActionListener(e-> new ListHotels(current).show());
        addAll(btnAddTask,btnListTasks);
        Button returnbtn=new Button("return to main");
        returnbtn.addActionListener(evt -> new HomeForm().show());
        add(returnbtn);
        
        
    }
    
    
}
