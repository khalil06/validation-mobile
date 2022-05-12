package com.company.myapp.gui.back.company;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Company;
import com.company.myapp.Entities.*;
import com.company.myapp.services.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;
    

    Company currentCompany;

    Label nameLabel , imageLabel , descriptionLabel ;
    TextField nameTF , imageTF , descriptionTF ;
    
    
    
    
    ImageViewer imageIV;
    Button selectImageButton;
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentCompany == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCompany = ShowAll.currentCompany;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        nameLabel = new Label("Name : ");
        nameLabel.setUIID("labelDefault");
        nameTF = new TextField();
        nameTF.setHint("Tapez le name");
        


        
        
        
        


        
        
        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");
        


        

        
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentCompany == null) {
            
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            
            
            manageButton = new Button("Ajouter");
        } else {
            nameTF.setText(currentCompany.getName());
            
            descriptionTF.setText(currentCompany.getDescription());
            
            
            
            if (currentCompany.getImage() != null) {
                selectedImage = currentCompany.getImage();
                String url = Statics.COMPANY_IMAGE_URL + currentCompany.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            imageLabel, imageIV,
            selectImageButton,
            nameLabel, nameTF,
            
            descriptionLabel, descriptionTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });
        
        if (currentCompany == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CompanyService.getInstance().add(
                            new Company(
                                    
                                    
                                    nameTF.getText(),
                                    selectedImage,
                                    descriptionTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Company ajouté avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de company. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CompanyService.getInstance().edit(
                            new Company(
                                    currentCompany.getId(),
                                    
                                    
                                    nameTF.getText(),
                                    selectedImage,
                                    descriptionTF.getText()

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Company modifié avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de company. Code d'erreur : " + responseCode, new Command("Ok"));
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
        
        
        
        
        
        
        
        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }
        
             
        return true;
    }
}