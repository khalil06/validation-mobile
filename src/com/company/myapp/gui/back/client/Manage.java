package com.company.myapp.gui.back.client;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Client;
import com.company.myapp.Entities.*;
import com.company.myapp.services.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    

    Client currentClient;

    Label nameLabel , emailLabel ;
    TextField nameTF , emailTF ;
    
    
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentClient == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentClient = ShowAll.currentClient;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        nameLabel = new Label("Name : ");
        nameLabel.setUIID("labelDefault");
        nameTF = new TextField();
        nameTF.setHint("Tapez le name");
        


        
        
        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");
        


        

        

        if (currentClient == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            nameTF.setText(currentClient.getName());
            emailTF.setText(currentClient.getEmail());
            
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            nameLabel, nameTF,
            emailLabel, emailTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentClient == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ClientService.getInstance().add(
                            new Client(
                                    
                                    
                                    nameTF.getText(),
                                    emailTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Client ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de client. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ClientService.getInstance().edit(
                            new Client(
                                    currentClient.getId(),
                                    
                                    
                                    nameTF.getText(),
                                    emailTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Client modifié avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de client. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        if (nameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Name vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
             
        return true;
    }
}