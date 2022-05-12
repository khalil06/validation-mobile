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
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Activite;
import com.company.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceActivite {

    public ArrayList<Activite> activites;
    
    public static ServiceActivite instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceActivite() {
         req = new ConnectionRequest();
    }

    public static ServiceActivite getInstance() {
        if (instance == null) {
            instance = new ServiceActivite();
        }
        return instance;
    }

    public boolean addActivite(Activite t) {

        System.out.println(t);
        System.out.println("********");
     //  String url = Statics.BASE_URL+"addActivite?nomActivite=" + t.getNom_activite() + "&typeActivite=" +
        //      t.getType_activite()+"&prix="+String.valueOf(t.getPrix())+"&adresse="+t.getAdresse()+"&pays="+t.getPays();

       String url = Statics.BASE_URL + "addActivite";
       req.setUrl(url);
       req.setPost(false);
        req.addArgument("nomActivite", t.getNom_activite());
        req.addArgument("typeActivite", t.getType_activite());
        req.addArgument("prix", String.valueOf(t.getPrix()));
        req.addArgument("adresse", t.getAdresse());
        req.addArgument("pays", t.getPays());


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

    public ArrayList<Activite> parseActivite(String jsonText){
        try {
            activites =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> ActiviteListJson =
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)ActiviteListJson.get("root");
            for(Map<String,Object> obj : list){
                Activite t = new Activite();
                String nomActivite =obj.get("nomActivite").toString();
                t.setNom_activite(nomActivite);
                String typeActivite =obj.get("typeActivite").toString();
                t.setType_activite(typeActivite);
                float prix = Float.parseFloat(obj.get("prix").toString());
                t.setPrix(prix);
                String adresse =obj.get("adresse").toString();
                t.setAdresse(adresse);
                String pays =obj.get("pays").toString();
                t.setPays(pays);
                activites.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return activites;
    }
    
    public ArrayList<Activite> getAllActivite(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/Activite/";
        String url = Statics.BASE_URL+"index/";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                activites = parseActivite(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return activites;
    }
}
