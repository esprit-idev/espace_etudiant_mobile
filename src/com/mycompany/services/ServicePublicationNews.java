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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.PublicationNews;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eslem Nabitt
 */
public class ServicePublicationNews {
    
    //we declare the singleton : to ensure that we have one instance of our object
    public static ServicePublicationNews instance = null;
    
    //initiate a connection request
    public ConnectionRequest request;
    
    //make sure to create an instance once it is null
    public static ServicePublicationNews getInstance(){
        if(instance == null)
            instance = new ServicePublicationNews();
        return instance;
    }
    //constructor
    private ServicePublicationNews(){
        request = new ConnectionRequest();
    }
    
    // add publication 
    public void addPublication(PublicationNews pub){
        
        String url = Static.BASE_URL+"/addpubsJSON/new?title="+ pub.getTitle()+ "&owner=" + pub.getOwner() + "&content=" + pub.getContent();
        
        request.setUrl(url);
        request.addResponseListener((e) -> {
            String jsonResponse = new String(request.getResponseData());
            System.out.println(jsonResponse);
        });
        //this is needed in order to execute the request, 
        NetworkManager.getInstance().addToQueueAndWait(request);
                
    }
    
    //display publications 
    
    public ArrayList<PublicationNews> displayPubs(){
        
        ArrayList<PublicationNews> response = new ArrayList<>();
        String url = Static.BASE_URL+"/allpubsJSON";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> mapPubs = jsonParser.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapPubs.get("root");
                    
                    for(Map<String,Object> object : ListOfMaps){
                        PublicationNews pub = new PublicationNews();
                        
                        //id
                        float id = Float.parseFloat(object.get("id").toString());
                        //title
                        String title = object.get("title").toString();
                        //owner
                        String owner = object.get("owner").toString();
                        //content
                        String content = object.get("content").toString();
                        //date
                        String date = object.get("date").toString();
                        
                        pub.setId((int) id);
                        pub.setTitle(title);
                        pub.setContent(content);
                        pub.setOwner(owner);
                        //convert date into date format
                        /*String DateConv = object.get("date").toString().substring(object.get("date").toString().indexOf("timestamp") + 10,object.get("date").toString().lastIndexOf(")"));
                        Date currentTime = new Date(Double.valueOf(DateConv).longValue()* 1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                        String dateString = formatter.format(currentTime); */
                        pub.setDate(date);
                        response.add(pub);
                    }
                } catch (IOException ex) {
                    System.out.print(ex);
                }

            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return response;
    }
    
    //display onePublication
    
    public PublicationNews onePublication(int id, PublicationNews pub){
        
        String url = Static.BASE_URL+"/onepubjson/"+id;
        String res = new String(request.getResponseData());
        request.setUrl(url);
        
        request.addResponseListener((e) -> {
             JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> data = jsonParser.parseJSON(new CharArrayReader(new String(res).toCharArray()));
                    pub.setTitle(data.get("title").toString());
                    pub.setOwner(data.get("owner").toString());
                    pub.setContent(data.get("content").toString());
                }catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
                System.out.println(res);
                        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return pub;
        
    }
    
    
}
