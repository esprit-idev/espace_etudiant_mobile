package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.entities.Emploi;
import com.mycompany.services.ServiceEmploi;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;

public class OffresEmplois extends BaseForm{
	public OffresEmplois() {
		Toolbar tb=getToolbar();
		setLayout(new FlowLayout(LEFT,CENTER));
		setTitle("Offres d'emploi");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
                
                //arrayList of job offers 
                ArrayList<Emploi> listPub = ServiceEmploi.getInstance().displayPubs();
        
                //loop through the publications
                for(Emploi pub : listPub){

                    addButton(pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage(), pub.getDate(), pub);

                }
	}

    private void addButton(String title, String content, String categoryName, String image, String date, Emploi pub) {
         Container cnt = new Container(BoxLayout.y());
         Label dateTxt = new Label(date);
         Label catTxt = new Label(categoryName);
         Label contentTxt = new Label(content.substring(0, 40).concat("..."));
         Label titleTxt = new Label(title);
         catTxt.setUIID("RedLabel");
         titleTxt.setUIID("Label_3");
         Image news;
            try {
                news = createImage(Static.News_Emploi_Pic + image);
                ImageViewer img = new ImageViewer(news);
                img.getAllStyles().setAlignment(LEFT);
                cnt.add(img);
            } catch (IOException ex) {
                System.out.print(ex);
            }
      // cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(titleTxt), BoxLayout.encloseX(dateTxt),BoxLayout.encloseX(contentTxt), BoxLayout.encloseX(catTxt)));
      Button myBtn = new Button();
        myBtn.addActionListener(e -> {
             new OffreDetail(pub.getTitle(),pub.getContent(),pub.getCategoryName(),pub.getImage()).show();; 
        });

      cnt.setLeadComponent(myBtn);
      cnt.addAll(titleTxt, dateTxt,catTxt,contentTxt);
      add(cnt);
    }
}
