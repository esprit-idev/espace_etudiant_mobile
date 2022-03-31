/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceStudent;
import com.mycompany.uis.ListStudents;


/**
 *
 * @author Lenovo
 */
public class UpdateStudent extends Form {
    
    Form current;
    public UpdateStudent(Resources res , User stu) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier un etudiant");
        getContentPane().setScrollVisible(false);
        
        
       // super.addSideMenu(res);
        
        TextField username = new TextField(stu.getUsername() , "Prenom : " , 20 , TextField.ANY);
        TextField nom= new TextField(stu.getPrenom() , "Nom : " , 20 , TextField.ANY);       
        TextField email= new TextField(stu.getEmail() , "Email : " , 20 , TextField.ANY);
        TextField password= new TextField(stu.getPassword() , "Password : " , 20 , TextField.ANY);


             //  TextField email = new TextField(String.valueOf(.getEtat()) , "Etat" , 20 , TextField.ANY);
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
       // ComboBox etatCombo = new ComboBox();
        
        //etatCombo.addItem("Desactiver");
        
       // etatCombo.addItem("Activer");
        
       // if(stu.getIsBanned() == 0 ) {
           // etatCombo.setSelectedIndex(0);
       // }
       // else 
         //   etatCombo.setSelectedIndex(1);
        
        
        
        
        
        username.setUIID("NewsTopLine");
        nom.setUIID("NewsTopLine");
        email.setUIID("NewsTopLine");
        password.setUIID("NewsTopLine");
        
        username.setSingleLineTextArea(true);
        nom.setSingleLineTextArea(true);
        email.setSingleLineTextArea(true);
        password.setSingleLineTextArea(true);

        
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("RoundBtn");
      // btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           stu.setUsername(username.getText());
           stu.setPrenom(nom.getText());
           stu.setEmail(email.getText());
           stu.setPassword(password.getText());


           
          // if(etatCombo.getSelectedIndex() == 0 ) {
            //   r.setEtat(0);
         //  }
         //  else 
            //   r.setEtat(1);
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceStudent.getInstance().modifierStudent(stu)) { // if true
           new ListStudents(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.setUIID("IndianredRoundBtn");
       btnAnnuler.addActionListener(e -> {
           new ListStudents(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label("");
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(username),
              //  createLineSeparator(),
                new FloatingHint(nom),
                 new FloatingHint(email),
                 new FloatingHint(password)


              //  createLineSeparator(),
               // etatCombo,
               // createLineSeparator(),//ligne de séparation
                 
               // btnModifier,
               // btnAnnuler
                
               
        );
          Container ct = BoxLayout.encloseY(
           btnModifier,
           btnAnnuler
          );
        
        add(content);
        add(ct);
        show();
        
        
    }
}
