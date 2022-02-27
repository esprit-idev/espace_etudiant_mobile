package com.mycompany.uis;

import java.io.IOException;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

public class Login extends Form{
	public Login() {
		setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("Login");
		Container cnt = new Container(BoxLayout.y());
		 try {
	            ImageViewer logo=new ImageViewer();
	    		String img_name="/logo.png";
				logo.setImage(Image.createImage(img_name).scaledHeight(450));
				cnt.add(logo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        TextField tf_email=new TextField("","email");
        tf_email.setConstraint(TextField.EMAILADDR);
        TextField tf_mdp=new TextField("","mot de passe");
        tf_mdp.setConstraint(TextField.PASSWORD);
        Button btn_login=new Button("Se connecter");
        Label l_mdp_oub=new Label("mot de passe oublie");
        cnt.addAll(tf_email,tf_mdp,btn_login,l_mdp_oub);
        btn_login.addActionListener(
        		new ActionListener<ActionEvent>() {
                	@Override
                	public void actionPerformed(ActionEvent evt) {
                		new TabAff().show();          		
                	}
        		});
        addAll(cnt);
	}
}
