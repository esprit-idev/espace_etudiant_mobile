package com.mycompany.uis;

import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

public class TabAff extends Form{
	public TabAff() {
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
            	new Login().show();
                }
        });
        
	}

}
