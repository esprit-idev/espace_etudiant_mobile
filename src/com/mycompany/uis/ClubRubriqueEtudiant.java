/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.io.Util;
import com.codename1.ui.Button;

import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;

import com.codename1.ui.Display;
import com.codename1.ui.Font;

import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.ClubPub;
import com.mycompany.services.ClubPubService;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;

/**
 *
 * @author anash
 */
public class ClubRubriqueEtudiant extends Form {

    public ClubRubriqueEtudiant(String clubName, String clubImg, String clubDesc, String clubId) throws IOException {
        ArrayList<ClubPub> pubs;
        getStyle().setBgColor(0xffffff);
        Toolbar tb = getToolbar();
        setScrollableY(true);
        tb.setBackCommand("", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
                new ClubsList().show();
            }
        });
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle(clubName);

        Container clubC = new Container();
        clubC.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        clubC.getAllStyles().setBgImage(Image.createImage("/bg.PNG").fill(600, 600));
        add(clubC);

        /**
         * round profile picture*
         */
        Image picClub = createImage(Static.ClubPic + clubImg).fill(300, 300);
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
        TextArea clubDescr = new TextArea(clubDesc);
        clubDescr.setEditable(false);
        clubDescr.setEnabled(false);
        Stroke borderStrokeclubDescr = new Stroke(0, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        clubDescr.getAllStyles().setBorder(RoundRectBorder.create().
                strokeColor(2).
                strokeOpacity(120).
                stroke(borderStrokeclubDescr));

        clubDescr.getAllStyles().setFgColor(0xffffff);
        clubDescr.getAllStyles().setFont(clubDescrFont);
        clubDescr.getAllStyles().setAlignment(CENTER);
        clubDescr.getAllStyles().setPadding(0, 2, 2, 2);

        /*edit club desc*/
        Container titleDesc = new Container();
        titleDesc.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        Label descC = new Label("Description du club :");
        Font clubDescrTitle = Font.createTrueTypeFont("dsss", "Poppins-Regular.ttf").
                derive(50, Font.STYLE_BOLD);
        descC.getAllStyles().setFgColor(0xfffffff);
        descC.getAllStyles().setMarginTop(10);

        descC.getAllStyles().setMarginLeft(30);
        descC.getAllStyles().setFont(clubDescrTitle);
        /*desc title*/
        titleDesc.add(descC);

        clubC.add(titleDesc);
        clubC.add(clubDescr);

        // actual pubs
        pubs = ClubPubService.getInstance().getAllPubs(clubId);
        if (pubs.isEmpty()) {
            add(new Label("Pas de publciations"));
        } else {
            for (ClubPub p : pubs) {
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
                cardTitle.setLayout(new BorderLayout());
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
                Image pic = createImage(Static.ClubPic + clubImg).fill(100, 100);
                Image roundMask = Image.createImage(pic.getWidth(), pic.getHeight(), 0x000000);
                Graphics gr = roundMask.getGraphics();
                gr.fillArc(0, 0, pic.getWidth(), pic.getWidth(), 0, 360);
                Object mask = roundMask.createMask();
                pic = pic.applyMask(mask);
                ImageViewer iv_club = new ImageViewer(pic);

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
                TextArea pubdesc = new TextArea(
                        Jsoup.parse(p.getPubDesc()).text()
                );
                pubdesc.setEditable(false);
                pubdesc.setEnabled(false);

                Stroke borderStrokepubdesc = new Stroke(0, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
                pubdesc.getAllStyles().setBorder(RoundRectBorder.create().
                        strokeColor(2).
                        strokeOpacity(120).
                        stroke(borderStrokepubdesc));

                pubdesc.getAllStyles().setFgColor(0x000000);
                cardBody.add(pubdesc);
                Font poppinsdesc = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                        derive(40, Font.STYLE_PLAIN);
                pubdesc.getAllStyles().setFont(poppinsdesc);


                /*pubfile*/
                if (p.getPubFile().length() > 0) {
                    try {

                        Button displayFile = new Button("Attatched file: " + p.getPubFile());
                        displayFile.setUIID("btnFilePub");
                        displayFile.getAllStyles().setBorder(Border.createEmpty());
                        displayFile.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
                        displayFile.addActionListener(e -> {
                            String fileName = Static.ClubPubsFile + p.getPubFile();
                            try {
                                Display.getInstance().execute(fileName);
                            } catch (Exception es) {
                            }

                        });

                        cardBody.add(displayFile);
                        revalidate();

                    } catch (Exception ex) {

                    }
                }

                /*pubPicture*/
                if (p.getPubImage().length() > 0) {
                    try {
                        Image imgs = Image.createImage(Static.ClubPubsPic + p.getPubImage())
                                .scaledHeight(800);

                        ImageViewer iv = new ImageViewer(imgs);

                        cardBody.add(iv);
                        revalidate();

                    } catch (Exception ex) {

                    }
                }

                Container blacklines = new Container();
                blacklines.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                Style blacklinesStyles = blacklines.getUnselectedStyle();
                blacklinesStyles.setBgColor(0xA0A0A0);
                blacklinesStyles.setBgTransparency(255);
                blacklinesStyles.setMarginRight(30);
                blacklinesStyles.setMarginLeft(30);
                blacklinesStyles.setPadding(1, 1, 0, 0);

                blacklines.add(cardBody);

                cardTitle.add(BorderLayout.WEST, iv_club);
                cardTitle.add(BorderLayout.CENTER, l_name);
                /**
                 * delete & add icons
                 */

                Button btn_share = new Button();
                btn_share.setUIID("RedLabelRight");
                btn_share.setIcon(Image.createImage("/fb.png").scaled(210, 70));
                cardTitle.add(BorderLayout.EAST, btn_share);
                btn_share.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        String fileName = "https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2F127.0.0.1%3A8000%2FdisplayPub%2F" + p.getId() + "%2F" + clubId + "&amp;src=sdkpreparse";
                        try {
                            Display.getInstance().execute(fileName);
                        } catch (Exception es) {
                        }
                    }
                });
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
}
