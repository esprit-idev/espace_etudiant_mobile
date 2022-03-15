/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Document;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MeriamBI
 */
public class ServiceDocument {
    public ConnectionRequest req;
    private static ServiceDocument instance;
    private ArrayList<Document> docs=new ArrayList<>();
    
    private ServiceDocument(){
        req=new ConnectionRequest();
    }

    public static ServiceDocument getInstance() {
        if (instance==null)
            instance=new ServiceDocument();
        return instance;
    }
    
    public ArrayList<Document> getAllDocs(){
        String url=Static.BASE_URL+"/allDocs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                docs=parseDocument(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return docs;
    }
    
    private ArrayList<Document> parseDocument(String jsonText){
        try {
        docs=new ArrayList<>();
        JSONParser jsonP=new JSONParser();
            Map<String,Object>docListJson=jsonP.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String,Object>>) docListJson.get("root");
            for(Map<String,Object> obj : list){
                //if(obj.get("url")==null){
                String matiere="",niveau="",url="";
                String nom=obj.get("nom").toString();
                String date_insert=obj.get("date_insert").toString();
                String prop=obj.get("proprietaire").toString();
                if(obj.get("matiere")!=null)
                    matiere=obj.get("matiere").toString();
                if(obj.get("niveau")!=null)
                    niveau=obj.get("niveau").toString();
                if(obj.get("url")!=null)
                    url=obj.get("url").toString();
                Document d=new Document(nom,date_insert,prop,matiere,niveau,url);
                docs.add(d);
                //}
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return docs;
    }
    
}
