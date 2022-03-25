/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.utils.Static;
import java.io.IOException;

/**
 *
 * @author eslem
 */
public class OffreDetail extends Form {
    
      public OffreDetail(int Id,String Title,String Content,String Category,String img){     
                int admin = 1;
                Form previous = Display.getInstance().getCurrent();
                Toolbar tb = getToolbar();
                tb.setBackCommand("", e -> previous.showBack());
                if (admin == 1) {
                tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_MORE_VERT, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        new NewsSheet(null, Id, Title,Content,Category,previous).show();
                    }
                });
            }
                setScrollableY(true);
		Container cnt = new Container();
                cnt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                cnt.getAllStyles().setMarginTop(5);
		setTitle(Title);
		
                Label titleTxt = new Label(Title);
                Label catTxt = new Label(Category);
                SpanLabel contentTxt = new SpanLabel(Content);
               
                Font clubDescrFont = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                    derive(40, Font.STYLE_BOLD);
                titleTxt.setUIID("WelcomeTitle");
                titleTxt.setAlignment(LEFT);
                catTxt.setAlignment(LEFT);
                catTxt.setUIID("SmallThinLabel");
                catTxt.setUIID("RedLabel");
                contentTxt.setUIID("WelcomeContent");
                contentTxt.getTextAllStyles().setFgColor(0x000000);
                contentTxt.getTextAllStyles().setFont(clubDescrFont);
                contentTxt.getTextAllStyles().setAlignment(LEFT);
                contentTxt.getTextAllStyles().setPadding(2, 2, 2, 2);
                /*
                    image
                */     
                Image offre;
                try {
                    offre = createImage(Static.News_Emploi_Pic + img).scaled(800,900);
                    ImageViewer offreImg = new ImageViewer(offre);
                    offreImg.getAllStyles().setAlignment(LEFT);
                    cnt.add(offreImg);
                } catch (IOException ex) {
                    System.out.print(ex);
                }
                
                cnt.add(titleTxt);
                cnt.add(catTxt);
                cnt.add(contentTxt);
                add(cnt);
    }
}
