package com.company.myapp.gui.back.reservation;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.company.myapp.Entities.Reservation;
import com.company.myapp.services.ReservationService;
import com.company.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    public static Reservation currentReservation = null;
    Button addBtn;

    
    

    public ShowAll(Form previous) {
        super("Reservations", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Reservation> listReservations = ReservationService.getInstance().getAll();
        
        
        if (listReservations.size() > 0) {
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReservation = null;
            new Manage(this).show();
        });
        
    }
    Label clientLabel   , volLabel   , typeLabel   , classeLabel   , finalPriceLabel  ;
    

    private Container makeModelWithoutButtons(Reservation reservation) {
        Container reservationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reservationModel.setUIID("containerRounded");
        
        
        clientLabel = new Label("Client : " + reservation.getClient());
        clientLabel.setUIID("labelDefault");
        
        volLabel = new Label("Vol : " + reservation.getVol());
        volLabel.setUIID("labelDefault");
        
        typeLabel = new Label("Type : " + reservation.getType());
        typeLabel.setUIID("labelDefault");
        
        classeLabel = new Label("Classe : " + reservation.getClasse());
        classeLabel.setUIID("labelDefault");
        
        finalPriceLabel = new Label("FinalPrice : " + reservation.getFinalPrice());
        finalPriceLabel.setUIID("labelDefault");
        
        clientLabel = new Label("Client : " + reservation.getClient().getName());
        clientLabel.setUIID("labelDefault");
        
        volLabel = new Label("Vol : " + reservation.getVol().getDestination());
        volLabel.setUIID("labelDefault");
        

        reservationModel.addAll(
                
                clientLabel, volLabel, typeLabel, classeLabel, finalPriceLabel
        );

        return reservationModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeReservationModel(Reservation reservation) {

        Container reservationModel = makeModelWithoutButtons(reservation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentReservation = reservation;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reservation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReservationService.getInstance().delete(reservation.getId());

                if (responseCode == 200) {
                    currentReservation = null;
                    dlg.dispose();
                    reservationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        reservationModel.add(btnsContainer);

        return reservationModel;
    }
    
}