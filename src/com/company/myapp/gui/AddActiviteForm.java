/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Activite;
import com.company.myapp.services.ServiceActivite;

/**
 *
 * @author bhk
 */
public class AddActiviteForm extends Form{
    private Resources theme = UIManager.initFirstTheme("/theme");
    public AddActiviteForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Ajouter une nouvelle activité");
        this.getStyle().setBgColor(0xffffff);
        setLayout(BoxLayout.y());

        TextField tfNomActivite = new TextField("","Nom de l'activité");
        tfNomActivite.setPreferredSize(new Dimension(200,140));
        TextField tfTypeActivite = new TextField("","Type de l'activité");
        tfTypeActivite.setPreferredSize(new Dimension(200,140));
        TextField tfPrixActivite = new TextField("","Prix de l'activité");
        tfPrixActivite.setPreferredSize(new Dimension(200,140));
        TextField tfAdresse = new TextField("","Adresse de l'activité");
        tfAdresse.setPreferredSize(new Dimension(200,140));
        TextField tfPays = new TextField("","Pays de l'activité");
        tfPays.setPreferredSize(new Dimension(200,140));

        Button btnValider = new Button("Add Activité");
        btnValider.getStyle().setBgTransparency(255);
        btnValider.getStyle().setBgColor(0xcfb9);
        btnValider.getStyle().setFgColor(0xffffff);
        btnValider.getStyle().setMarginTop(40);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        btnValider.setPreferredSize(new Dimension(200,140));
        btnValider.getStyle().setBorder(RoundBorder.create().
                rectangle(true).
                color(0xcfb9).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNomActivite.getText().length()==0)||(tfTypeActivite.getText().length()==0)||(tfPrixActivite.getText().length()==0)||(tfAdresse.getText().length()==0)||(tfPays.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Activite a = new Activite(tfNomActivite.getText(), tfTypeActivite.getText(), Float.parseFloat(tfPrixActivite.getText()), tfAdresse.getText(), tfPays.getText());
                        if( ServiceActivite.getInstance().addActivite(a))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfNomActivite,tfTypeActivite,tfPrixActivite,tfAdresse,tfPays,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
