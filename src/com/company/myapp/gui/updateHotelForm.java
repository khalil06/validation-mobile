/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Hotel;
import com.company.myapp.services.ServiceHotel;

/**
 *
 * @author dorsaf
 */
public class updateHotelForm extends Form{
    
    public updateHotelForm(Form previous, Hotel t){
        setTitle("Modif Hotel");
        
        TextField tfNomHotel = new TextField(t.getNomHotel(),"Nom Hotel", 20, TextField.ANY);
        ComboBox cbEtoiles = new ComboBox();
        cbEtoiles.addItem(1);
        cbEtoiles.addItem(2);
        cbEtoiles.addItem(3);
        cbEtoiles.addItem(4);
        cbEtoiles.addItem(5);
        
        TextField tfNbrChambres= new TextField(String.valueOf(t.getNbrChambres()), "Nbr chambres", 20, TextField.ANY);
        TextField tfAdresse= new TextField(t.getAdresse(),"Adresse", 20, TextField.ANY);
        TextField tfPays= new TextField(t.getPays(), "Pays", 20, TextField.ANY);
        TextField tfTel= new TextField(String.valueOf(t.getTel()), "Tel", 20, TextField.ANY);
        TextField tfEmail= new TextField(t.getEmail(), "Email", 20, TextField.ANY);
        
        
        Button btnValider = new Button("Modifier");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNomHotel.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        t.setNomHotel(tfNomHotel.getText());
                        t.setNbrChambres(Integer.parseInt(tfNbrChambres.getText()));
                        t.setAdresse(tfAdresse.getText());
                        t.setPays(tfPays.getText());
                        t.setTel(Integer.parseInt(tfTel.getText()));
                        t.setEmail(tfAdresse.getText());
                        if( ServiceHotel.getInstance().updateHotel(t))
                        {
                           Dialog.show("Success","Connection accepted modfiiicaitonnsaterr",new Command("OK"));
                           new ListHotels(previous).show();
                           refreshTheme();
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
            }
        });
        
        addAll(tfNomHotel,cbEtoiles, tfNbrChambres,tfAdresse,tfPays,tfTel,tfEmail,btnValider);
    }
}
