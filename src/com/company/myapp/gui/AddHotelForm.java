/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.gui;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RGBImage;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.company.myapp.Entities.Hotel;
import com.company.myapp.services.ServiceHotel;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
/**
 *
 * @author dorsaf
 */
public class AddHotelForm extends Form{
        private Resources theme;

    
    public AddHotelForm(Form previous, Resources res) {      
        theme = UIManager.initFirstTheme("/theme");

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Add new Hotel");
        setLayout(BoxLayout.y());
        getContentPane().setScrollVisible(false);
        
        TextField tfNomHotel = new TextField("","Nom Hotel");      
        SpanLabel sp= new SpanLabel("Nombre d'étoiles");
        TextField tfNbrChambres= new TextField("", "Nbr chambres");
        TextField tfAdresse= new TextField("", "Adresse");
        TextField tfPays= new TextField("", "Pays");
        TextField tfTel= new TextField("", "Tel");
        TextField tfEmail= new TextField("", "Email");
        Form hii = new Form("",new BoxLayout(BoxLayout.Y_AXIS));  
        Slider o = createStarRankSlider(theme);
        hii.add(FlowLayout.encloseLeftMiddle(o));        
        CheckBox multiSelect = new CheckBox("Multi-select");

        Button btnValider = new Button("Add Hotel");
        Button btnImg =new Button("inserer image");
        btnImg.addActionListener((ActionEvent e) ->{
            if(FileChooser.isAvailable()){
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpeg, .jpg, .jpg, .png/plain", (ActionEvent e2)->{
                    if(multiSelect.isSelected()){
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths){
                            CN.execute(path);
                        }
                        return;
                    }
                    String file = (String) e2.getSource();
                    if(file == null){
                        add("no file was selected");
                        revalidate();
                    }
                    else{
                        Image logo;
                        try{
                            logo = Image.createImage(file).scaledHeight(500);
                            add(logo);
                            String imageFile = FileSystemStorage.getInstance().getAppHomePath()+ "photo.png";
                            System.out.println(imageFile);
                            try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)){
                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);
                            }catch(IOException ex){
                                
                            }
                        } catch (IOException ex) {
                        }
                    }
                    String extension = null;
                    if(file.lastIndexOf(".") > 0){
                        extension = file.substring(file.lastIndexOf(".") + 1);
                        StringBuilder hi = new StringBuilder(file);
                        if(file.startsWith("file://")){
                            hi.delete(0, 7);      
                        }
                        int lastIndexPeriod = hi.toString().lastIndexOf(".");
                        Log.p(hi.toString());
                        String ext = hi.toString();
                        try{
                            String namePic = saveFileToDevice(file,ext);
                    
                                 
                            btnValider.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    System.out.println(o.getProgress());
                                    if ((tfNomHotel.getText().length()==0))
                                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                                    else
                                    {
                                        try {
                                            Hotel t = new Hotel(tfNomHotel.getText().toString(), o.getProgress(), Integer.parseInt(tfNbrChambres.getText()), 
                                                    tfAdresse.getText().toString(), tfPays.getText().toString(), Integer.parseInt(tfTel.getText()), tfEmail.getText().toString(), namePic );
                                            if(ServiceHotel.getInstance().addTask(t,getQRImage(t.getNomHotel())))
                                            {
                                               Dialog.show("Success","Connection accepted",new Command("OK"));                                               
                                               new ListHotels(previous).show();
                                               refreshTheme();
                                            }else
                                                Dialog.show("ERROR", "Server error", new Command("OK"));
                                        } catch (NumberFormatException e) {
                                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                                        }
                                    }
                                }
                            });
                        }catch(Exception ex){}
                        }
                    });
                }
            });
        addAll(tfNomHotel,sp,hii, tfNbrChambres,tfAdresse,tfPays,tfTel,tfEmail,btnImg,btnValider);    
        
    }
      
    protected String saveFileToDevice(String hi, String ext) throws IOException{
        URI uri;
        try{
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            return hi;
        }catch(URISyntaxException ex){

        }
        return "hh";
      }

   
    private Image getQRImage(String data)
    {
      try
      {
        BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, 400, 400);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] image = new int[width*height];
        int i=0;
        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
            image[i++] = !matrix.get(x, y) ? 0xffffffff : 0xff000000;
          }
        }
        return new RGBImage(image, width, height);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return null;
    }

    private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}

    private Slider createStarRankSlider(Resources res) {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(1);
        starRank.setMaxValue(5);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte)0);
        Image fullStar = res.getImage("full.png");
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = res.getImage("emty.png");
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));         
        return starRank;
    }
    public void showStarPickingForm() {
        theme = UIManager.initFirstTheme("/theme");

        Form hi = new Form("Star Slider", new BoxLayout(BoxLayout.Y_AXIS));
        hi.add(FlowLayout.encloseCenter(createStarRankSlider(theme)));
        hi.show();
    
    }
}
