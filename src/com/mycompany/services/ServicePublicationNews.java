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
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.PublicationNews;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eslem Nabitt
 */
public class ServicePublicationNews {
    //return result state
    boolean resultOk;
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
                        Result result = Result.fromContent(object);
                        //id
                        float id = Float.parseFloat(object.get("id").toString());
                        //title
                        String title = object.get("title").toString();
                        //owner
                        String owner="";
                        if(owner != null){
                            owner = object.get("owner").toString();
                        }
                        //content
                        String content = object.get("content").toString();
                        //category
                        pub.setCategoryName(result.getAsString("categoryName/categoryName"));
                        //image            
                        pub.setImage(result.getAsString("image"));
                        //comments 
                        String comments ="";
                        if(object.get("comments")!=null)
                            comments = object.get("comments").toString();
                        pub.setId((int) id);
                        pub.setTitle(title);
                        pub.setContent(content);
                        pub.setOwner(owner);
                        
                        
                        pub.setComments(comments);
                        //date
                        //convert date into date format
                        String DateConv = object.get("date").toString().substring(object.get("date").toString().indexOf("timestamp") + 1,object.get("date").toString().lastIndexOf("T"));
                        pub.setDate(DateConv);
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
        request.setUrl(url);
        
        request.addResponseListener((e) -> {
             JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> data = jsonParser.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    pub.setTitle(data.get("title").toString());
                    pub.setOwner(data.get("owner").toString());
                    pub.setContent(data.get("content").toString());
                }catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return pub;
        
    }
    
    // add publication 
    public boolean addPublication(PublicationNews pub){ 
        String url = Static.BASE_URL+"/addpubsJSON/new?title="+ pub.getTitle()+ "&owner=" + pub.getOwner() + "&content=" + pub.getContent().toString() + "&categoryName=" + pub.getCategoryName()+ "&image=" + pub.getImage();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }
    
    //update publication
     public boolean updatePublicationNews(int id,String title, String owner, String content, String categoryName, String image) {
            String url = Static.BASE_URL + "/updatepubsJSON/"+id + "?title=" + title + "&owner=" + owner + "&content=" + content +"&categoryName=" + categoryName + "&image=" + image;
            request.setUrl(url);
            request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200; 
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }
    //delete publication 
     
    public boolean deletePublicationNews(int id) {
        String url = Static.BASE_URL + "/deletepubsJSON/" + id;
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultOk = request.getResponseCode() == 200;

                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;

    }
    
}
