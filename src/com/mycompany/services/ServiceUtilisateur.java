/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.ConnectionRequest ;
import com.codename1.io.JSONParser;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.mycompany.utils.Static;
import com.mycompany.entities.User;
import com.codename1.ui.util.Resources;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.mycompany.gui.AllStudents;
import com.mycompany.uis.ActivateForm;
import com.mycompany.uis.AddStudent;
import com.mycompany.uis.ListStudents;
import com.mycompany.uis.TabAff;
import com.mycompany.uis.Profile;
import com.mycompany.uis.SessionManager;

/**
 *
 * @author YOOSURF
 */
public class ServiceUtilisateur {
    
    public static ServiceUtilisateur instance = null ;
    
    public static boolean resultOK = true ;
     String json;
    
    private ConnectionRequest req ;
    
    public static ServiceUtilisateur getInstance(){
        if (instance == null)
            instance = new ServiceUtilisateur();
        return instance ;
    }
    
    public ServiceUtilisateur(){
        req = new ConnectionRequest();
    }
    
    //signin
    public void signin (TextField email, TextField password , Resources res){
     // String r = '"'+"ROLE_ADMIN"+'"';  
       String r="ROLE_ADMIN";
    String url = Static.BASE_URL+"/loginJson?email="+email.getText().toString()+"&password="+password.getText().toString();
   req = new ConnectionRequest(url , false); // false yaani url mezelt matba3thitch lel server
    req.setUrl(url);
   req.addResponseListener((e)->{
        
   JSONParser j = new JSONParser();
  // String json = new String(req.getResponseData())+"";
   json = new String(req.getResponseData()) + "";
  
   try{
  
   if(json.equals("password not found")||json.equals("user not found")){
       Dialog.show("Echec d'authentification","Email ou mot de passe éronné","OK", null);
   }
   
   else{
      // User student = new User();
       System.out.println("data =="+json);
       Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
    
    //if(SessionManager.pref.getIsBanned().equals(true) ){
       //  Dialog.show("Echec d'authentification","Votre compte est desactivé","OK", null);}
  //  else{
    
                          Result result = Result.fromContent(user);
String roles=result.getAsString("roles");
        float id = Float.parseFloat(user.get("id").toString());
                SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
                SessionManager.setPassowrd(user.get("password").toString());
                SessionManager.setUserName(user.get("username").toString());
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setPrenom(user.get("prenom").toString());
              //float roles =Float.parseFloat(user.get("roles").toString());
                SessionManager.setRoles(roles);
                
            //    System.out.println(roles);
  // System.out.println(SessionManager.getRoles());
                
               //System.out.println(user.get("roles"));

//System.out.println(roles.getClass().getName());
               
          new TabAff(res).show();

      
     //else{
      // new ActivateForm(res).show();  
    // }
  // }
   }
   }catch(Exception ex){
   ex.printStackTrace();}
   });
   
     NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    public String getPasswordByEmail(String email, Resources rs ) { 
       String url = Static.BASE_URL+"/getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
             json = new String(req.getResponseData()) + "";
           //   if(json.equals("user not found")){
    //   Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK", null);    
   //}
            //else{     
                    try {
            
   
      
                System.out.println("data =="+json);
                
                Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
   
   
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
              
           // } 
           
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
       NetworkManager.getInstance().addToQueueAndWait(req);
    return json;
    } 
    
    
    // edit user profil
    public static void EditUser(int id ,String username , String password ){
    
        String url =Static.BASE_URL+"/editProfileJson?id="+id+"&username="+username+"&password="+password ;
        MultipartRequest req = new MultipartRequest();
        
        req.setUrl(url);
        req.setPost(true);
      //  req.addArgument("id",String.valueOf(SessionManager.getId()));
        req.addArgument("username", username);
        req.addArgument("password", password);
        //req.addArgument("email", email);
        //System.out.println(email);
        req.addResponseListener((response)->{
        
            byte[] data = (byte[]) response.getMetaData() ;
            String s = new String(data) ;
            System.out.println(s);
            
           
        });

    NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
