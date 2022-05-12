package com.company.myapp.gui.back;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.company.myapp.gui.Login;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilBack() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
    try {
        addGUIs();
    }catch (Exception e)
    {
        System.out.println(e.getMessage());
    }
    }

    private void addGUIs() {
        //mageViewer userImage = new ImageViewer(theme.getImage("default.jpg").fill(200, 200));
        //userImage.setUIID("candidatImage");
        label = new Label("Admin"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        //userContainer.add(BorderLayout.WEST, userImage);
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeClientsButton(), 
                makeCompanysButton(), 
                makeReservationsButton(), 
                makeVolsButton()
                
        );

        this.add(menuContainer);
    }

    private Button makeClientsButton() {
        Button button = new Button("Clients");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.company.myapp.gui.back.client.ShowAll(this).show());
        return button;
    }
    private Button makeCompanysButton() {
        Button button = new Button("Companys");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.company.myapp.gui.back.company.ShowAll(this).show());
        return button;
    }
    private Button makeReservationsButton() {
        Button button = new Button("Reservations");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.company.myapp.gui.back.reservation.ShowAll(this).show());
        return button;
    }
    private Button makeVolsButton() {
        Button button = new Button("Vols");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.company.myapp.gui.back.vol.ShowAll(this).show());
        return button;
    }
    
}
