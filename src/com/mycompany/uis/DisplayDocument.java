/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.entities.Document;
import com.mycompany.utils.Static;

/**
 *
 * @author MeriamBI
 */
public class DisplayDocument extends Form{
    public DisplayDocument(Document doc){
        setLayout(new BorderLayout());
        BrowserComponent browser = new BrowserComponent();
        if(doc.getUrl().equals(""))
            browser.setURL(Static.BASE_URL+"/document/apercuDocument/"+doc.getId());
        else
            browser.setURL(Static.BASE_URL+"/document/apercuUrl/"+doc.getId());
        add(BorderLayout.CENTER,browser);
    }
}
