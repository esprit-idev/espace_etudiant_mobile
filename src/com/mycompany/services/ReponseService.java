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
import com.mycompany.entities.Reponse;
import static com.mycompany.services.ThreadService.instance;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author 21656
 */
public class ReponseService {
    public ArrayList<Reponse> reps;
    public static ReponseService instance;
    public boolean resultOK;
    private ConnectionRequest req;
    private ReponseService(){
        req = new ConnectionRequest();
    }
    
    public static ReponseService getInstance(){
        if (instance == null)
            instance= new ReponseService();
            
        return instance;
    }
    public ArrayList<Reponse> parseReponses(String jsonText){
        try{
        reps = new ArrayList<>();
        JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));        
            ArrayList<Map<String,Object>> list = (ArrayList<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj: list){
                Result result = Result.fromContent(obj);
                Reponse r = new Reponse();
                 
                
                r.setId(obj.get("id").toString());
                r.setReply(obj.get("reply").toString());
                
                //System.out.println(threadT);
                r.setReplyDate(obj.get("replyDate").toString());
                
                reps.add(r);
        }
        }catch(IOException ex){
            System.out.println(""+ex.getMessage());
        }
        return reps;
    }
    public ArrayList<Reponse> getAllReponses(int id){
        String url = "http://127.0.0.1:8000/reponse/AllReponses/"+id;
        req.setUrl(url);
        req.setPost(false);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reps = parseReponses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reps;
    }
    public boolean addReponse(Reponse r,int id){
        
        String url = Static.BASE_URL+"/reponse/addReponse/"+r.getReply()+"/"+id;
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

    public boolean delete(Reponse r) {
        String[] ids= split(r.getId(),".");
        String url = Static.BASE_URL+"/reponse/deleteReponse/"+Integer.parseInt(ids[0]);
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
