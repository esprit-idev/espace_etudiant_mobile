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
import com.mycompany.services.ServiceClasse;
import com.mycompany.services.ServiceNiveau;
import static com.mycompany.uis.ClasseAdd.isNumericArray;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class ClasseUpdate extends Form{
    
   public ClasseUpdate(String id,String classe,String niveau){
        
        
        Toolbar tb=getToolbar();
		setTitle("Classe");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cnt = new Container(BoxLayout.y());
                
                Label idd = new Label ("Id:"+id);
               TextField c=new TextField(classe,"");
               TextField n=new TextField(niveau,"");
               
               Button btnSend= new Button("Valider");
                 btnSend.addActionListener((e)-> {
                     
                     
                     System.out.println(c.getText());
                     
            
            if( isNumericArray(c.getText())){
                
                int i=0;
            
            ArrayList<Niveau> list = ServiceNiveau.getInstance().getAllNiveaux();
            
                      for (Niveau ni : list){
                          if(i==0){
                              
                              if(n.getText().equals(ni.getId())){
                             
                              i=1;
                              }
                          }
                         
                    
                          
                      }
                       if(i==1){
                     if(!c.getText().equals("")){
                      ServiceClasse.getInstance().UpdateClasse(id,c.getText(),n.getText());
                new ClasseList().show();
                     }else{
                        
                          Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
            dialog.setDisposeWhenPointerOutOfBounds(true);
            dialog.add("Error");
            dialog.add("Classe ne peut pas être vide. ");
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

               
                 
               cnt.addAll(idd,c,n,btnSend);
               
               add(cnt);
        
        
        
    }
    
    
}
