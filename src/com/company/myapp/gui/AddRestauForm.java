/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;
import com.codename1.ext.filechooser.FileChooser;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.company.myapp.Entities.Resteau;
import com.company.myapp.services.ServiceRestau;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;


/**
 *
 * @author bhk
 */
/**
 *
 * @author hp
 */
public class AddRestauForm  extends Form{
     public AddRestauForm(Form previous) {
        setTitle("Add a new task");
        setLayout(BoxLayout.y());
                        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        TextField tfName = new TextField("","nom");
        TextField tfAdress= new TextField("", "adresse ");
       TextField tfType = new TextField("","type");
        TextField tfpays = new TextField("","pays");
        TextField tftel = new TextField("","tel");
       // TextField tfimg = new TextField("","img");
CheckBox multiSelect = new CheckBox("Multi-select");
        Button btnValider = new Button("Add task");
      Button btnImg =new Button("inserer image");
      
        btnImg.addActionListener((ActionEvent e) ->{
            
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpg, .png/plain", (ActionEvent e2)->{
                    if(e2 == null || e2.getSource() == null){
                        add("no file was selected");
                        revalidate();
                        return;
                    }
                    if(multiSelect.isSelected()){
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths){
                            CN.execute(path);
                        }
                        return;
                    }
                    String file = (String) e2.getSource();
                    if(file == null){
                        add("no file was selected");
                        revalidate();
                    }
                    else{
                        Image logo;
                        try{
                            logo = Image.createImage(file).scaledHeight(500);
                            add(logo);
                            String imageFile = FileSystemStorage.getInstance().getAppHomePath()+ "photo.png";
                            try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)){
                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);
                            }catch(IOException ex){
                                
                            }
                        } catch (IOException ex) {
                        }
                    }
                    String extension = null;
                    if(file.lastIndexOf(".") > 0){
                        extension = file.substring(file.lastIndexOf(".") + 1);
                        StringBuilder hi = new StringBuilder(file);
                        if(file.startsWith("file://")){
                            hi.delete(0, 7);
                        }
                    
                        int lastIndexPeriod = hi.toString().lastIndexOf(".");
                        Log.p(hi.toString());
                        String ext = hi.toString().substring(0, lastIndexPeriod - 1);
                        try{
                            String namePic = saveFileToDevice(file,ext);
                            System.out.println(namePic);                            
                            btnValider.addActionListener(new ActionListener() {
                                @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length()==0)||(tfAdress.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Resteau R= new Resteau(tfAdress.getText().toString(), tfName.getText().toString(), tfType.getText().toString(), tfpays.getText().toString(), tftel.getText().toString(), namePic);
                        if( ServiceRestau.getInstance().addRestau(R))
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
          }catch(Exception ex){}
                    }
                
                    });
                
           });
                    
        addAll(tfName,tfAdress, tfType ,tfpays ,tftel ,btnImg,btnValider);
                
    }
                
      protected String saveFileToDevice(String hi, String ext) throws IOException{
        URI uri;
        try{
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring( index + 1);
            return hi;
        }catch(URISyntaxException ex){

        }
        return "hh";
      }
        
}            