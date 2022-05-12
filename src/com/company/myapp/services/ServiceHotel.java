/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Hotel;
import com.company.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author dorsaf
 */
public class ServiceHotel {
    public ArrayList<Hotel> tasks;
    
    public static ServiceHotel instance=null;
    public static boolean resultOK;
    private ConnectionRequest req;
    private static String nom;

    private ServiceHotel() {
         req = new ConnectionRequest();
    }

    public static ServiceHotel getInstance() {
        if (instance == null) {
            instance = new ServiceHotel();
        }
        return instance;
    }
    public String[] split(String str)
{
    ArrayList<String> splitArray = new ArrayList<>();
    StringTokenizer arr = new StringTokenizer(str, "//");//split by commas
    while(arr.hasMoreTokens())
        splitArray.add(arr.nextToken());
    return splitArray.toArray(new String[splitArray.size()]);
}
     public boolean addTask(Hotel t, Image qrImage) {
         String nomImg = addImage(qrImage, t.getImage());
         if(nomImg == null)
             return false;
         String[] split = split(nomImg);
       String url = Statics.BASE_URL + "addHotell?nomHotel=" + t.getNomHotel() + "&nbrEtoiles=" + t.getNbrEtoiles()+ "&nbrChambres=" + t.getNbrChambres()
               + "&adresse=" + t.getAdresse()+ "&pays=" + t.getPays()+ "&tel=" + t.getTel()+ "&email=" + t.getEmail();
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("Nom", t.getNomHotel());
       req.addArgument("Nbr et", t.getNbrEtoiles()+"");
       req.addArgument("Nbr cham", t.getNbrChambres()+"");
       req.addArgument("adresse ", t.getAdresse()+"");
       req.addArgument("pays", t.getPays()+"");
       req.addArgument("tel", t.getTel()+"");
       req.addArgument("email", t.getEmail()+"");
       req.addArgument("image", split[0].trim());
         System.out.println(split[0]);
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public String addImage(Image qrImage, String hotelImg) {
        MultipartRequest cr = new MultipartRequest();
        EncodedImage em = EncodedImage.createFromImage(qrImage, true);
        byte[] data = em.getImageData();
        cr.setUrl(Statics.BASE_URL + "addQrImage");
        cr.setPost(true);
        String mime="image/jpeg";
        try {
            cr.addData("file", data, mime);
            System.out.println(hotelImg);
            cr.addData("hotel", hotelImg, mime);
        } catch (Exception ex) {
            return null;
        }
        cr.setFilename("file", "MyImage.jpg");//any unique name you want
        cr.setFilename("hotel", "Hotel.jpg");//any unique name you want
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("aaaaaa");
                if( cr.getResponseCode() == 200){
                   nom = new String(cr.getResponseData());
                    System.out.println(nom);
                }
                 cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return nom;
    }
    
     public ArrayList<Hotel> parseTasks(String jsonText){
        ArrayList<Hotel>tasks=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Hotel t = new Hotel();
                float id = Float.parseFloat(obj.get("idHotel").toString());
                t.setIdHotel((int)id);
                t.setNomHotel(obj.get("nomHotel").toString());
                t.setNbrEtoiles(((int)Float.parseFloat(obj.get("nbrEtoiles").toString())));
                t.setNbrChambres(((int)Float.parseFloat(obj.get("nbrChambres").toString())));
                t.setAdresse(obj.get("adresse").toString());
                t.setPays(obj.get("pays").toString());
                t.setTel(((int)Float.parseFloat(obj.get("tel").toString())));
                t.setEmail(obj.get("email").toString());
                t.setImage(obj.get("image").toString());
                if (obj.get("nomHotel")==null)
                    t.setNomHotel("null");
                else
                    t.setNomHotel(obj.get("nomHotel").toString());
                tasks.add(t);
            }
        } catch (IOException ex) {
        }
        return tasks;
    }
    
    public ArrayList<Hotel> getAllTasks(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"HotelMobile/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
   
    
    
    public boolean deleteHotel(int id){
        String url = Statics.BASE_URL+"deleteHotell?idHotel="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }


    public boolean updateHotel(Hotel t){
        String url = Statics.BASE_URL + "updateHotell?idHotel="+t.getIdHotel()+"&nomHotel=" + t.getNomHotel() + "&nbrEtoiles=" + t.getNbrEtoiles()+ "&nbrChambres=" + t.getNbrChambres()
            + "&adresse=" + t.getAdresse()+ "&pays=" + t.getPays()+ "&tel=" + t.getTel()+ "&email=" + t.getEmail()+ "&image=" + t.getImage();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;

    }
   
    public ArrayList<Hotel> searchh(String nom){
        ConnectionRequest req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL + "searchh?searchValue="+nom;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

}
        
