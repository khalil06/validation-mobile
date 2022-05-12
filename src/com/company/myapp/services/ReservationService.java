package com.company.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Client;
import com.company.myapp.Entities.Reservation;
import com.company.myapp.Entities.Vol;
import com.company.myapp.Entities.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationService {

    public static ReservationService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Reservation> listReservations;

    

    private ReservationService() {
        cr = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }
    
    public ArrayList<Reservation> getAll() {
        listReservations = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/reservation");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listReservations = getList();
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

        return listReservations;
    }

    private ArrayList<Reservation> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Reservation reservation = new Reservation(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makeClient((Map<String, Object>) obj.get("client")),
                        makeVol((Map<String, Object>) obj.get("vol")),
                        (String) obj.get("type"),
                        (String) obj.get("classe"),
                        Float.parseFloat(obj.get("finalPrice").toString())
                        
                );

                listReservations.add(reservation);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listReservations;
    }
    
    public Client makeClient(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Client client = new Client();
        client.setId((int) Float.parseFloat(obj.get("id").toString()));
        client.setName((String) obj.get("name"));
        return client;
    }
    
    public Vol makeVol(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Vol vol = new Vol();
        vol.setId((int) Float.parseFloat(obj.get("id").toString()));
        vol.setDestination((String) obj.get("destination"));
        return vol;
    }
    
    public int add(Reservation reservation) {
        return manage(reservation, false);
    }

    public int edit(Reservation reservation) {
        return manage(reservation, true );
    }

    public int manage(Reservation reservation, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL_AMIR + "/reservation/edit");
            cr.addArgument("id", String.valueOf(reservation.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL_AMIR + "/reservation/add");
        }
        
        cr.addArgument("client", String.valueOf(reservation.getClient().getId()));
        cr.addArgument("vol", String.valueOf(reservation.getVol().getId()));
        cr.addArgument("type", reservation.getType());
        cr.addArgument("classe", reservation.getClasse());
        cr.addArgument("finalPrice", String.valueOf(reservation.getFinalPrice()));
        
        
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

    public int delete(int reservationId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/reservation/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(reservationId));

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
