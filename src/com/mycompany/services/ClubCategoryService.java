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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Club;
import com.mycompany.entities.ClubCategory;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author anash
 */
public class ClubCategoryService {

    public ArrayList<ClubCategory> categories = new ArrayList<>();
    public static ClubCategoryService instance;
    public ConnectionRequest request;

    private ClubCategoryService() {
        request = new ConnectionRequest();
    }
    
    public static ClubCategoryService getInstance() {
        if (instance == null) {
            instance = new ClubCategoryService();
        }
        return instance;
    }
        public ArrayList<ClubCategory> parseClubsCategories(String jsonText) {
        try {
            categories = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> categoriesListJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) categoriesListJSON.get("root");
            for (Map<String, Object> obj : list) {
                ClubCategory category = new ClubCategory();

                category.setCategorie(obj.get("categorieNom").toString());
                categories.add(category);
            }
        } catch (IOException e) {
        }
        return categories;
    }
        
            public ArrayList<ClubCategory> getAllClubCategories() {
        String url = Static.BASE_URL + "/AllClubCategories";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseClubsCategories(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return categories;
    }



}
