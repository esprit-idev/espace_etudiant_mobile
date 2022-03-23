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
import com.mycompany.entities.Matiere;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MeriamBI
 */
public class ServiceMatiere {
    public ConnectionRequest req;
    private static ServiceMatiere instance;
    private ArrayList<Matiere> matieres=new ArrayList<>();
    
    private ServiceMatiere(){
        req=new ConnectionRequest();
    }

    public static ServiceMatiere getInstance() {
        if (instance==null)
            instance=new ServiceMatiere();
        return instance;
    }
    
    public ArrayList<Matiere> getAllMatieres(){
        String url=Static.BASE_URL+"/allMatieres";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                matieres=parseMatiere(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return matieres;
    }
    
    private ArrayList<Matiere> parseMatiere(String jsonText){
        try {
        matieres=new ArrayList<>();
        JSONParser jsonP=new JSONParser();
            Map<String,Object>matiereListJson=jsonP.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String,Object>>) matiereListJson.get("root");
            for(Map<String,Object> obj : list){
                //if(obj.get("url")==null){
                
                String id=obj.get("id").toString();
                String niveau=obj.get("niveau").toString();
                Matiere m=new Matiere(id,niveau);
                matieres.add(m);
                //}
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return matieres;
    }
}
