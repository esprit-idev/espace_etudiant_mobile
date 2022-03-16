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
import com.mycompany.entities.ClubPub;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author anash
 */
public class ClubPubService {
      public ArrayList<ClubPub> pubs = new ArrayList<>();
    public static ClubPubService instance;
    public ConnectionRequest request;

    private ClubPubService() {
        request = new ConnectionRequest();
    }

    public static ClubPubService getInstance() {
        if (instance == null) {
            instance = new ClubPubService();
        }
        return instance;
    }

    public ArrayList<ClubPub> parsePubs(String jsonText) {
        try {
            pubs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> pubsListJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) pubsListJSON.get("root");
            for (Map<String, Object> obj : list) {
                ClubPub pub = new ClubPub();

                
                pub.setClub(obj.get("club").toString());
                pub.setPubDate(obj.get("pubDate").toString());
                pub.setPubDesc(obj.get("pubDescription").toString());
             //   pub.setPubFile(obj.get("clubResponsable").toString());
                pub.setPubImage(obj.get("ClubImg").toString());
                System.out.print(obj);
                pubs.add(pub);
            }
        } catch (IOException e) {
        }
        return pubs;
    }

    public ArrayList<ClubPub> getAllPubs(String clubID) {
        String url = Static.BASE_URL + "/allPubs/"+clubID;
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                pubs = parsePubs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return pubs;
    }
}
