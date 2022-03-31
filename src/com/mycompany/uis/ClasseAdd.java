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
import com.mycompany.entities.Niveau;
import com.mycompany.uis.NiveauAdd;
import com.mycompany.uis.NiveauxList;
import java.util.ArrayList;
import com.mycompany.services.ServiceClasse;
import com.mycompany.services.ServiceClasse;
import com.mycompany.services.ServiceNiveau;

/**
 *
 * @author aa
 */
public class ClasseAdd extends Form{
    public ClasseAdd(){
         Toolbar tb=getToolbar();
		setTitle("Ajouter une classe");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> new ClasseList().show());
		Container cnt = new Container(BoxLayout.y());
                
                Label c = new Label ("Classe:");
                 TextField classe =new TextField("","Classe"); 
                Label n = new Label ("Niveau:");
                TextField niveau =new TextField("","niveau"); 
                Button btn= new Button("Valider");
                
        btn.addActionListener((e)-> {
       
           
            
            if( isNumericArray(classe.getText())){
            
            int i=0;
            
            ArrayList<Niveau> list = ServiceNiveau.getInstance().getAllNiveaux();
            
                      for (Niveau ni : list){
                          if(i==0){
                              
                              if(niveau.getText().equals(ni.getId())){
                             
                              i=1;
                              }
                          }
                         
                    
                          
                      }
                       if(i==1){
                           
                           ServiceClasse.getInstance().AddClasse(classe.getText(),niveau.getText());
                           new ClasseList().show();
                           
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
        }
            else{
                
                
                                     
                           Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
            dialog.setDisposeWhenPointerOutOfBounds(true);
            dialog.add("Error");
            dialog.add("Classe must be a number");
            Button ok= new Button("OK");
            
                           
             
             ok.addActionListener((e1)->{
                 dialog.dispose();
              
             });
             
             
             dialog.add(ok);
             dialog.show();
                
            }   
            

        });
        
        cnt.addAll(c,classe,n,niveau,btn);
        add(cnt);
        
    }
    public static boolean isNumericArray(String str) {
    if (str == null)
        return false;
    for (char c : str.toCharArray())
        if (c < '0' || c > '9')
            return false;
    return true;}
 
    
}
