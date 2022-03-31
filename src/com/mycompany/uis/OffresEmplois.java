package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.Emploi;
import com.mycompany.services.ServiceEmploi;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;

public class OffresEmplois extends Form{
	public OffresEmplois() {
            int admin;
            if (SessionManager.getRoles().equals("ROLE_ADMIN"))
                admin = 1;
                    else
                admin = 0;
            
		Toolbar tb=getToolbar();
		setLayout(new FlowLayout(LEFT,CENTER));
		setTitle("Offres d'emploi");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
            if(admin == 1){
             //add btn
                FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
                RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
                rb.uiid(true);
                fab.bindFabToContainer(getContentPane());
                fab.addActionListener(e -> {
                    new OffreAdd().show();
                });   
            }               
                //arrayList of job offers 
                ArrayList<Emploi> listPub = ServiceEmploi.getInstance().displayPubs();
        
                //loop through the publications
                for(Emploi pub : listPub){

                    addButton(pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage(), pub.getDate(), pub);

                }
	}

    private void addButton(String title, String content, String categoryName, String image, String date, Emploi pub) {
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
                news = createImage(Static.News_Emploi_Pic + image).fill(300, 300);
                ImageViewer img = new ImageViewer(news);
                img.getAllStyles().setAlignment(LEFT);
                cardTitle.add(img);
            } catch (IOException ex) {
                System.out.print(ex);
            }
        Button myBtn = new Button();
        myBtn.addActionListener(e -> {
           new OffreDetail(pub.getId(),pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage()).show();;
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
