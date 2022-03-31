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
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceAdmin;
import java.util.ArrayList;

/**
 *
 * @author YOOSURF
 */
public class ListAdmin extends Form{
    public ListAdmin(Resources res){
         Toolbar tb=getToolbar();
		setTitle("Liste des administrateurs");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cntb = new Container(BoxLayout.x());
                Container cnt = new Container(BoxLayout.y());
                tb.addCommandToRightBar("Add", null, (ActionListener) (ActionEvent evt) ->{
                 new AddAdmin(res).show();
                });
                
                ArrayList<User> list = ServiceAdmin.getInstance().affichageAdmin();
                      for (User a : list){
                          Label niveau = new Label ("Nom et Prenom de l'admin : "+a.getPrenom()+a.getUsername());
                                  Button btnSend= new Button("Supprimer");
                                  Button update = new Button("Update");
                                   update.setUIID("RoundBtn");
                                  btnSend.setUIID("IndianredRoundBtn");
        
                            niveau.addPointerPressedListener((ActionListener)(ActionEvent evt)->{
                           Dialog.show("Admin", niveau.getText()+"\n Email :"+a.getEmail(),"OK", null);
                            }); 
                           
                                  
         btnSend.addActionListener((e)-> {
                ServiceAdmin.getInstance().deleteAdmin(a.getId());
                new ListAdmin(res).show();
           
            
        });
          update.addActionListener((e)-> {
             //   ServiceStudent.getInstance().deleteStudent(stu.getId());
                new UpdateAdmin(res,a).show();
                });
         
         cnt.addAll(niveau,btnSend,update);
                          
                      }
                       add(cnt);
        
    }
}
