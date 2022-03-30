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
import com.codename1.util.StringUtil;
import com.mycompany.entities.Club;
import com.mycompany.entities.User;
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
    public ArrayList<User> users = new ArrayList<>();

    public static ClubService instance;
    public ConnectionRequest request;
    public boolean resultOk;
    String clubimg;

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
                Result result = Result.fromContent(obj);
                Club club = new Club();

                
                    club.setClubId(Util.split(obj.get("id").toString(), ".")[0]);
                
                club.setClubName(obj.get("clubNom").toString());
                club.setClubDesc(obj.get("clubDescription").toString());
                club.setClubCategorie(result.getAsString("clubCategorie/categorieNom"));
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

    public boolean addClub(Club club) {

        String url = Static.BASE_URL + "/addClubJson/new?clubNom=" + club.getClubName() + "&clubPic=" + club.getClubPic() + "&clubDesc=" + club.getClubDesc() + "&clubResponsable=" + club.getClubRespo() + "&clubCategorie=" + club.getClubCategorie();
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

    public boolean deleteClub(String idClub) {
        String url = Static.BASE_URL + "/deleteClubJson/" + idClub;
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

    public boolean updateClub(String clubID, String clubNom, String clubDesc, String clubRespo, String clubCatego) {
        String url = Static.BASE_URL + "/updateClubJson/" + clubID + "?clubNom=" + clubNom + "&clubDesc=" + clubDesc + "&clubResponsable=" + clubRespo + "&clubCategorie=" + clubCatego;
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

    public String getClubPic(String clubID) {
        String url = Static.BASE_URL + "/getClubPic/" + clubID;
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                clubimg = StringUtil.replaceAll(new String(request.getResponseData()), "\"", "");

                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return clubimg;
    }

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) usersListJSON.get("root");
            for (Map<String, Object> obj : list) {
                User user = new User();
                user.setId(Integer.parseInt(Util.split(obj.get("id").toString(), ".")[0]));
                user.setEmail(obj.get("email").toString());
                users.add(user);
            }
        } catch (IOException e) {
        }
        return users;
    }
    
    public ArrayList<User> addClubUsers() {
        String url = Static.BASE_URL + "/UsersAddClub";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return users;
    }
    public ArrayList<User> updateClubUsers(String idClub) {
        String url = Static.BASE_URL + "/UsersUpdateClub/"+idClub;
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return users;
    }

}
