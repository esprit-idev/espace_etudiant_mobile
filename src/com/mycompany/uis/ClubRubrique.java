/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.ClubPub;
import com.mycompany.services.ClubPubService;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;

/**
 *
 * @author anash
 */
public class ClubRubrique extends Form {

    public ClubRubrique(String clubName, String clubImg, String clubDesc, String clubId) throws IOException {
        ArrayList<ClubPub> pubs;
        getStyle().setBgColor(0xffffff);
        Toolbar tb = getToolbar();
        setScrollableY(true);
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle(clubName);

        Container clubC = new Container();
        clubC.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        clubC.getAllStyles().setBgImage(Image.createImage("/bg.PNG").fill(600, 600));
        add(clubC);
        /**
         * round profile picture*
         */
        Image picClub = createImage("/" + clubImg).fill(300, 300);
        Image roundMaskClub = Image.createImage(picClub.getWidth(), picClub.getHeight(), 0xff000000);
        Graphics graphics = roundMaskClub.getGraphics();
        graphics.setColor(0xffffff);
        graphics.fillArc(0, 0, picClub.getWidth(), picClub.getWidth(), 0, 360);
        Object maskClub = roundMaskClub.createMask();
        picClub = picClub.applyMask(maskClub);
        ImageViewer img = new ImageViewer(picClub);
        img.getAllStyles().setMarginTop(20);
        clubC.add(img);
        /**
         * club description
         */
        Font clubDescrFont = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                    derive(40, Font.STYLE_BOLD);
        SpanLabel clubDescr = new SpanLabel(clubDesc);
        clubDescr.getTextAllStyles().setFgColor(0xffffff);
        clubDescr.getTextAllStyles().setFont(clubDescrFont);
        clubDescr.getTextAllStyles().setAlignment(CENTER);
        clubDescr.getTextAllStyles().setPadding(2, 2, 2, 2);
        clubC.add(clubDescr);
        /**
         * club pub
         */
        TextArea postText = new TextArea("", 2, 3, TextArea.ANY);
        postText.setMaxSize(500);
        Style postStyle = postText.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        postStyle.setBorder(RoundRectBorder.create().
                strokeColor(2).
                strokeOpacity(120).
                stroke(borderStroke));
        postStyle.setBgColor(0xF3F3F3);
        postStyle.setBgTransparency(255);
        postStyle.setMarginTop(2);
        postStyle.setPadding(2, 2, 2, 2);
        add(postText);
        postText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(Component arg0) {
                postStyle.setBgColor(0xffffff);
                postStyle.setBgTransparency(255);
            }

            @Override
            public void focusLost(Component arg0) {
                postStyle.setBgColor(0xF3F3F3);
                postStyle.setBgTransparency(255);
            }
        });

        /*filename+icon*/
        Container c = new Container();
        c.setLayout(new FlowLayout(RIGHT));
        /**
         * club file/image + button
         */
        Label filename = new Label("zjhdzd.doc");
        Style filenamestyles = filename.getUnselectedStyle();
        filenamestyles.setMarginTop(1);
        c.add(filename);
        /**
         * icon
         */
        Font materialFont = FontImage.getMaterialDesignFont();
        materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
        Label btn_attach = new Label();
        Style attachstyles = btn_attach.getUnselectedStyle();
        attachstyles.setMarginRight(50);
        btn_attach.setIcon(FontImage.create("\ue226", btn_attach.getUnselectedStyle(), materialFont));
        c.add(btn_attach);
        add(c);
        /**
         * button post
         */
        Button btn_post = new Button("Ajouter");
        btn_post.getStyle().setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120)
        );
        add(btn_post);

        pubs = ClubPubService.getInstance().getAllPubs(clubId);
        for (ClubPub p : pubs) {
            /*SpanLabel s = new SpanLabel();
            s.setText(
                    Jsoup.parse(p.getPubDesc()).text()
                 
                    + " "
                  //  + p.getPubImage().split("=")[1].replaceAll("}", "")
                    + " "
                    + p.getPubDate()
                    + " "
                    + p.getPubImage()
                    + " "
                 //   + p.getClubRespo().split("=")[1].replaceAll("}", "")
            );
            add(s);*/
            /**
             * * card
             */

            Container card = new Container(BoxLayout.y());
            card.getStyle().setBorder(RoundRectBorder.create().shadowOpacity(100));
            card.getStyle().setMarginTop(10);

            /**
             * * card header
             */
            Container cardTitle = new Container();
            cardTitle.setLayout(new BoxLayout(BoxLayout.X_AXIS));
            Style cardTitleStyles = cardTitle.getUnselectedStyle();
            cardTitleStyles.setBgColor(0xF3F3F3);
            cardTitleStyles.setBgTransparency(255);
            cardTitleStyles.setMarginRight(30);
            cardTitleStyles.setMarginLeft(30);
            cardTitleStyles.setBorder(RoundRectBorder.create().
                    bottomOnlyMode(false).
                    strokeColor(0).
                    strokeOpacity(120));
            cardTitleStyles.setPadding(20, 20, 10, 10);
            /**
             * club name
             */
            Label l_name = new Label(clubName);
            Font poppinsname = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                    derive(50, Font.STYLE_PLAIN);
            Style l_nameStyles = l_name.getUnselectedStyle();
            l_nameStyles.setFgColor(0x000000);
            l_nameStyles.setFont(poppinsname);

            /**
             * club picture
             */
            Image pic = createImage("/" + clubImg).fill(100, 100);
            Image roundMask = Image.createImage(pic.getWidth(), pic.getHeight(), 0x000000);
            Graphics gr = roundMask.getGraphics();
            gr.fillArc(0, 0, pic.getWidth(), pic.getWidth(), 0, 360);
            Object mask = roundMask.createMask();
            pic = pic.applyMask(mask);
            ImageViewer iv_club = new ImageViewer(pic);

            cardTitle.add(iv_club);
            cardTitle.add(l_name);

            /**
             * * card body
             */
            Container cardBody = new Container();
            cardBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            Style cardBodyStyles = cardBody.getUnselectedStyle();
            cardBodyStyles.setBgColor(0xF3F3F3);
            cardBodyStyles.setBgTransparency(255);

            cardBodyStyles.setPadding(20, 20, 10, 10);
            /**
             * pub description
             */
            SpanLabel pubdesc = new SpanLabel(Jsoup.parse(p.getPubDesc()).text());
            pubdesc.getTextAllStyles().setFgColor(0x000000);
            cardBody.add(pubdesc);
            Font poppinsdesc = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                    derive(40, Font.STYLE_PLAIN);
            pubdesc.getTextAllStyles().setFont(poppinsdesc);

            Container blacklines = new Container();
            blacklines.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            Style blacklinesStyles = blacklines.getUnselectedStyle();
            blacklinesStyles.setBgColor(0xA0A0A0);
            blacklinesStyles.setBgTransparency(255);
            blacklinesStyles.setMarginRight(30);
            blacklinesStyles.setMarginLeft(30);
            blacklinesStyles.setPadding(1, 1, 0, 0);

            blacklines.add(cardBody);

            /**
             * * card footer
             */
            Container cardFooter = new Container();
            cardFooter.setLayout(new BoxLayout(BoxLayout.X_AXIS));
            Style cardFooterStyles = cardFooter.getUnselectedStyle();
            cardFooterStyles.setBgColor(0xF3F3F3);
            cardFooterStyles.setBgTransparency(255);
            cardFooterStyles.setMarginRight(30);
            cardFooterStyles.setMarginLeft(30);
            cardFooterStyles.setMarginBottom(30);
            cardFooterStyles.setBorder(RoundRectBorder.create().
                    topOnlyMode(false).
                    strokeColor(0).
                    strokeOpacity(120));
            cardFooterStyles.setPadding(20, 20, 10, 10);
            /**
             * pub date
             */
            Label pubDate = new Label("Publié le " + Util.split(p.getPubDate(), "T")[0] + ".");
            Style pubDateStyles = pubDate.getUnselectedStyle();
            pubDateStyles.setFgColor(0x000000);

            Font poppinsdate = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                    derive(40, Font.STYLE_PLAIN);

            pubDateStyles.setFont(poppinsdate);

            cardFooter.add(pubDate);
            card.add(cardTitle);
            card.add(blacklines);
            card.add(cardFooter);
            add(card);
            /* add(cardTitle);
            add(cardBody);
            add(cardFooter);*/
        }

    }
}
