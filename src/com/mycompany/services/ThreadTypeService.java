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
import static com.codename1.io.Util.split;
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.ThreadType;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author 21656
 */
public class ThreadTypeService {
    public ArrayList<ThreadType> threadTypes;
    public static ThreadTypeService instance;
    public boolean resultOK;
    private ConnectionRequest req;
    private ThreadTypeService(){
        req = new ConnectionRequest();
    }
    public static ThreadTypeService getInstance(){
        if (instance == null)
            instance= new ThreadTypeService();
            
        return instance;
    }
    public ArrayList<ThreadType> parseThreadType(String jsonText){
        try{
        threadTypes = new ArrayList<>();
        JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));        
            ArrayList<Map<String,Object>> list = (ArrayList<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj: list){
                Result result = Result.fromContent(obj);
                ThreadType t = new ThreadType(); 
                 
                
                t.setId(obj.get("id").toString());
                t.setContent(obj.get("content").toString());
                threadTypes.add(t);
        }
        }catch(IOException ex){
            System.out.println(""+ex.getMessage());
        }
        return threadTypes;
    }
    public ArrayList<ThreadType> getAllThreadType(){
        String url = "http://127.0.0.1:8000/threadtype/getAll";
        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                threadTypes = parseThreadType(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return threadTypes;
    }

    

    public boolean delete(ThreadType t) {
        String[] ids= split(t.getId(),".");
        String url = "http://127.0.0.1:8000/threadtype/deleteJSON/"+Integer.parseInt(ids[0]);
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
        @Override
        public void actionPerformed(NetworkEvent evt){
            resultOK = req.getResponseCode()==200;
        }
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public boolean update(ThreadType t, String q){
        String[] ids= split(t.getId(),".");
        String url = "http://127.0.0.1:8000/threadtype/UpdateJSON/"+Integer.parseInt(ids[0])+"/"+q;
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
        @Override
        public void actionPerformed(NetworkEvent evt){
            resultOK = req.getResponseCode()==200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    

    public boolean addTopic(ThreadType t) {
        String url = Static.BASE_URL+"/threadtype/addTopic/"+t.getContent();
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
        @Override
        public void actionPerformed(NetworkEvent evt){
            resultOK = req.getResponseCode()==200;
        }
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
}
