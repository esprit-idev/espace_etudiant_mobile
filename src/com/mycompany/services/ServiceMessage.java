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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Message;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author aa
 */
public class ServiceMessage {
    
      public static ServiceMessage instance=null;
    
    private ConnectionRequest req;
    
        public static ServiceMessage getInstance(){
        if( instance ==null)
            instance = new ServiceMessage();
        return instance;
        
    }
    
    public ServiceMessage(){
        req = new ConnectionRequest();
    }
    

        
        
      
         
         
        
         
         
         public ArrayList<Message>afficheMessage(int ui){
            
    
        ArrayList<Message>result = new ArrayList<>();
        String url = Static.BASE_URL+"/m?cid="+ui;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() 
        {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                JSONParser json;
                json  = new JSONParser();
                try{
                        Map<String,Object> MessageListJson = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        List<Map<String,Object>>listOfMaps= (List<Map<String,Object>>) MessageListJson.get("root");
                        for(Map<String,Object> obj : listOfMaps)
                        {
      //                //Création des tâches et récupération de leurs données
                            Message m = new Message();
                             
                   
                            
                                float id = Float.parseFloat(obj.get("id").toString());
                        //title
                        float classe = Float.parseFloat(obj.get("classe").toString());
                        //owner
                        float user = Float.parseFloat(obj.get("user").toString());
                        //content
                        String content = obj.get("content").toString();
                        //date
                        String date = obj.get("date").toString();
                        
                            m.setId((int)id);
                            m.setContent(content);
                            m.setUser((int)user);
                            m.setClasse((int)classe);
                            m.setPostDate(date);
                          
                            
                            result.add(m);
                        }
                    } 

                 catch(Exception ex){
                    ex.printStackTrace();

                 }

            }


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
        
        
        
    
    }
         
         
                  public void sendMessage(int uid,String content){
    
        ArrayList<Message>result = new ArrayList<>();
        String url = Static.BASE_URL+"/addconversation?content="+content+"&uid="+uid;
        req.setUrl(url);
        
        req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    
    }
         
         
         
      
          
    
}
