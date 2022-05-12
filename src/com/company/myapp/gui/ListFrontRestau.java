/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.company.myapp.Entities.Resteau;
import com.company.myapp.services.ServiceRestau;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ListFrontRestau extends Form{
      public ListFrontRestau(Form previous) {
        setTitle("List restau");
/*
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceRestau.getInstance().getAllRestau().toString());
        add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
*/
 ArrayList<Resteau> list = ServiceRestau.getInstance().getAllRestau();
        {
            for (Resteau R : list) {
                Container c3 = new Container(BoxLayout.y());
 
                SpanLabel cat1= new SpanLabel("type Resteau :" + R.getTypeR());
                SpanLabel cat2= new SpanLabel( "nom" + R.getNomR());
                SpanLabel cat3= new SpanLabel("adress :" + R.getAdressR());
                SpanLabel cat4= new SpanLabel("pays :" + R.getPaysR());
                SpanLabel cat5= new SpanLabel("tel :" + R.getTelR());
     
String url = "http://localhost/pack-and-go-web/public/uploads";
        Image ph = Image.createImage(1150, 420);
        EncodedImage enc = EncodedImage.createFromImage(ph, false);
        Image urlim = URLImage.createToStorage(enc, R.getImgR(), url+"/"+R.getImgR());
        ImageViewer imgV = new ImageViewer();
        imgV.setImage(urlim);
            //    sp.setText(ServiceHotel.getInstance().getAllTasks().toString());
                c3.add(cat1);
                c3.add(cat2);
                c3.add(cat3);
                c3.add(cat4);
                c3.add(cat5);
              c3.add(imgV);
            
                add(c3);
                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
               
              //  Delete.getAllStyles().setBgColor(0xF36B08);
      

                                Button Map =new Button("map");
                                 c3.add(Map);



            }
        }
    }
  private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }
  
}
