package com.company.myapp.gui.back.company;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.company.myapp.Entities.Company;
import com.company.myapp.services.CompanyService;
import com.company.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    Resources theme = UIManager.initFirstTheme("/theme");
    
    public static Company currentCompany = null;
    Button addBtn;

    
    

    public ShowAll(Form previous) {
        super("Companys", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Company> listCompanys = CompanyService.getInstance().getAll();
        
        
        if (listCompanys.size() > 0) {
            for (Company company : listCompanys) {
                Component model = makeCompanyModel(company);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCompany = null;
            new Manage(this).show();
        });
        
    }
    Label nameLabel   , imageLabel   , descriptionLabel  ;
    
    ImageViewer imageIV;
    

    private Container makeModelWithoutButtons(Company company) {
        Container companyModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        companyModel.setUIID("containerRounded");
        
        
        nameLabel = new Label("Name : " + company.getName());
        nameLabel.setUIID("labelDefault");
        
        imageLabel = new Label("Image : " + company.getImage());
        imageLabel.setUIID("labelDefault");
        
        descriptionLabel = new Label("Description : " + company.getDescription());
        descriptionLabel.setUIID("labelDefault");
        
        if (company.getImage() != null) {
            String url = Statics.COMPANY_IMAGE_URL + company.getImage();
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

        companyModel.addAll(
                imageIV,
                nameLabel, descriptionLabel
        );

        return companyModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeCompanyModel(Company company) {

        Container companyModel = makeModelWithoutButtons(company);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCompany = company;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce company ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CompanyService.getInstance().delete(company.getId());

                if (responseCode == 200) {
                    currentCompany = null;
                    dlg.dispose();
                    companyModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du company. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        companyModel.add(btnsContainer);

        return companyModel;
    }
    
}