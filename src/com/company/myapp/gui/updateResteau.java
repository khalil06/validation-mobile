/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Resteau;
import com.company.myapp.services.ServiceRestau;
/**
 *
 * @author hp
 */
public class updateResteau  extends Form{
    
    public updateResteau(Form previous, Resteau R){
        setTitle("Modif Restau");
        
        TextField tfNResteau = new TextField(R.getNomR(),"Nom Resetau", 20, TextField.ANY);
          TextField tfAdress= new TextField(R.getAdressR(), "adresse ",20, TextField.ANY);
       TextField tfType = new TextField(R.getTypeR(),"type",20, TextField.ANY);
        TextField tfpays = new TextField(R.getPaysR(),"pays",20, TextField.ANY);
        TextField tftel = new TextField(R.getTelR(),"tel",20, TextField.ANY);
        TextField tfimg = new TextField(R.getImgR(),"imgr");      
        Button btnValider = new Button("Modifier");
                                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

         btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNResteau.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                       R.setNomR(tfNResteau.getText());
                       R.setAdressR(tfAdress.getText());
                       R.setTypeR(tfType.getText());
                       R.setPaysR(tfpays.getText());
                       R.setTelR(tftel.getText());
                       R.setImgR(tfimg.getText());

                        if( ServiceRestau.getInstance().updateResteau(R))
                        {
                           Dialog.show("Success","Connection accepted modfiiicaitonnsaterr",new Command("OK"));
                           new ListRestauuForm(previous).show();
                            
                           refreshTheme();
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }       
                }      
            }
        });    
        addAll(tfNResteau,tfAdress, tfType,tfpays,tftel,tfimg,btnValider);
    }
}
