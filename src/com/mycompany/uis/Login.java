package com.mycompany.uis;

import com.codename1.components.FloatingHint;
import java.io.IOException;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;

public class Login extends Form{
	public Login(Resources res) {
		setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("Login");
		Container cnt = new Container(BoxLayout.y());
                cnt.getStyle().setBgColor(0x000000);
		 try {
	            ImageViewer logo=new ImageViewer();
	    		String img_name="/logo.png";
				logo.setImage(Image.createImage(img_name).scaledHeight(450));
				cnt.add(logo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         TextField email = new TextField("", "Email", 20, TextField.ANY);
        email.setConstraint(TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
       // Button signUp = new Button("Sign Up");
        
        //mp oublié
        Button  mp = new Button("oublier mot de passe?","CenterLabel");
        
        
      // signUp.addActionListener(e -> new SignUpForm(res).show());
        //signUp.setUIID("Link");
       // Label doneHaveAnAccount = new Label("Vous n'avez aucune compte?");
        
        
        cnt.addAll(email,password,signIn,mp);
        
        
        
        //Container content = BoxLayout.encloseY(
          //      new FloatingHint(username),
               // createLineSeparator(),
            //    new FloatingHint(password),
              //  createLineSeparator(),
              //  signIn,
               // FlowLayout.encloseCenter(doneHaveAnAccount),mp
        //);
        //content.setScrollableY(true);
        //add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        
        signIn.addActionListener(e -> 
        { 
           //  if (new ServiceUtilisateur().signin(username, password ,res).equals("failed")) {
     //  Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK", null);
  // }
            // else{
           new ServiceUtilisateur().signin(email, password ,res);
              // new TabAff(res).show();
          //  new AddStudent().show();
           //  }
           
        });
        
        
        
        //Mp oublie event
        
        mp.addActionListener((e) -> {
           
            new ActivateForm(res).show();
            
            
        });

        addAll(cnt);
        
    }
}
