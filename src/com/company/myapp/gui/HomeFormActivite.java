/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;

/**
 *
 * @author bhk
 */
public class HomeFormActivite extends Form{
Form current;
    public HomeFormActivite() {

        current=this; //Back 
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        SpanLabel sp = new SpanLabel();
        Button btnAddTask = new Button("Ajouter une activite");
        btnAddTask.setPreferredSize(new Dimension(200,140));
        btnAddTask.getStyle().setBgTransparency(255);
        btnAddTask.getStyle().setBgColor(0xcfb9);
        btnAddTask.getStyle().setFgColor(0xffffff);
        btnAddTask.getStyle().setMarginTop(40);
        btnAddTask.getStyle().setMarginBottom(10);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        btnAddTask.getStyle().setBorder(RoundBorder.create().
                rectangle(true).
                color(0xcfb9).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));

        Button btnListTasks = new Button("List Activite");
        
        btnAddTask.addActionListener(e-> new AddActiviteForm(current).show());
        btnListTasks.addActionListener(e-> new ListActiviteForm(current).show());
        addAll(btnAddTask,btnListTasks);

        
        
    }
    
    
}
