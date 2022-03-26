package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.components.ToastBar;
import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.PublicationNews;
import com.mycompany.services.ServicePublicationNews;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;



public class TabAff extends Form {

    public TabAff(){
        setScrollableY(true);
        setLayout(new FlowLayout(LEFT,TOP));
        int admin=1;
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Tableau d'affichage");
        Toolbar tb = getToolbar();
        //tableau d'affichage
        tb.addMaterialCommandToSideMenu("Tableau d'affichage", FontImage.MATERIAL_DASHBOARD, new ActionListener<ActionEvent>() {

            public void actionPerformed(ActionEvent evt) {
                    new TabAff().show();
            }
        });
        if(admin==1){
            //etudiants
        tb.addMaterialCommandToSideMenu("Etudiants", FontImage.MATERIAL_PEOPLE, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                //liste des etudaints pour backoffice
            }
        });
            //administrateurs
        tb.addMaterialCommandToSideMenu("Admins", FontImage.MATERIAL_PEOPLE, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                //liste des administrateurs pour backoffice
            }
        });
        
            //niveaux d'études
        tb.addMaterialCommandToSideMenu("Niveaux d'études", FontImage.MATERIAL_SCHOOL, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                //liste des niveaux d'études pour backoffice
            }
        });
        
            //matières
        tb.addMaterialCommandToSideMenu("Matières", FontImage.MATERIAL_MENU_BOOK, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                //new MatiereList().show();
            }
        });
        //categorie publication
         tb.addMaterialCommandToSideMenu("Categorie News", FontImage.MATERIAL_MENU_BOOK, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                new NewsCategory().show();
            }
        });
         //categorie e;plois
         tb.addMaterialCommandToSideMenu("Categorie Emplois", FontImage.MATERIAL_MENU_BOOK, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                new OffreCategory().show();
            }
        });
        //fab button to add news:
         FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
            RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
            rb.uiid(true);
            fab.bindFabToContainer(getContentPane());
            fab.addActionListener(e -> {
                new NewsAdd().show();
            });

        }

        //forum
        tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_FORUM, new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent evt) {
                new Forum().show();
            }
        });
        //clubs
        tb.addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_GROUPS, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new ClubsList().show(); // only show the status for 3 seconds, then have it automatically clear
            }
        });

        //offres d'emploi
        tb.addMaterialCommandToSideMenu("Offres d'emploi", FontImage.MATERIAL_WORK, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new OffresEmplois().show();
            }
        });

        if(admin!=1){
            //profile
        tb.addMaterialCommandToSideMenu("Mon profile", FontImage.MATERIAL_SUPERVISOR_ACCOUNT, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new Profile().show();
            }
        });
        }

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
            display(pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage(), pub.getDate(),pub);
        }    
	}
    
     private void display(String title,String content,String categoryName,String newsImg, String date, PublicationNews pub){
         Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));    
         Label dateTxt = new Label(date);
         Label catTxt = new Label(categoryName);
         Label contentTxt = new Label(content);
         Label titleTxt = new Label(title);
         catTxt.setUIID("RedLabel");
         titleTxt.setUIID("Label_3");
         dateTxt.setTextPosition(RIGHT);
         /*
            Container header
         */
         Container cardTitle = new Container();
         cardTitle.setLayout(new BoxLayout(BoxLayout.X_AXIS));
          Style cardTitleStyles = cardTitle.getUnselectedStyle();
            cardTitleStyles.setBgColor(0xF3F3F3);
            cardTitleStyles.setBgTransparency(255);
            cardTitleStyles.setPadding(10, 10, 20, 20);
            cardTitleStyles.setBorder(RoundRectBorder.create().
                            bottomOnlyMode(true).
                            strokeColor( 0x858585).
                            strokeOpacity(999999));
         /*
            Container content
         */
         Container cardContent = new Container();
         cardContent.setLayout(new BoxLayout(BoxLayout.X_AXIS));
         Container cardInfo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
           Style cardContentStyles = cardContent.getUnselectedStyle();
            cardContentStyles.setBgColor(0xF3F3F3);
            cardContentStyles.setBgTransparency(255);
            cardContentStyles.setMarginBottom(30);
            cardContentStyles.setPadding(10, 10, 20, 20);

         /*
            Image
         */
         Image news;
            try {
                news = createImage(Static.News_Emploi_Pic + newsImg).fill(300, 300);
                ImageViewer img = new ImageViewer(news);
                img.getAllStyles().setAlignment(LEFT);
                cardTitle.add(img);
            } catch (IOException ex) {
                System.out.print(ex);
            }
        Button myBtn = new Button();
        myBtn.addActionListener(e -> {
           new NewsDetail(pub.getId(),pub.getTitle(),pub.getContent(),pub.getOwner(),pub.getCategoryName(),pub.getComments(),pub.getImage(),pub).show();
        });
        cardTitle.add(titleTxt);
        cardInfo.add(dateTxt);
        cardInfo.add(catTxt);
        cardInfo.add(contentTxt);
        cardContent.add(cardInfo);
        cnt.setLeadComponent(myBtn);
        cnt.add(cardTitle);
        cnt.add(cardContent);
        add(cnt);
       
    }

}
