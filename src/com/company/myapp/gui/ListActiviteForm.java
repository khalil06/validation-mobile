/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.company.myapp.Entities.Activite;
import com.company.myapp.services.ServiceActivite;
import java.util.ArrayList;
/**
 *
 * @author bhk
 */
public class ListActiviteForm extends Form {

    public ListActiviteForm(Form previous) {
        setTitle("List Activite");
/*
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceActivite.getInstance().getAllActivite().toString());
        add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        */
        ArrayList<Activite> list = ServiceActivite.getInstance().getAllActivite();
        {
            for (Activite A : list) {
                Container c3 = new Container(BoxLayout.y());

                SpanLabel cat1 = new SpanLabel("nom activite: " + A.getNom_activite());
                SpanLabel cat2 = new SpanLabel("type Activite: " + A.getType_activite());
                SpanLabel cat3 = new SpanLabel("prix: " + A.getPrix());
                SpanLabel cat4 = new SpanLabel("pays: " + A.getPays());
                SpanLabel cat5 = new SpanLabel("adresse: " + A.getAdresse());
                SpanLabel cat6 = new SpanLabel();
                c3.add(cat1);
                c3.add(cat2);
                c3.add(cat3);
                c3.add(cat4);
                c3.add(cat5);
                c3.add(cat6);
                add(c3);
                Form hi = new Form("PDF Viewer", BoxLayout.y());
                Button devGuide = new Button("Show PDF");
                devGuide.addActionListener(e -> {

                    FileSystemStorage fs = FileSystemStorage.getInstance();
                    final String homePath = fs.getAppHomePath();
                    String fileName = homePath+"xd.pdf" ;
                    if(!fs.exists(fileName)) {
                        Util.downloadUrlToFile("http://pdff.test/abc.pdf", fileName, true);
                    }
                    Display.getInstance().execute(fileName);

                });
                hi.add(devGuide);

                c3.add(hi);
                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
                Button returnbtn=new Button("return to main");
                returnbtn.addActionListener(evt -> new HomeForm().show());
                add(returnbtn);
            }

        }
    }
}
