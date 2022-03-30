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
import com.mycompany.entities.DocumentFavoris;
import com.mycompany.uis.SessionManager;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServiceDocumentFavoris {
    public ConnectionRequest req;
    private static ServiceDocumentFavoris instance;
    private ArrayList<DocumentFavoris> favs=new ArrayList<>();
    
    private ServiceDocumentFavoris(){
        req=new ConnectionRequest();
    }

    public static ServiceDocumentFavoris getInstance() {
        if (instance==null)
            instance=new ServiceDocumentFavoris();
        return instance;
    }
    
    public ArrayList<DocumentFavoris> getAllPinnedDocs(){
        String url=Static.BASE_URL+"/allPinnedDocs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                favs=parseFav(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return favs;
    }
    
    private ArrayList<DocumentFavoris> parseFav(String jsonText){
        int currentUserID=SessionManager.getId();
        try {
        favs=new ArrayList<>();
        JSONParser jsonP=new JSONParser();
            Map<String,Object>docListJson=jsonP.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String,Object>>) docListJson.get("root");
            for(Map<String,Object> obj : list){
                //if(obj.get("url")==null){
                Result result = Result.fromContent(obj);
                int id=result.getAsInteger("id");
                int userId=result.getAsInteger("user/id");
                if(currentUserID==userId){
                    String matiere="",niveau="",url="",base64="";
                    float docId=Float.parseFloat(result.getAsString("document/id"));
                    String nom=result.getAsString("document/nom");
                    String date_insert=result.getAsString("document/date_insert");
                    String prop=result.getAsString("document/proprietaire");
                    if(result.getAsString("document/matiere/id")!=null)
                        matiere=result.getAsString("document/matiere/id");
                    if(result.getAsString("document/niveau/id")!=null)
                        niveau=result.getAsString("document/niveau/id");
                    if(result.getAsString("document/url")!=null)
                        url=result.getAsString("document/url");
                    if(result.getAsString("document/base64")!=null)
                        base64=result.getAsString("document/base64");
                    float signalements=Float.parseFloat(result.getAsString("document/signalements"));
                    Document doc=new Document((int) docId,nom,date_insert,prop,matiere,niveau,url,base64, (int) signalements);
                    DocumentFavoris f=new DocumentFavoris(id,userId,doc);
                    favs.add(f);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return favs;
    }
    
    public void DisplayDoc(Document doc){
        if(doc.getUrl().equals("")){
            byte[] byteArrray = doc.getBase64().getBytes();
            byte[] decoded = Base64.decode(byteArrray);
            FileSystemStorage fs = FileSystemStorage.getInstance();
            try {
                if(!fs.exists(fs.getAppHomePath()))
                    fs.mkdir(fs.getAppHomePath());
                OutputStream os = fs.openOutputStream(fs.getAppHomePath() + doc.getNom());
                os.write(decoded);
                Util.cleanup(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Display.getInstance().execute(fs.getAppHomePath() + doc.getNom());
        }
        else
            Display.getInstance().execute(doc.getUrl());
    }
    
    public void PinDoc(Document doc,int userId){
        //get user with id or change user instead of int in args
        String url = Static.BASE_URL+"/addPin/new?userId="+userId+"&docId="+doc.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void UnpinDoc(int docId,int userId) {
        String url = Static.BASE_URL +"/deletePin/"+docId+"/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
