package com.mycompany.uis;

import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.PublicationNews;
import com.mycompany.services.ServicePublicationNews;
import java.util.ArrayList;

public class TabAff extends BaseForm{
    
    
    public TabAff() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
      @Override
    protected boolean isCurrentInbox() {
        return true;
    }
    
    public TabAff(Resources res) {
		setLayout(new FlowLayout(CENTER,CENTER));
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
            	new Login(res).show();
                }
        });
/*
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_Container_2);
        gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4);
        gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3);
        gui_Container_4.addComponent(gui_Label_4);
        gui_Container_3.addComponent(gui_Label_3);
        gui_Container_3.addComponent(gui_Label_2);
        gui_Container_3.addComponent(gui_Text_Area_1);
        gui_Container_2.addComponent(gui_Label_1);
        gui_Container_2.setName("Container_2");
        gui_Container_4.setName("Container_4");
        gui_Label_1.setUIID("SmallFontLabel");
        gui_Label_1.setName("Label_1");  
        gui_Label_4.setUIID("Padding2");
        gui_Label_4.setName("Label_4");
        ((com.codename1.ui.layouts.FlowLayout)gui_Container_4.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
        gui_Container_3.setName("Container_3");
        gui_Label_3.setName("Label_3");
        ArrayList<PublicationNews> listPub = ServicePublicationNews.getInstance().displayPubs();
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
        addComponent(gui_Container_1);
        for(PublicationNews pub : listPub){
            addButton(pub.getTitle(),pub.getContent(), pub.getDate(), pub);
        
        }
        */
	}
    
     private void addButton(String title,String content, String date, PublicationNews pub) {
         Container cnt = BorderLayout.center(this);
         Label titleTxt = new Label(title);
         Label dateTxt = new Label(date);
       // gui_Label_1.setText(date);   
        // gui_Label_3.setText(title);
       // gui_Text_Area_1.setText(content);
       cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(titleTxt), BoxLayout.encloseX(dateTxt)));
       add(cnt);
       
    }
        
        //-- DON'T EDIT BELOW THIS LINE!!!
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();

}
