package com.company.myapp.gui.back.client;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.company.myapp.Entities.Client;
import com.company.myapp.services.ClientService;
import com.company.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    public static Client currentClient = null;
    Button addBtn;

    
    

    public ShowAll(Form previous) {
        super("Clients", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Client> listClients = ClientService.getInstance().getAll();
        
        
        if (listClients.size() > 0) {
            for (Client client : listClients) {
                Component model = makeClientModel(client);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentClient = null;
            new Manage(this).show();
        });
        
    }
    Label nameLabel   , emailLabel  ;
    

    private Container makeModelWithoutButtons(Client client) {
        Container clientModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        clientModel.setUIID("containerRounded");
        
        
        nameLabel = new Label("Name : " + client.getName());
        nameLabel.setUIID("labelDefault");
        
        emailLabel = new Label("Email : " + client.getEmail());
        emailLabel.setUIID("labelDefault");
        

        clientModel.addAll(
                
                nameLabel, emailLabel
        );

        return clientModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeClientModel(Client client) {

        Container clientModel = makeModelWithoutButtons(client);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentClient = client;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce client ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ClientService.getInstance().delete(client.getId());

                if (responseCode == 200) {
                    currentClient = null;
                    dlg.dispose();
                    clientModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du client. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        clientModel.add(btnsContainer);

        return clientModel;
    }
    
}