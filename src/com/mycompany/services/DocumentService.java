/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Document;
import com.mycompany.uis.Statics;

/**
 *
 * @author MeriamBI
 */

public class DocumentService {
	public ArrayList<Document> docs;
	public static DocumentService instance;
	public boolean resultOK;
	private ConnectionRequest req;
	
	public DocumentService() {
		req=new ConnectionRequest();
	}
	
	public static DocumentService getInstance() {
		if(instance==null) {
			instance=new DocumentService();
		}
		return instance;
	}
	
	public boolean addDoc(Document doc) {
		String url=Statics.BASE_URL+"/addDoc/"+doc.getId();
		req.setUrl(url);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			
			@Override
			public void actionPerformed(NetworkEvent evt) {
				// TODO Auto-generated method stub
				resultOK=req.getResponseCode()==200;
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}
	
	public ArrayList<Document> parseDocs(String jsonText){
		try {
			docs=new ArrayList<>();
			JSONParser j=new JSONParser();
			Map<String,Object> docsListJSON=j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
			ArrayList<Map<String, Object>> list=(ArrayList<Map<String, Object>>)docsListJSON.get("root");
			for (Map<String, Object> obj : list) {
                Document d = new Document();

                int id = Integer.parseInt(obj.get("id").toString());
                d.setId((int) id);
                d.setNom(obj.get("nom").toString());
                d.setNiveau(obj.get("niveau").toString());
                d.setMatiere(obj.get("matiere").toString());
                d.setMatiere(obj.get("date_insert").toString());
                d.setMatiere(obj.get("proprietaire").toString());
                int signals = Integer.parseInt(obj.get("signalements").toString());
                d.setSignals((int) signals);
                System.out.print(obj);
                docs.add(d);
            }
		} catch (IOException e) {

		}
		return docs;
	}
	
	public ArrayList<Document> getAllDocs() {
        String url = Statics.BASE_URL + "/allDocs";
        
        req.setUrl("http://127.0.0.1:8000/allDocs");
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                docs = parseDocs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return docs;
    }
}
