/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.services.ServiceNiveau;

/**
 *
 * @author aa
 */
public class NiveauAdd extends Form{
    public NiveauAdd(){
       
        Toolbar tb=getToolbar();
		setTitle("Ajouter un niveau");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cnt= new Container(BoxLayout.y());
                 Label niveau = new Label ("Niveau:");
                 TextField text =new TextField("","Niveau");   
                 Button btnAdd= new Button("Ajouter");
                 Button annuler = new Button("Annuler");
                 
                 
                 
                btnAdd.addActionListener((e)-> {
                    ServiceNiveau.getInstance().AddNiveau(text.getText());
                new NiveauxList().show();

        });
                
                 annuler.addActionListener((e)-> {
                new NiveauxList().show();

        });
                
                
        cnt.addAll(niveau,text,annuler,btnAdd);
        add(cnt);
        
    }
    
}
