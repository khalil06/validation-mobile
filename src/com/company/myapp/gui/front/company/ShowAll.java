package com.company.myapp.gui.front.company;

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
    
    
    Container btnsContainer;

    private Component makeCompanyModel(Company company) {

        Container companyModel = makeModelWithoutButtons(company);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        
        companyModel.add(btnsContainer);

        return companyModel;
    }
    
}