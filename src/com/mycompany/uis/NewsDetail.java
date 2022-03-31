/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.PublicationNews;
import com.mycompany.services.ClubPubService;
import com.mycompany.services.ServicePublicationNews;
import com.mycompany.utils.Static;
import java.io.IOException;

/**
 *
 * @author Eslem Nabitt
 */
public class NewsDetail extends Form {
    
    public NewsDetail(Resources res, int Id,String Title,String Content, String Owner, String Category,String comments, String NewsImg,PublicationNews pub){
        int admin;
            if (SessionManager.getRoles().equals("ROLE_ADMIN"))
                admin = 1;
                    else
                admin = 0;       
        Toolbar tb=getToolbar();
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
            if (admin == 1) {
                tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_MORE_VERT, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        new NewsSheet(res ,null, Id, Title,Owner,Content,Category,previous).show();
                    }
                });
            }
                setScrollableY(true);
		Container cnt = new Container();
                cnt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		setTitle(Title);
		
                Label titleTxt = new Label(Title);
                Label catTxt = new Label(Category);
                Label ownerTxt = new Label("Ecrit par : " +Owner);
                Label commentsArea = new Label("Commentaires");
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
               // contentTxt.setUIID("SmallFont");
                 //    comment.p
                 //Container commentCnt = new Container(BoxLayout.xCenter());
                TextField comment = new TextField("","Commentaire", 10, TextArea.ANY);
                /*
                    comments
                */
                
                Button commentbtn = new Button("Ajouter");
                Container commentCnt = BoxLayout.encloseY(
                GridLayout.encloseIn(2, comment, commentbtn));
                commentbtn.addActionListener(e ->{
                    String mb = comment.getText();
                    Label commentTxt = new Label(mb);
                    Style commentStyle = commentCnt.getAllStyles();
                    Stroke borderStroke = new Stroke(1, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
                    commentStyle.setBorder(RoundRectBorder.create().
                        strokeColor(0).
                        strokeOpacity(20).
                        stroke(borderStroke));
                    commentStyle.setBgColor(0xffffff);
                    commentStyle.setBgTransparency(255);
                    commentStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
                    commentStyle.setMargin(Component.BOTTOM, 4);
                    commentCnt.add(mb);
                    
                });
                 /*
                    image
                */
                
                Image offre;
                try {
                    int width = Display.getInstance().getDisplayWidth();
                    int height = Display.getInstance().getDisplayHeight();
                    offre = createImage(Static.News_Emploi_Pic + NewsImg).scaled(800,900);
                    ImageViewer offreImg = new ImageViewer(offre);
                    offreImg.getAllStyles().setAlignment(LEFT);
                    cnt.add(offreImg);
                } catch (IOException ex) {
                    System.out.print(ex);
                }
                
               // cnt.add(titleTxt);
                cnt.add(ownerTxt);
                cnt.add(catTxt);
                cnt.add(contentTxt);
                if (admin == 0) {
                cnt.add(commentsArea);
                cnt.add(commentCnt);
                }
                add(cnt);
    }
}
