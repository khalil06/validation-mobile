/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import static com.codename1.ui.URLImage.RESIZE_SCALE_TO_FILL;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.company.myapp.Entities.Hotel;
import com.company.myapp.services.ServiceHotel;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ListHotels extends Form {
    private Form hi;
    

    public ListHotels(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());  
        afficherListHotels(previous);
    }
    
    public Container createContainerHotel(Hotel r, Form previous){
        Container c3 = new Container(BoxLayout.y());

        SpanLabel cat1= new SpanLabel("Nom hotel :" + r.getNomHotel());
        SpanLabel cat2= new SpanLabel(r.getNbrEtoiles() + "etoiles");
        SpanLabel cat3= new SpanLabel("Nombre chambres :" + r.getNbrChambres());
        SpanLabel cat4= new SpanLabel("Adresse :" + r.getAdresse());
        SpanLabel cat5= new SpanLabel("Pays :" + r.getPays());
        SpanLabel cat6= new SpanLabel("Tel :" + r.getTel());
        SpanLabel cat7= new SpanLabel("Email :" + r.getEmail());

        String url = "http://pdff.test/uploads";
        Image ph = Image.createImage(1150, 420);
        EncodedImage enc = EncodedImage.createFromImage(ph, false);
        Image urlim = URLImage.createToStorage(enc, r.getImage(), url+"/"+r.getImage());
        ImageViewer imgV = new ImageViewer();
        imgV.setImage(urlim);
        c3.add(imgV);
        c3.add(cat1);
        c3.add(cat2);
        c3.add(cat3);
        c3.add(cat4);
        c3.add(cat5);
        c3.add(cat6);
        c3.add(cat7);
        Button Delete =new Button("Delete");
        c3.add(Delete);
        Delete.addActionListener(e -> {
            Dialog alert = new Dialog("Attention");
            SpanLabel message = new SpanLabel("Etes-vous sur de vouloir supprimer cet Hotel?\nCette action est irréversible!");
            alert.add(message);
            Button ok = new Button("Confirmer");
            Button cancel = new Button(new Command("Annuler"));
            //User clicks on ok to delete account
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                   if(ServiceHotel.getInstance().deleteHotel(r.getIdHotel())){ 
                        afficherListHotels(previous);
                   };
                    alert.dispose();
                    refreshTheme();
                }
            });
            alert.add(cancel);
            alert.add(ok);
            alert.showDialog();
        });
        
        Button update =new Button("Update");
        c3.add(update);
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new updateHotelForm(previous,r).show();
            };  
        });

        return c3;
    }
    
    public void afficherListHotels(Form previous){
        removeAll();
        hi = new Form("Search", new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("List Hotels");
        ArrayList<Hotel> list = ServiceHotel.getInstance().getAllTasks();
        Toolbar.setGlobalToolbar(true);
        hi.getToolbar().addSearchCommand(e -> {
            String text = (String)e.getSource();
            if(text == null || text.length() == 0) {
                // clear search
                for(Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);  
                    new ListHotels(previous).show();
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                ArrayList<Hotel> listt = ServiceHotel.getInstance().searchh(text);
                removeAll();
                for (Hotel r : listt) {
                    Container c3 = createContainerHotel(r, previous);
                    add(c3);
                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        add(hi);   
        for (Hotel r : list) { 
            Container c3 = createContainerHotel(r, previous);
            add(c3);
        }

    }
   
}
