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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceAdmin;


/**
 *
 * @author YOOSURF
 */
public class AddAdmin extends Form{
    public AddAdmin(Resources res){
    
         Toolbar tb=getToolbar();
		setTitle("Ajout des Administrateurs");
		Form previous = Display.getInstance().getCurrent();
		 tb.setBackCommand("", e -> {
            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
            new ListAdmin(res).show();
        });
		Container cnt = new Container(BoxLayout.y());
                
                
		TextField username=new TextField("","Prenom ");
                TextField prenom=new TextField("","Nom ");
                TextField email=new TextField("","L'email ");
                TextField password=new TextField("","Le mot de passe ");
                //TextField isBanned=new TextField("","Le mot de passe ");


        //ComboBox<String> classe=new ComboBox<String>();
        //get niveau from db and add to cb
       // classe.addItem("3A");          
        //classe.addItem("3B");
        //cbNiveau.addItem("4SIM");
       
        Button ajouter=new Button("Ajouter");
        cnt.addAll(username,prenom,email,password,ajouter);
        
        
        ajouter.addActionListener((ActionListener) (ActionEvent evt) -> {
            // add a book
            User a = new User(prenom.getText() , username.getText(),email.getText(),password.getText() );
           // new  ServiceStudent.ajoutStudent(stu);
            new  ServiceAdmin().AddAdmin(a);
             new ListAdmin(res).show();
            });
        
      //  List.addActionListener((ActionListener)(ActionEvent evt)-> {
        //new ServiceStudent().affichageStudent();
        //new ListStudents(res).show();
        //});
        
        
       // ajouter.addActionListener(new ActionListener() {
        	//@Override
        	//public void actionPerformed(ActionEvent evt) {
                    // ServiceStudent.getInstance().signin(username, password, res);
        		//ajout a la bd              		
        	//}
		//});    
        addAll(cnt);
        
    }
    
}
