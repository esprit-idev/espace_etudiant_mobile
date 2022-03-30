package com.mycompany.uis;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;

public class Profile extends Form{
	public Profile(Resources res) {
		Toolbar tb=getToolbar();
		setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("Mon Profile");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
                
                 tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("logo.png");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

     //   Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
       // Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
      //  facebook.setTextPosition(BOTTOM);
       // twitter.setTextPosition(BOTTOM);
        
     
     //  Label picture = new Label(ServiceUtilisateur.UrlImage(SessionManager.getImage()),"PictureWithBackground");
       
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3 ,
                          //  facebook,
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("logo.png"), "PictureWhiteBackgrond"))
                          //  twitter
                    )
                )
        ));
        Container cnt = new Container(BoxLayout.y());
        String us = SessionManager.getUserName();
        System.out.println(us);

      TextField username=new TextField(us);
       // username.setUIID("TextFieldBlack");
        addStringValue("Username", username);

     //  TextField email=new TextField(SessionManager.getEmail(),"email",20,TextField.EMAILADDR);
       // addStringValue("E-Mail", email);
        
        TextField password = new TextField(SessionManager.getPassowrd(), "Password", 20, TextField.PASSWORD);
       // password.setUIID("TextFieldBlack");
        addStringValue("Password", password);
        Button modif = new Button ("modifier") ;
     // image.setUIID("Update");
     // modif.setUIID("Edit");
    //  addStrinfValue("",modif);
   // TextField path = new TextField("");
    
      
    cnt.add(modif);
        
        modif.addActionListener ((edit) ->{
        InfiniteProgress ip =new InfiniteProgress();
        final Dialog ipDig =ip.showInifiniteBlocking();
        ServiceUtilisateur.EditUser(SessionManager.getId() ,username.getText(), password.getText());
        SessionManager.setUserName(username.getText());
     //   SessionManager.setEmail(email.getText());
        SessionManager.setPassowrd(password.getText());
        Dialog.show("Succes", "Modification des cordonnées avec succes", "OK" , null);
        ipDig.dispose();
        refreshTheme();

        
        });
         addAll(cnt);
        }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
       // add(createLineSeparator(0xeeeeee));
    }
                
	}

