package com.company.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Company;
import com.company.myapp.Entities.Vol;
import com.company.myapp.Entities.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VolService {

    public static VolService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Vol> listVols;

    

    private VolService() {
        cr = new ConnectionRequest();
    }

    public static VolService getInstance() {
        if (instance == null) {
            instance = new VolService();
        }

        return instance;
    }
    
    public ArrayList<Vol> getAll() {
        listVols = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/vol");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listVols = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listVols;
    }

    private ArrayList<Vol> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Vol vol = new Vol(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makeCompany((Map<String, Object>) obj.get("company")),
                        (String) obj.get("origin"),
                        (String) obj.get("destination"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("departureDate")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("arrivalDate")),
                        (int) Float.parseFloat(obj.get("status").toString()),
                        Float.parseFloat(obj.get("initialPrice").toString())
                        
                );

                listVols.add(vol);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listVols;
    }
    
    public Company makeCompany(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Company company = new Company();
        company.setId((int) Float.parseFloat(obj.get("id").toString()));
        company.setName((String) obj.get("name"));
        return company;
    }
    
    public int add(Vol vol) {
        return manage(vol, false);
    }

    public int edit(Vol vol) {
        return manage(vol, true );
    }

    public int manage(Vol vol, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL_AMIR + "/vol/edit");
            cr.addArgument("id", String.valueOf(vol.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL_AMIR + "/vol/add");
        }
        
        cr.addArgument("company", String.valueOf(vol.getCompany().getId()));
        cr.addArgument("origin", vol.getOrigin());
        cr.addArgument("destination", vol.getDestination());
        cr.addArgument("departureDate", new SimpleDateFormat("dd-MM-yyyy").format(vol.getDepartureDate()));
        cr.addArgument("arrivalDate", new SimpleDateFormat("dd-MM-yyyy").format(vol.getArrivalDate()));
        cr.addArgument("status", String.valueOf(vol.getStatus()));
        cr.addArgument("initialPrice", String.valueOf(vol.getInitialPrice()));
        
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int volId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/vol/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(volId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
