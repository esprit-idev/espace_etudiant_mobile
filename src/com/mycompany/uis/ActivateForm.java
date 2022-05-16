/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
//import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceUtilisateur;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author YOOSURF
 */
public class ActivateForm extends Form{

    TextField email ;
    public ActivateForm(Resources res) {
        Toolbar tb=getToolbar();
       	setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("mot de passe oublié");
		Container cnt = new Container(BoxLayout.y());
                cnt.getStyle().setBgColor(0x000000);
                 Form previous = Display.getInstance().getCurrent();
                 tb.setBackCommand("", e -> previous.showBack()); 
		 try {
	            ImageViewer logo=new ImageViewer();
	    		String img_name="/logo.png";
				logo.setImage(Image.createImage(img_name).scaledHeight(450));
				cnt.add(logo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
		
                // TextField email = new TextField("","saisir votre email",20,TextField.ANY);
               email = new TextField("","saisir votre email");
                 email.setSingleLineTextArea(false);
                 Button valider = new Button("Valider");
                // Label haveAnAcount = new Label("Retour se connecter?");
                 Button signIn = new Button("Renouveler votre mot de passe");
                 cnt.addAll(email,valider,signIn);
                 
                

                 signIn.addActionListener(e -> previous.showBack());
                 signIn.setUIID("CenterLink");
                 
              // Container content = BoxLayout.encloseY(
                       
                    //  new FloatingHint (email),
                         //CreateLineSeparator(),
                      //  valider ,
                     //  FlowLayout.encloseCenter(haveAnAcount),
                      //  signIn
             //   );
               //  content.setScrollableY(true);
                
                // add(BorderLayout.CENTER,content);
                 valider.requestFocus();
                 valider.addActionListener(e -> {
                 InfiniteProgress ip = new InfiniteProgress();
                 final Dialog ipDialog = ip.showInfiniteBlocking();
                if(new ServiceUtilisateur().getPasswordByEmail(email.getText() , res).equals("user not found")) 
                 {
       Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK", null);   
        new Login(res).show();
            refreshTheme();
   }
                else{
                   new ServiceUtilisateur().getPasswordByEmail(email.getText() , res) ; 
                 sendMail(res);
            ipDialog.dispose();
            
            Dialog.show("Mot de passe","Nous avons envoyé le mot de passe a votre e-mail. Veuillez vérifier votre boite de réception",new Command("OK"));
              //  }
               // else {
               // Dialog.show("Alerte","verifier votre email",new Command("OK"));
               // }
                
            new Login(res).show();
            refreshTheme();
                 
                }  });
                  
             addAll(cnt);    
    }

//sendMail
    
    public void sendMail(Resources res){
         
    try{
        Properties props = new Properties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtps.host","smtp.gmail.com"); 
        props.put("mail.smtps.port","587");
        props.put("mail.smtps.auth","true");
        props.put("mail.smtps.starttls.enable","true");
    
     
       Session session = Session.getInstance(props,null);
       MimeMessage msg = new MimeMessage (session);
        msg.setFrom(new InternetAddress("Reintialisation mot de passe<malek@gmail.com>"));
        msg.setRecipients(Message.RecipientType.TO,email.getText().toString());
        msg.setSubject("Application nom : Confirmation du");
        msg.setSentDate(new Date(System.currentTimeMillis()));
        /*int min = 100;  
       int max = 900;  
//Generate random int value from 200 to 400     
     int b = (int)(Math.random()*(max-min+1)+min); 
     String s=Integer.toString(b);
     System.out.println(b); */
        String mp=ServiceUtilisateur.getInstance().getPasswordByEmail(email.getText().toString(), res);;
        String txt = "Bienvenue sur EdSpace : Tapez ce mot de passe : "+mp+" dans le champs requis ";
        
        msg.setText(txt);
        
       SMTPTransport  st = (SMTPTransport)session.getTransport("smtps") ;
        
        st.connect("smtp.gmail.com",465,"edspace2.plateforme@gmail.com","edspace123");
           
          st.sendMessage(msg, msg.getAllRecipients());
            
          System.out.println("server response : "+st.getLastServerResponse());
       
    }catch(Exception e){
    e.printStackTrace();
    }
    
    }

}
    

