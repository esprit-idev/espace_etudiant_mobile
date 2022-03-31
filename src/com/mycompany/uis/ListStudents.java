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
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceStudent;
import com.mycompany.utils.Static;
import java.util.ArrayList;

/**
 *
 * @author YOOSURF
 */
public class ListStudents extends Form{
    public ListStudents(Resources res){
     Toolbar tb=getToolbar();
		setTitle("Liste des etudiants");
		Form previous = Display.getInstance().getCurrent();
		 tb.setBackCommand("", e -> {
            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
            new TabAff(res).show();
        });
		//Container cntb = new Container(BoxLayout.x());
                //Container cnt = new Container(BoxLayout.y());
                tb.addCommandToRightBar("Add", null, (ActionListener) (ActionEvent evt) ->{
                 new AddStudent(res).show();
                });
                
                ArrayList<User> list = ServiceStudent.getInstance().affichageStudent();
                
                
                Container cntb = new Container(new BoxLayout(BoxLayout.X_AXIS));
                Container cnt= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        
                      for (User stu : list){
                          Label niveau = new Label ("Nom et Prenom de l'etudiant:"+stu.getPrenom()+stu.getUsername());
                                  Button btndel= new Button("Supprimer");
                                  Button update = new Button("Update");
                                  update.setUIID("RoundBtn");
                                  btndel.setUIID("IndianredRoundBtn");
                                 // cntb.add(update);
                                  //cntb.add(btndel);
                                 // cntb.addAll(update , btndel);
                                  
                            niveau.addPointerPressedListener((ActionListener)(ActionEvent evt)->{
                           Dialog.show("Etudiant","Prenom : " + niveau.getText()+"\n Email :"+stu.getEmail(),"OK", null);
                            }); 
                           
                                  
         btndel.addActionListener((e)-> {
                ServiceStudent.getInstance().deleteStudent(stu.getId());
                new ListStudents(res).show();
           
            
        });
          update.addActionListener((e)-> {
             //   ServiceStudent.getInstance().deleteStudent(stu.getId());
                new UpdateStudent(res,stu).show();
                });
         
       // cnt.add(niveau);
       cnt.addAll(niveau, update , btndel);
       
                          
                      }
                        add(cnt);
                      // addAll(cnt,cntb);
                     
    
    
    }
    
}
