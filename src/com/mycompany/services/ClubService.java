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
import com.codename1.io.Util;
import com.codename1.processing.Result;
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
                Result result=Result.fromContent(obj);
                Club club = new Club();

                try {
                    club.setClubId(Util.split(obj.get("id").toString(), ".")[0]);
                } catch (Exception e) {
                    club.setClubId(Util.split(obj.get("id").toString(), ".")[0]);

                }
                club.setClubName(obj.get("clubNom").toString());
                club.setClubDesc(obj.get("clubDescription").toString());
                club.setClubCategorie(obj.get("clubCategorie").toString());
                club.setClubRespo(result.getAsString("clubResponsable/email"));
                club.setClubPic(obj.get("ClubPic").toString());
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

    public void editClubDesc(String clubID, String clubDesc) {

        String url = Static.BASE_URL + "/editClubJson/" + clubID + "?clubDesc=" + clubDesc;

        request.setUrl(url);
        request.addResponseListener((e) -> {
            String jsonResponse = new String(request.getResponseData());
            System.out.println(jsonResponse);
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

    }

    public ArrayList<Club> sds(String clubId) {
        String url = Static.BASE_URL + "/oneClub/" + clubId;
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
