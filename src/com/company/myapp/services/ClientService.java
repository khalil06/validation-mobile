package com.company.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Client;
import com.company.myapp.Entities.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientService {

    public static ClientService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Client> listClients;

    

    private ClientService() {
        cr = new ConnectionRequest();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }
    
    public ArrayList<Client> getAll() {
        listClients = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/client");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listClients = getList();
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

        return listClients;
    }

    private ArrayList<Client> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Client client = new Client(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        (String) obj.get("name"),
                        (String) obj.get("email")
                        
                );

                listClients.add(client);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listClients;
    }
    
    public int add(Client client) {
        return manage(client, false);
    }

    public int edit(Client client) {
        return manage(client, true );
    }

    public int manage(Client client, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/client/edit");
            cr.addArgument("id", String.valueOf(client.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/client/add");
        }
        
        cr.addArgument("name", client.getName());
        cr.addArgument("email", client.getEmail());
        
        
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

    public int delete(int clientId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/client/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(clientId));

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
