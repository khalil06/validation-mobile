package com.company.myapp.gui.back.reservation;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.Entities.Reservation;
import com.company.myapp.Entities.*;
import com.company.myapp.services.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    

    Reservation currentReservation;

    Label typeLabel , classeLabel , finalPriceLabel ;
    TextField typeTF , classeTF , finalPriceTF ;
    
    
    ArrayList<Client> listClients;
    PickerComponent clientPC;
    Client selectedClient = null;
    ArrayList<Vol> listVols;
    PickerComponent volPC;
    Vol selectedVol = null;
    
    
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentReservation == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReservation = ShowAll.currentReservation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        
        String[] clientStrings;
        int clientIndex;
        clientPC = PickerComponent.createStrings("").label("Client");
        listClients = ClientService.getInstance().getAll();
        clientStrings = new String[listClients.size()];
        clientIndex = 0;
        for (Client client : listClients) {
            clientStrings[clientIndex] = client.getName();
            clientIndex++;
        }
        clientPC.getPicker().setStrings(clientStrings);
        clientPC.getPicker().addActionListener(l -> selectedClient = listClients.get(clientPC.getPicker().getSelectedStringIndex()));
        
        String[] volStrings;
        int volIndex;
        volPC = PickerComponent.createStrings("").label("Vol");
        listVols = VolService.getInstance().getAll();
        volStrings = new String[listVols.size()];
        volIndex = 0;
        for (Vol vol : listVols) {
            volStrings[volIndex] = vol.getDestination();
            volIndex++;
        }
        volPC.getPicker().setStrings(volStrings);
        volPC.getPicker().addActionListener(l -> selectedVol = listVols.get(volPC.getPicker().getSelectedStringIndex()));
        

        
        


        
        


        
        
        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeTF = new TextField();
        typeTF.setHint("Tapez le type");
        


        
        
        classeLabel = new Label("Classe : ");
        classeLabel.setUIID("labelDefault");
        classeTF = new TextField();
        classeTF.setHint("Tapez le classe");
        


        
        
        finalPriceLabel = new Label("FinalPrice : ");
        finalPriceLabel.setUIID("labelDefault");
        finalPriceTF = new TextField();
        finalPriceTF.setHint("Tapez le finalPrice");
        


        

        

        if (currentReservation == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            
            typeTF.setText(currentReservation.getType());
            classeTF.setText(currentReservation.getClasse());
            finalPriceTF.setText(String.valueOf(currentReservation.getFinalPrice()));
            
            clientPC.getPicker().setSelectedString(currentReservation.getClient().getName());
            selectedClient = currentReservation.getClient();
            volPC.getPicker().setSelectedString(currentReservation.getVol().getDestination());
            selectedVol = currentReservation.getVol();
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            
            
            typeLabel, typeTF,
            classeLabel, classeTF,
            finalPriceLabel, finalPriceTF,
            clientPC,volPC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentReservation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().add(
                            new Reservation(
                                    
                                    
                                    selectedClient,
                                    selectedVol,
                                    typeTF.getText(),
                                    classeTF.getText(),
                                    Float.parseFloat(finalPriceTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation ajouté avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().edit(
                            new Reservation(
                                    currentReservation.getId(),
                                    
                                    
                                    selectedClient,
                                    selectedVol,
                                    typeTF.getText(),
                                    classeTF.getText(),
                                    Float.parseFloat(finalPriceTF.getText())

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation modifié avec succés");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
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

        
        
        
        
        
        
        
        
        if (typeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Type vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (classeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Classe vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        if (finalPriceTF.getText().equals("")) {
            Dialog.show("Avertissement", "FinalPrice vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(finalPriceTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", finalPriceTF.getText() + " n'est pas un nombre valide (finalPrice)", new Command("Ok"));
            return false;
        }
        
        

        
        if (selectedClient == null) {
            Dialog.show("Avertissement", "Veuillez choisir un client", new Command("Ok"));
            return false;
        }
        
        if (selectedVol == null) {
            Dialog.show("Avertissement", "Veuillez choisir un vol", new Command("Ok"));
            return false;
        }
        

        
             
        return true;
    }
}