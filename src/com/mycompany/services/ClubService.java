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
import com.mycompany.entities.Club;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author anash
 */
public class ClubService {

    public ArrayList<Club> clubs = new ArrayList<>();
    public static ClubService instance;
    public ConnectionRequest request;

    private ClubService() {
        request = new ConnectionRequest();
    }

    public static ClubService getInstance() {
        if (instance == null) {
            instance = new ClubService();
        }
        return instance;
    }

    public ArrayList<Club> parseClubs(String jsonText) {
        try {
            clubs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> clubsListJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) clubsListJSON.get("root");
            for (Map<String, Object> obj : list) {
                Club club = new Club();

                club.setClubId(obj.get("id").toString());
                club.setClubName(obj.get("clubNom").toString());
                club.setClubDesc(obj.get("clubDescription").toString());
                club.setClubCategorie(obj.get("clubCategorie").toString());
                club.setClubRespo(obj.get("clubResponsable").toString());
                club.setClubPic(obj.get("ClubPic").toString());
                System.out.print(obj);
                clubs.add(club);
            }
        } catch (IOException e) {
        }
        return clubs;
    }

    public ArrayList<Club> getAllClubs() {
        String url = Static.BASE_URL + "/allClubs";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                clubs = parseClubs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return clubs;
    }
}
