package com.company.myapp.gui.back.vol;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Company;
import com.company.myapp.Entities.Vol;
import com.company.myapp.Entities.*;
import com.company.myapp.services.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    

    Vol currentVol;

    Label originLabel , destinationLabel , statusLabel , initialPriceLabel ;
    TextField originTF , destinationTF , initialPriceTF ;
    PickerComponent departureDateTF;PickerComponent arrivalDateTF;
    CheckBox statusCB;
    ArrayList<Company> listCompanys;
    PickerComponent companyPC;
    Company selectedCompany = null;
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentVol == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentVol = ShowAll.currentVol;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        
        String[] companyStrings;
        int companyIndex;
        companyPC = PickerComponent.createStrings("").label("Company");
        listCompanys = CompanyService.getInstance().getAll();
        companyStrings = new String[listCompanys.size()];
        companyIndex = 0;
        for (Company company : listCompanys) {
            companyStrings[companyIndex] = company.getName();
            companyIndex++;
        }
        companyPC.getPicker().setStrings(companyStrings);
        companyPC.getPicker().addActionListener(l -> selectedCompany = listCompanys.get(companyPC.getPicker().getSelectedStringIndex()));
        

        
        


        
        
        originLabel = new Label("Origin : ");
        originLabel.setUIID("labelDefault");
        originTF = new TextField();
        originTF.setHint("Tapez le origin");
        


        
        
        destinationLabel = new Label("Destination : ");
        destinationLabel.setUIID("labelDefault");
        destinationTF = new TextField();
        destinationTF.setHint("Tapez le destination");
        


        
        departureDateTF = PickerComponent.createDate(null).label("DepartureDate");
        


        
        arrivalDateTF = PickerComponent.createDate(null).label("ArrivalDate");
        


        
        
        
        
        statusCB = new CheckBox("Status : ");
        


        
        
        initialPriceLabel = new Label("InitialPrice : ");
        initialPriceLabel.setUIID("labelDefault");
        initialPriceTF = new TextField();
        initialPriceTF.setHint("Tapez le initialPrice");
        


        

        

        if (currentVol == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            originTF.setText(currentVol.getOrigin());
            destinationTF.setText(currentVol.getDestination());
            departureDateTF.getPicker().setDate(currentVol.getDepartureDate());
            arrivalDateTF.getPicker().setDate(currentVol.getArrivalDate());
            if(currentVol.getStatus() == 1){statusCB.setSelected(true);}
            initialPriceTF.setText(String.valueOf(currentVol.getInitialPrice()));
            
            companyPC.getPicker().setSelectedString(currentVol.getCompany().getName());
            selectedCompany = currentVol.getCompany();
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            
            originLabel, originTF,
            destinationLabel, destinationTF,
            departureDateTF,
            arrivalDateTF,
            statusCB,
            initialPriceLabel, initialPriceTF,
            companyPC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentVol == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = VolService.getInstance().add(
                            new Vol(
                                    
                                    
                                    selectedCompany,
                                    originTF.getText(),
                                    destinationTF.getText(),
                                    departureDateTF.getPicker().getDate(),
                                    arrivalDateTF.getPicker().getDate(),
                                    statusCB.isSelected() ?  1 : 0,
                                    Float.parseFloat(initialPriceTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Vol ajouté avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de vol. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = VolService.getInstance().edit(
                            new Vol(
                                    currentVol.getId(),
                                    
                                    
                                    selectedCompany,
                                    originTF.getText(),
                                    destinationTF.getText(),
                                    departureDateTF.getPicker().getDate(),
                                    arrivalDateTF.getPicker().getDate(),
                                    statusCB.isSelected() ?  1 : 0,
                                    Float.parseFloat(initialPriceTF.getText())

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Vol modifié avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de vol. Code d'erreur : " + responseCode, new Command("Ok"));
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

        
        
        
        
        
        if (originTF.getText().equals("")) {
            Dialog.show("Avertissement", "Origin vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (destinationTF.getText().equals("")) {
            Dialog.show("Avertissement", "Destination vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (departureDateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la departureDate", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (arrivalDateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la arrivalDate", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (initialPriceTF.getText().equals("")) {
            Dialog.show("Avertissement", "InitialPrice vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(initialPriceTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", initialPriceTF.getText() + " n'est pas un nombre valide (initialPrice)", new Command("Ok"));
            return false;
        }
        
        

        
        if (selectedCompany == null) {
            Dialog.show("Avertissement", "Veuillez choisir un company", new Command("Ok"));
            return false;
        }
        

        
             
        return true;
    }
}