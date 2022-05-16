/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.SpanLabel;
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
import com.mycompany.entities.User;
import com.mycompany.services.ServiceClasse;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class Classe2etudiant extends Form{
        public Classe2etudiant(String id){
           
        System.out.println(id);
        Toolbar tb=getToolbar();
		setTitle("Liste d'etudiants");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> new ClasseEtudiant(id).show());
		Container cnt = new Container(BoxLayout.y());
                Label d = new Label ("Email:");
                 TextField etudiant =new TextField("","Email"); 
                 
                 Button btn= new Button("Valider");
                
        btn.addActionListener((e)-> {
                String r = "false";
                ArrayList<String> list = ServiceClasse.getInstance().add2classe(id,etudiant.getText());
                 for (String n : list){
                              r=n;

                 }
                 
                 if(r.equals("true")){
                           new ClasseEtudiant(id).show();
                     
                 }else{
                     Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
            dialog.setDisposeWhenPointerOutOfBounds(true);
            dialog.add("Error");
            dialog.add("Niveau does not exit");
            Button ok= new Button("OK");
            
                           
             
             ok.addActionListener((e1)->{
                 dialog.dispose();
              
             });
             
             
             dialog.add(ok);
             dialog.show();
                 }
                 
        });

         
         cnt.addAll(d,etudiant,btn);
                          
                      
                      add(cnt);
                
        
        
    }
}
