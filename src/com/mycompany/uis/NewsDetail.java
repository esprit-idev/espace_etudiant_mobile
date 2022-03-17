/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.entities.PublicationNews;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServicePublicationNews;

/**
 *
 * @author Eslem Nabitt
 */
public class NewsDetail extends BaseForm {
    
    PublicationNews pub;
    public NewsDetail(){
                Toolbar tb=getToolbar();
		setLayout(new FlowLayout(CENTER,CENTER));
		//setTitle("hello");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
                initGuiBuilderComponents();
    }
      private void initGuiBuilderComponents() {
        PublicationNews onepub = ServicePublicationNews.getInstance().onePublication(pub.getId(), pub);
        Container cnt = new Container(BoxLayout.y());
         Label dateTxt = new Label(onepub.getDate());
         Label catTxt = new Label(onepub.getCategoryName().substring(14, pub.getCategoryName().length()-1));
         Label contentTxt = new Label(onepub.getContent().substring(0, 40).concat("..."));
         Label titleTxt = new Label(onepub.getTitle());
         catTxt.setUIID("RedLabel");
         titleTxt.setUIID("Label_3");
         dateTxt.setTextPosition(RIGHT);
           cnt.addAll(titleTxt, dateTxt,catTxt,contentTxt);
      add(cnt);
    }
}
