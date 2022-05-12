/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.company.myapp.Entities.Resteau;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public class ServiceRestau {
     public ArrayList<Resteau> Restaurant;
    
    public static ServiceRestau instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceRestau() {
         req = new ConnectionRequest();
    }

    public static ServiceRestau getInstance() {
        if (instance == null) {
            instance = new ServiceRestau();
        }
        return instance;
    }

    public boolean addRestau(Resteau R) {
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "addRestau?nomr=" + R.getNomR() + "&adressr=" + R.getAdressR()+ "&typer=" + R.getTypeR()
              + "&paysr=" + R.getPaysR()+  "&telr=" + R.getTelR()+ "&imgr=" + R.getImgR();

       req.setUrl(url);
       req.setPost(false);
       req.addArgument("Nom",R.getNomR());
       req.addArgument("Adress", R.getAdressR()+"");
       req.addArgument("Type", R.getTypeR()+"");
       req.addArgument("pays ", R.getPaysR()+"");
       req.addArgument("tel", R.getTelR()+"");
       req.addArgument("image",  R.getImgR()+"");
         System.out.println(req);
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
  public ArrayList<Resteau> parseTasks(String jsonText){
        try {
           ArrayList <Resteau>Restaurant=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Resteau R = new Resteau();
              
                R.setTypeR((obj).get("typer").toString());
                R.setNomR((obj).get("nomr").toString());
                R.setAdressR((obj).get("adressr").toString());
                R.setPaysR((obj).get("paysr").toString());
                R.setTelR((obj).get("telr").toString());
                R.setImgR((obj).get("imgr").toString());
                  float idr = Float.parseFloat(obj.get("idr").toString());
                R.setIdR((int)idr);
               // R.setNomR((int)Float.parseFloat(obj.get("status").toString())));
                       System.out.println(R);

                if (obj.get("nomr")==null)
              R.setNomR("null");
                else
                    R.setNomR(obj.get("nomr").toString());
                Restaurant.add(R);
            }
                 return Restaurant;
     
        } catch (IOException ex) {

        }

      return Restaurant;
    }
    
    public ArrayList<Resteau> getAllRestau(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"test/";
        System.out.println("===>"+url);

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               Restaurant = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return Restaurant;
        
    }
  
 public boolean deleteRestau(int idr){
        String url = Statics.BASE_URL+"deleteRestau?idr="+idr;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }
    
    public boolean updateResteau(Resteau R){
        String url = Statics.BASE_URL +"updateResteau?idr="+ R.getIdR()+ "&nomr=" + R.getNomR()+ "&adressr=" + R.getAdressR()+ "&typer=" + R.getTypeR() + "&paysr=" + R.getPaysR()+ "&telr=" + R.getTelR()+ "&imgr=" + R.getImgR();
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
    // new MapForm(); 
    
}
