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
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceNiveau;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class NiveauxList extends Form{
    
    public NiveauxList(){
         Toolbar tb=getToolbar();
		setTitle("Niveaux");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cntb = new Container(BoxLayout.y());
                Container cnt = new Container(BoxLayout.y());
                
                Button btnAdd= new Button("Ajouter un niveau");
                btnAdd.addActionListener((e)-> {
                new NiveauAdd().show();

        });
                
                 Label empty = new Label ("");
                   cnt.addAll(btnAdd,empty);
                
                ArrayList<Niveau> list = ServiceNiveau.getInstance().getAllNiveaux();
                      for (Niveau n : list){
                         
                          Label niveau = new Label ("Niveau:"+n.getId());
                                  Button btnSend= new Button("Supprimer");

        
         btnSend.addActionListener((e)-> {
                ServiceNiveau.getInstance().DeleteNiveau(n.getId());
                new NiveauxList().show();

        });

         
         cnt.addAll(niveau,btnSend);
                          
                      }
                      addAll(cnt);
                      
                       
                      
                      
                      
                      
                      
                      
                      
        
      
           
                
                
                
                
                
                
        
        
        
    }
    
    
    
}
