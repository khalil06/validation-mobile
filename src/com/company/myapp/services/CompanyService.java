package com.company.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.myapp.Entities.Company;
import com.company.myapp.Entities.*;
import com.company.myapp.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompanyService {

    public static CompanyService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Company> listCompanys;

    

    private CompanyService() {
        cr = new ConnectionRequest();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }
    
    public ArrayList<Company> getAll() {
        listCompanys = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/company");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCompanys = getList();
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

        return listCompanys;
    }

    private ArrayList<Company> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Company company = new Company(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        (String) obj.get("name"),
                        (String) obj.get("image"),
                        (String) obj.get("description")
                        
                );

                listCompanys.add(company);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listCompanys;
    }
    
    public int add(Company company) {
        return manage(company, false, true);
    }

    public int edit(Company company, boolean imageEdited) {
        return manage(company, true , imageEdited);
    }

    public int manage(Company company, boolean isEdit, boolean imageEdited) {
        
        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Company.jpg");

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL_AMIR + "/company/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(company.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL_AMIR + "/company/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", company.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", company.getImage());
        }

        cr.addArgumentNoEncoding("name", company.getName());
        cr.addArgumentNoEncoding("image", company.getImage());
        cr.addArgumentNoEncoding("description", company.getDescription());
        

        
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

    public int delete(int companyId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_AMIR + "/company/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(companyId));

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
