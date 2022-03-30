/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import static com.codename1.io.Util.split;
import com.codename1.util.StringUtil;
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Thread;
import com.mycompany.utils.Static;
import com.mycompany.entities.ThreadType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author 21656
 */
public class ThreadService {
    
    public ArrayList<Thread> threads;
    public static ThreadService instance;
    public boolean resultOK;
    private ConnectionRequest req;
    private ThreadService(){
        req = new ConnectionRequest();
    }
    public static ThreadService getInstance(){
        if (instance == null)
            instance= new ThreadService();
            
        return instance;
    }
    
    public boolean addThread(Thread t,String type,int id){
        String url = Static.BASE_URL+"/thread/addThread/"+t.getQuestion()+"/"+id+"/"+type;
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
    public ArrayList<Thread> parseThreads(String jsonText){
        try{
        threads = new ArrayList<>();
        JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));        
            ArrayList<Map<String,Object>> list = (ArrayList<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj: list){
                Result result = Result.fromContent(obj);
                Thread t = new Thread();
                 
                
                t.setId(obj.get("id").toString());
                t.setQuestion(obj.get("question").toString());
                ArrayList ts = (ArrayList) obj.get("reponses");
                String threadT = obj.get("threadType").toString().substring(9,(obj.get("threadType").toString().length())-1);
                //System.out.println(threadT);
                t.setThreadType(threadT);
                t.setReponses(ts);
                t.setVerified(obj.get("Verified").toString());
                System.out.print(ts);
                threads.add(t);
        }
        }catch(IOException ex){
            System.out.println(""+ex.getMessage());
        }
        return threads;
    }
    public ArrayList<Thread> getAllThreads(){
        String url = "http://127.0.0.1:8000/thread/AllThreads";
        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                threads = parseThreads(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return threads;
    }
    public ArrayList<Thread> getThread(int id){
        String url = "http://127.0.0.1:8000/thread/getThread/"+id;
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                threads = parseThreads(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return threads;
    }

    public boolean delete(Thread t) {
        String[] ids= split(t.getId(),".");
        String url = "http://127.0.0.1:8000/thread/displayJSON/"+Integer.parseInt(ids[0]);
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
    
    
    
    public boolean update(Thread t,String q){
        String[] ids= split(t.getId(),".");
        String url = "http://127.0.0.1:8000/thread/UpdateJSON/"+Integer.parseInt(ids[0])+"/"+q;
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

    public boolean verify(Thread t) {
        String[] ids= split(t.getId(),".");
        String url = "http://127.0.0.1:8000/thread/verifyJSON/"+Integer.parseInt(ids[0]);
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
