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
import com.mycompany.entities.User;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author YOOSURF
 */
public class ServiceAdmin {
    
    
      public static ServiceAdmin instance = null ;
    
    private ConnectionRequest req ;
    public static ServiceAdmin getInstance(){
    if (instance == null)
        instance = new ServiceAdmin();
    return instance ;
    }
    
    public ServiceAdmin (){
        req = new ConnectionRequest ();
    }
    
    //affichage
    public ArrayList<User>affichageAdmin(){
    ArrayList<User> result = new ArrayList<>();
    String url = Statics.BASE_URL+"/afficheAdmin";
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>(){
   
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp ;
            jsonp = new JSONParser ();
            try {
            Map<String,Object>mapUsers = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapUsers.get("root");
           
            for(Map<String,Object> obj : listOfMaps){
            User admin = new User ();
            float id =Float.parseFloat(obj.get("id").toString());
            String username = obj.get("username").toString();
             String prenom = obj.get("prenom").toString();
              String email = obj.get("email").toString();
              admin.setId((int)id);
              admin.setUsername(username);
              admin.setPrenom(prenom);
              admin.setEmail(email);
            result.add(admin);
            }
            }
           catch(Exception ex){
           ex.printStackTrace();}
        }
    
    });
       NetworkManager.getInstance().addToQueueAndWait(req);
       return result ;
    }
    
    private ConnectionRequest connectionRequest;
     //ajout 
    public void AddAdmin(User user)
    //public void AjoutStu(User user)
    {
        connectionRequest=new ConnectionRequest();
       

       //  String url =Statics.BASE_URL+"/addStudentJSON?&username="+ user.getUsername()+"&prenom="+prenom.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString();
       // String url =Statics.BASE_URL+"/addReclamation?objet="+reclamation.getObjet()+"&description="+reclamation.getDescription()+"&user="+reclamation.getIduser(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        //req.setUrl(url);
        connectionRequest.addResponseListener((e) -> {
            
            String str = new String(connectionRequest.getResponseData()); //Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
       
         connectionRequest.setUrl(Statics.BASE_URL+"/addAdminJSON?&username=" + user.getUsername() +"&prenom=" + user.getPrenom()+"&email="+user.getEmail()+"&password="+user.getPassword());
        NetworkManager.getInstance().addToQueue(connectionRequest);
       // NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
      public void deleteAdmin(int id ) {
        String url = Statics.BASE_URL +"/deleteAdminJson?id="+id;
        
        req.setUrl(url);
        
          req.addResponseListener((e)-> 
        {
           String str=new String(req.getResponseData());
          System.out.println("data=="+str);


        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
      
       //Update 
    public boolean modifierAdmin(User user) {
        String url = Statics.BASE_URL +"/updateAdminJSON?id="+user.getId()+"&username="+user.getUsername()+"&prenom="+user.getPrenom()+"&email="+user.getEmail()+"&password="+user.getPassword()+"&isbanned="+user.getIsBanned();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                boolean result = req.getResponseCode() == 200; // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
   boolean result = req.getResponseCode() == 200;
       // boolean resultOk;
   return result ;
        
    }
    
}
