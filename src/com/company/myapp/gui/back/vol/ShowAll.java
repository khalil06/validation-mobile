package com.company.myapp.gui.back.vol;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.company.myapp.Entities.Vol;
import com.company.myapp.services.VolService;
import com.company.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    public static Vol currentVol = null;
    Button addBtn;

    
    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public ShowAll(Form previous) {
        super("Vols", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);
        

        ArrayList<Vol> listVols = VolService.getInstance().getAll();
        
        componentModels = new ArrayList<>();
        
        // tri
        sortPicker = PickerComponent.createStrings("Company", "Origin", "Destination", "DepartureDate", "ArrivalDate", "Status", "InitialPrice").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listVols);
            for (Vol vol : listVols) {
                Component model = makeVolModel(vol);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);
        
        if (listVols.size() > 0) {
            for (Vol vol : listVols) {
                Component model = makeVolModel(vol);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentVol = null;
            new Manage(this).show();
        });
        
    }
    Label companyLabel   , originLabel   , destinationLabel   , departureDateLabel   , arrivalDateLabel   , statusLabel   , initialPriceLabel  ;
    

    private Container makeModelWithoutButtons(Vol vol) {
        Container volModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        volModel.setUIID("containerRounded");
        
        
        companyLabel = new Label("Company : " + vol.getCompany());
        companyLabel.setUIID("labelDefault");
        
        originLabel = new Label("Origin : " + vol.getOrigin());
        originLabel.setUIID("labelDefault");
        
        destinationLabel = new Label("Destination : " + vol.getDestination());
        destinationLabel.setUIID("labelDefault");
        
        departureDateLabel = new Label("DepartureDate : " + new SimpleDateFormat("dd-MM-yyyy").format(vol.getDepartureDate()));
        departureDateLabel.setUIID("labelDefault");
        
        arrivalDateLabel = new Label("ArrivalDate : " + new SimpleDateFormat("dd-MM-yyyy").format(vol.getArrivalDate()));
        arrivalDateLabel.setUIID("labelDefault");
        
        statusLabel = new Label("Status : " + (vol.getStatus() == 1 ?  "True" : "False"));
        statusLabel.setUIID("labelDefault");
        
        initialPriceLabel = new Label("InitialPrice : " + vol.getInitialPrice());
        initialPriceLabel.setUIID("labelDefault");
        
        companyLabel = new Label("Company : " + vol.getCompany().getName());
        companyLabel.setUIID("labelDefault");
        

        volModel.addAll(
                
                companyLabel, originLabel, destinationLabel, departureDateLabel, arrivalDateLabel, statusLabel, initialPriceLabel
        );

        return volModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeVolModel(Vol vol) {

        Container volModel = makeModelWithoutButtons(vol);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentVol = vol;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce vol ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = VolService.getInstance().delete(vol.getId());

                if (responseCode == 200) {
                    currentVol = null;
                    dlg.dispose();
                    volModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du vol. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        volModel.add(btnsContainer);

        return volModel;
    }
    
}