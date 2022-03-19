/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.processing.Result;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Base64;
import com.mycompany.entities.Document;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceDocument {

    public ConnectionRequest req;
    private static ServiceDocument instance;
    public boolean resultOk;
    private ArrayList<Document> docs = new ArrayList<>();

    private ServiceDocument() {
        req = new ConnectionRequest();
    }

    public static ServiceDocument getInstance() {
        if (instance == null) {
            instance = new ServiceDocument();
        }
        return instance;
    }

    public ArrayList<Document> getAllDocs() {
        String url = Static.BASE_URL + "/allDocs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                docs = parseDocument(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return docs;
    }

    private ArrayList<Document> parseDocument(String jsonText) {
        try {
            docs = new ArrayList<>();
            JSONParser jsonP = new JSONParser();
            Map<String, Object> docListJson = jsonP.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) docListJson.get("root");
            for (Map<String, Object> obj : list) {
                //if(obj.get("url")==null){
                Result result = Result.fromContent(obj);
                String matiere = "", niveau = "", url = "", base64 = "";
                float id = Float.parseFloat(obj.get("id").toString());
                String nom = obj.get("nom").toString();
                String date_insert = obj.get("date_insert").toString();
                String prop = obj.get("proprietaire").toString();
                if (result.getAsString("matiere/id") != null) {
                    matiere = result.getAsString("matiere/id");
                }
                if (result.getAsString("niveau/id") != null) {
                    niveau = result.getAsString("niveau/id");
                }
                if (obj.get("url") != null) {
                    url = obj.get("url").toString();
                }
                if (obj.get("base64") != null) {
                    base64 = obj.get("base64").toString();
                }
                float signalements = Float.parseFloat(obj.get("signalements").toString());
                Document d = new Document((int) id, nom, date_insert, prop, matiere, niveau, url, base64, (int) signalements);
                docs.add(d);
                //}
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return docs;
    }

    public void DisplayDoc(Document doc) {
        if (doc.getUrl().equals("")) {
            byte[] byteArrray = doc.getBase64().getBytes();
            byte[] decoded = Base64.decode(byteArrray);
            FileSystemStorage fs = FileSystemStorage.getInstance();
            try {
                if (!fs.exists(fs.getAppHomePath())) {
                    fs.mkdir(fs.getAppHomePath());
                }
                OutputStream os = fs.openOutputStream(fs.getAppHomePath() + doc.getNom());
                os.write(decoded);
                Util.cleanup(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(fs.getAppHomePath() + doc.getNom());
            Display.getInstance().execute(fs.getAppHomePath() + doc.getNom());
        } else {
            Display.getInstance().execute(doc.getUrl());
        }
    }

    public boolean addUrl(Document doc) {
        String url = Static.BASE_URL + "/addUrl/new?nom=" + doc.getNom() + "&date_insert=" + doc.getDate_insert() + "&proprietaire=" + doc.getProp() + "&matiere=" + doc.getMatiere() + "&niveau=" + doc.getNiveau() + "&url=" + doc.getUrl() + "&base64=" + doc.getBase64();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean deleteDoc(int id) {
        String url = Static.BASE_URL+"/deleteDoc/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean signalDoc(Document doc) {
        String url = Static.BASE_URL+"/signalDoc/"+doc.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    public boolean shareDoc(Document doc,String destEmail,String body,String subject) {
        String userEmail="meriamesprittest@gmail.com"; //to_chnage
        String url = Static.BASE_URL+"/shareDoc/"+doc.getId()+"?userEmail=" + userEmail + "&destEmail=" + destEmail + "&body=" + body + "&subject=" + subject;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
}
