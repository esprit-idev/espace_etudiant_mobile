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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Classe;
import com.mycompany.entities.User;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aa
 */
public class ServiceClasse {
    public ConnectionRequest req;
    private static ServiceClasse instance;
    private ArrayList<Classe> classe=new ArrayList<>();
      private ServiceClasse(){
        req=new ConnectionRequest();
    }
      public static ServiceClasse getInstance() {
        if (instance==null)
            instance=new ServiceClasse ();
        return instance;
    }
      
      
      public ArrayList<Classe> getAllClasse(){
          ArrayList<Classe>result = new ArrayList<>();
        String url=Static.BASE_URL+"/classejson";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json  = new JSONParser();
                try{
                        Map<String,Object> ClasseListJson = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        List<Map<String,Object>>listOfMaps= (List<Map<String,Object>>) ClasseListJson.get("root");
                        for(Map<String,Object> obj : listOfMaps)
                        {
      //                //Création des tâches et récupération de leurs données
                          String id=obj.get("id").toString();
                String niveau=obj.get("niveau_id").toString();
                String classe=obj.get("classe").toString();
                
                Classe m=new Classe(id,niveau,classe);
                            
                                
                            
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
      
      
      
        public void DeleteClasse(String id){
        String url=Static.BASE_URL+"/suppclasse?id="+id;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
      
      
      
             public void AddClasse(String classe,String niveau){
        String url=Static.BASE_URL+"/addclasse?classe="+classe+"&niveau="+niveau;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
      
      
           public void UpdateClasse(String id,String classe,String niveau){
        String url=Static.BASE_URL+"/updateclasse?classe="+classe+"&niveau="+niveau+"&id="+id;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
      
      
     public ArrayList<User> viewClasse(String id){
           ArrayList<User>result = new ArrayList<>();
        String url=Static.BASE_URL+"/listeclasse?id="+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json  = new JSONParser();
                try{
                        Map<String,Object> ClasseListJson = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        List<Map<String,Object>>listOfMaps= (List<Map<String,Object>>) ClasseListJson.get("root");
                        for(Map<String,Object> obj : listOfMaps)
                        {
      //                //Création des tâches et récupération de leurs données
                         
                String username=obj.get("username").toString();
                String prenom=obj.get("prenom").toString();
                String email=obj.get("email").toString();
                
                User u=new User();
                u.setEmail(email);
                u.setUsername(username);
                u.setPrenom(prenom);
                            
                                
                            
                            result.add(u);
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

     
     
      public ArrayList<String> add2classe(String id,String email){
         
          ArrayList<String>result = new ArrayList<>();
        String url=Static.BASE_URL+"/addtoclasse?id="+id+"&email="+email;
        req.setUrl(url);
  
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json  = new JSONParser();
                try{
                        Map<String,Object> ClasseListJson = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        List<Map<String,Object>>listOfMaps= (List<Map<String,Object>>) ClasseListJson.get("root");
                        for(Map<String,Object> obj : listOfMaps)
                        {
      //                //Création des tâches et récupération de leurs données
                         
                String r=obj.get("done").toString();
                
                
                
                                
                            
                            result.add(r);
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
      
      
      
          public void DeleteS(String email){
        String url=Static.BASE_URL+"/etudiantsuppclasse?email="+email;
        req.setUrl(url);
 req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
                      
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
      
          
                   public ArrayList<com.mycompany.chat.Classe> getClasse(int uid){
          ArrayList<com.mycompany.chat.Classe>result = new ArrayList<>();
        String url=Static.BASE_URL+"/classefromuid?uid=" + uid;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json  = new JSONParser();
                try{
                        Map<String,Object> ClasseListJson = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        List<Map<String,Object>>listOfMaps= (List<Map<String,Object>>) ClasseListJson.get("root");
                        for(Map<String,Object> obj : listOfMaps)
                        {
      //                //Création des tâches et récupération de leurs données
                          String id=obj.get("classe").toString();

                
                com.mycompany.chat.Classe m=new com.mycompany.chat.Classe();
                m.setId((int)Float.parseFloat(id));
                            
                                
                            
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
      
      
}
