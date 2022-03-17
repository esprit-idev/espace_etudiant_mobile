package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.PublicationNews;
import com.mycompany.services.ServicePublicationNews;
import java.io.IOException;
import java.util.ArrayList;


public class TabAff extends BaseForm{
    
      @Override
    protected boolean isCurrentInbox() {
        return true;
    }
    
    public TabAff() {
		setLayout(new FlowLayout(LEFT,CENTER));
		setTitle("Tableau d'affichage");
		Toolbar tb=getToolbar();
		//tableau d'affichage
		tb.addMaterialCommandToSideMenu("Tableau d'affichage", FontImage.MATERIAL_DASHBOARD, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
            	//new TabAff().show();
                }
        });
        
      //forum
		tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_FORUM, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
            	new Forum().show();
                }
        });
      //clubs
		tb.addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_PEOPLE, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new Club().show();
                }
        });
        
      //offres d'emploi
		tb.addMaterialCommandToSideMenu("Offres d'emploi", FontImage.MATERIAL_WORK, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new OffresEmplois().show();
                }
        });
        
        //profile
		tb.addMaterialCommandToSideMenu("Mon profile", FontImage.MATERIAL_SUPERVISOR_ACCOUNT, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new Profile().show();
                }
        });
        
        
        //centre de partage
		tb.addMaterialCommandToSideMenu("Centre de partage", FontImage.MATERIAL_ATTACH_FILE, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new CentrePartage().show();
                }
        });
        
        
      //dcnx
		tb.addMaterialCommandToSideMenu("Se deconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new Login().show();
                }
        });
        //publications list     
        
        ArrayList<PublicationNews> listPub = ServicePublicationNews.getInstance().displayPubs();
        
        //loop through the publications
        for(PublicationNews pub : listPub){
            
            addButton(pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage(), pub.getDate(), pub);
        
        }
        
	}
    
     private void addButton(String title,String content,String categoryName, String image,String date, PublicationNews pub) {
         Container cnt = new Container(BoxLayout.y());
         Label dateTxt = new Label(date);
         Label catTxt = new Label(categoryName.substring(14, pub.getCategoryName().length()-1));
         Label contentTxt = new Label(content.substring(0, 40).concat("..."));
         Label titleTxt = new Label(title);
         catTxt.setUIID("RedLabel");
         titleTxt.setUIID("Label_3");
         dateTxt.setTextPosition(RIGHT);
         System.out.println(image.substring(6, pub.getImage().length()-1));
         //Image img = new Image
         ImageViewer logo=new ImageViewer();
	 String img_name="/"+image.substring(6, pub.getImage().length()-1);
         
       /* try {
            logo.setImage(Image.createImage(img_name));
            cnt.add(logo);
        } catch (IOException ex) {
                       ex.printStackTrace();
        }
        */
      Button myBtn = new Button();
        myBtn.addActionListener(e -> {
           //ServicePublicationNews.getInstance().onePublication(pub.getId(), pub);
           new NewsDetail().show();
     
        
        });

      cnt.setLeadComponent(myBtn);
      cnt.addAll(titleTxt, dateTxt,catTxt,contentTxt);
      add(cnt);
       
    }

}
