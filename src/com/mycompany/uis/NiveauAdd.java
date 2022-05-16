/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceNiveau;

/**
 *
 * @author aa
 */
public class NiveauAdd extends Form{
    public NiveauAdd(Resources res){
       
        Toolbar tb=getToolbar();
		setTitle("Ajouter un niveau");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> new NiveauxList(res).show());
		Container cnt= new Container(BoxLayout.y());
                 Label niveau = new Label ("Niveau:");
                 niveau.setUIID("CustomLabel");
                 TextField text =new TextField("","Niveau");   
                 Button btnAdd= new Button("Ajouter");
                 btnAdd.setUIID("BlackRoundFilledBtn");
                 Button annuler = new Button("Annuler");
                 annuler.setUIID("BlackRoundFilledBtn");
                 
                 
                 
                btnAdd.addActionListener((e)-> {
                    
                    if(!text.getText().equals("")){
                    ServiceNiveau.getInstance().AddNiveau(text.getText());
                new NiveauxList(res).show();
                    }else{
                        
                          Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
            dialog.setDisposeWhenPointerOutOfBounds(true);
            dialog.add("Error");
            dialog.add("Niveau ne peut pas être vide. ");
            Button ok= new Button("OK");
            
                           
             
             ok.addActionListener((e1)->{
                 dialog.dispose();
              
             });
             
             
             dialog.add(ok);
             dialog.show();
                    }

        });
                
                 annuler.addActionListener((e)-> {
                new NiveauxList(res).show();

        });
                
                
        cnt.addAll(niveau,text,btnAdd,annuler);
        add(cnt);
        
    }
    
}
