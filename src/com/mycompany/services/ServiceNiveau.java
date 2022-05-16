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
import com.mycompany.entities.Niveau;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServiceNiveau {
    public ConnectionRequest req;
    private static ServiceNiveau instance;
    private ArrayList<Niveau> niveaux=new ArrayList<>();
    
    private ServiceNiveau(){
        req=new ConnectionRequest();
    }

    public static ServiceNiveau getInstance() {
        if (instance==null)
            instance=new ServiceNiveau();
        return instance;
    }
    
    public ArrayList<Niveau> getAllNiveaux(){
        String url=Static.BASE_URL+"/niveaujson";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                niveaux=parseNiveau(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return niveaux;
    }
    
    private ArrayList<Niveau> parseNiveau(String jsonText){
        try {
        niveaux=new ArrayList<Niveau>();
        JSONParser jsonP=new JSONParser();
            Map<String,Object>niveauListJson=jsonP.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String,Object>>) niveauListJson.get("root");
            for(Map<String,Object> obj : list){
                String id=obj.get("id").toString();
                Niveau n=new Niveau(id);
                niveaux.add(n);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return niveaux;
    }
    
    
    
    public void DeleteNiveau(String id){
        String url=Static.BASE_URL+"/suppn?id="+id;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    
       public void AddNiveau(String id){
        String url=Static.BASE_URL+"/addniveau?id="+id;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
}
