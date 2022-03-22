/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Component;

import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;

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
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

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

    public ClubRubriqueEtudiant(String clubName, String clubImg, String clubDesc, String clubId, String clubCat, String clubRespo) throws IOException {
        int admin = 1; //change
        ArrayList<ClubPub> pubs;
        ArrayList<ClubPub> hangingPubs;

        getStyle().setBgColor(0xffffff);
        Toolbar tb = getToolbar();
        setScrollableY(true);
        Form previous = Display.getInstance().getCurrent();

        tb.setBackCommand("", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
                new ClubsList().show();
            }
        });
        if (admin == 1) {
            tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_MORE_VERT, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new ClubSheet(null, clubId, clubName, clubDesc, clubCat, clubRespo, previous).show();
                }
            });
        }
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle(clubName);

        Container clubC = new Container();
        clubC.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        clubC.getAllStyles().setBgImage(Image.createImage("/bg.PNG").fill(600, 600));
        add(clubC);

        /**
         * round profile picture*
         */
        Container imgandPubs = new Container();
        imgandPubs.setLayout(new BorderLayout());
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

        if (admin == 1) //change
        {
            img.remove();
            /*container for pubs buttons*/
            clubC.getAllStyles().setBgImage(Image.createImage("/bgA.PNG").fill(600, 600));

            Container pubsz = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Font matem = FontImage.getMaterialDesignFont();
            matem = matem.derive(60, Font.STYLE_PLAIN);

            Button pub_attente = new Button();
            pub_attente.setUIID("pubsButtons");

            Button pub_refused = new Button();
            pub_refused.setUIID("pubsButtons");

            Style sIcons = new Style(0xffffff, 0x000000, matem, (byte) 0l);
            pub_attente.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SCHEDULE_SEND, sIcons));
            pub_attente.getAllStyles().setMarginTop(30);
            pub_attente.getAllStyles().setMarginBottom(30);

            pub_attente.getAllStyles().setMarginRight(30);

            hangingPubs = ClubPubService.getInstance().getEnAttentePubs(clubId);
            FloatingActionButton pub_attentebadge = FloatingActionButton.createBadge(Integer.toString(hangingPubs.size()));
            pubsz.add(pub_attentebadge.bindFabToContainer(pub_attente, Component.RIGHT, Component.TOP));
            pub_attentebadge.getParent().revalidate();
            pub_attentebadge.setHidden(true);
            img.getAllStyles().setMarginLeft(170);

            if (!hangingPubs.isEmpty()) {
                pub_attentebadge.setHidden(false);

            }
            //display hanging pubs
            pub_attente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    Dialog dialog = new Dialog(BoxLayout.y());
                    dialog.setScrollableY(true);
                    dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                    Style style = dialog.getDialogStyle();
                    style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                    Label z = new Label("eeeeeeeeeeeeeeeezzzzzzzzzzzzzzz");
                    dialog.add(z);
                    z.getAllStyles().setFgColor(0xF3F3F3);
                    dialog.setDisposeWhenPointerOutOfBounds(true);
                    // body
                    for (ClubPub p : hangingPubs) {
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
                        cardTitleStyles.setBgColor(0x343a40);
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
                        l_nameStyles.setFgColor(0xffffff);
                        l_nameStyles.setFont(poppinsname);

                        /**
                         * club picture
                         */
                        Image pic;
                        try {
                            pic = createImage(Static.ClubPic + clubImg).fill(100, 100);
                            Image roundMask = Image.createImage(pic.getWidth(), pic.getHeight(), 0x000000);
                            Graphics gr = roundMask.getGraphics();
                            gr.fillArc(0, 0, pic.getWidth(), pic.getWidth(), 0, 360);
                            Object mask = roundMask.createMask();
                            pic = pic.applyMask(mask);
                            ImageViewer iv_club = new ImageViewer(pic);
                            cardTitle.add(BorderLayout.WEST, iv_club);

                        } catch (Exception ex) {
                        }

                        /**
                         * * card body
                         */
                        Container cardBody = new Container();
                        cardBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                        Style cardBodyStyles = cardBody.getUnselectedStyle();
                        cardBodyStyles.setBgColor(0x343a40);
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

                        pubdesc.getAllStyles().setFgColor(0xffffff);
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

                            } catch (Exception ex) {

                            }
                        }

                        /*pubPicture*/
                        if (p.getPubImage().length() > 0) {
                            try {
                                //      Image imgs = Image.createImage("file://C:/Users/anash/AppData/Local/Temp/bg.PNG");
                                Image imgs = Image.createImage(Static.ClubPubsPic + p.getPubImage())
                                        .scaledHeight(800);

                                ImageViewer iv = new ImageViewer(imgs);

                                cardBody.add(iv);

                            } catch (Exception ex) {

                            }
                        }

                        Container blacklines = new Container();
                        blacklines.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                        Style blacklinesStyles = blacklines.getUnselectedStyle();
                        blacklinesStyles.setBgColor(0xffffff);
                        blacklinesStyles.setBgTransparency(255);
                        blacklinesStyles.setMarginRight(30);
                        blacklinesStyles.setMarginLeft(30);
                        blacklinesStyles.setPadding(1, 1, 0, 0);

                        blacklines.add(cardBody);

                        /**
                         * delete & add icons
                         */
                        Font acceptPub = FontImage.getMaterialDesignFont();
                        acceptPub = acceptPub.derive(80, Font.STYLE_PLAIN);
                        Button btn_refuse = new Button();
                        btn_refuse.setUIID("RedLabelRight");
                        btn_refuse.setIcon(FontImage.create("\ue5cd", btn_refuse.getUnselectedStyle(), acceptPub));
                        Button btn_accept = new Button();
                        btn_accept.setUIID("GreenLabel");
                        btn_accept.setIcon(FontImage.create("\ue876", btn_accept.getUnselectedStyle(), acceptPub));
                        Container con = new Container();
                        con.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                        con.add(btn_accept).add(btn_refuse);
                        cardTitle.add(BorderLayout.CENTER, l_name);
                        cardTitle.add(BorderLayout.EAST, con);

                        /**
                         * refuse pub
                         */
                        btn_refuse.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent arg0) {

                                Dialog dialog = new Dialog(BoxLayout.y());
                                dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                                Style style = dialog.getDialogStyle();
                                style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                                dialog.setDisposeWhenPointerOutOfBounds(true);
                                // body
                                SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir refuser cette publication?");
                                dialog.add(bodyLabel);
                                // confirm supp button
                                Button confirm_btn = new Button("Oui");
                                confirm_btn.setUIID("IndianredRoundFilledBtn"); //change
                                confirm_btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e2) {
                                        //refuse method
                                        ClubPubService.getInstance().acceptRefusePub(p.getId(), "REFUSE");

                                        try {
                                            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));

                                            new ClubRubriqueEtudiant(clubName, clubImg, clubDesc, clubId, clubCat, clubRespo).show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.toString());
                                        }
                                    }
                                });
                                // deny button
                                Button deny_btn = new Button("Non");
                                deny_btn.setUIID("IndianredRoundBtn");
                                deny_btn.addActionListener(e3 -> {
                                    dialog.dispose();
                                });
                                dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
                                dialog.show();

                            }
                        });

                        /**
                         * accept pub
                         */
                        btn_accept.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent arg0) {
                                Dialog dialog = new Dialog(BoxLayout.y());
                                dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                                Style style = dialog.getDialogStyle();
                                style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                                dialog.setDisposeWhenPointerOutOfBounds(true);
                                // body
                                SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir accepter cette publication?");
                                dialog.add(bodyLabel);
                                // confirm supp button
                                Button confirm_btn = new Button("Oui");
                                confirm_btn.setUIID("RoundFilledBtn");
                                confirm_btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e2) {
                                        //refuse method
                                        ClubPubService.getInstance().acceptRefusePub(p.getId(), "ACCEPT");

                                        try {
                                            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));
                                            new ClubRubriqueEtudiant(clubName, clubImg, clubDesc, clubId, clubCat, clubRespo).show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.toString());
                                        }
                                    }
                                });
                                // deny button
                                Button deny_btn = new Button("Non");
                                deny_btn.setUIID("RoundBtn");
                                deny_btn.addActionListener(e3 -> {
                                    dialog.dispose();
                                });
                                dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
                                dialog.show();
                            }
                        });

                        /**
                         * * card footer
                         */
                        Container cardFooter = new Container();
                        cardFooter.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                        Style cardFooterStyles = cardFooter.getUnselectedStyle();
                        cardFooterStyles.setBgColor(0x343a40);
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
                        pubDateStyles.setFgColor(0xffffff);

                        Font poppinsdate = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                                derive(40, Font.STYLE_PLAIN);

                        pubDateStyles.setFont(poppinsdate);

                        cardFooter.add(pubDate);
                        card.add(cardTitle);
                        card.add(blacklines);
                        card.add(cardFooter);
                        dialog.add(card);

                    }

                    dialog.show();
                }
            });
            imgandPubs.add(BorderLayout.EAST, pubsz);
            imgandPubs.add(BorderLayout.CENTER, img);

            clubC.add(imgandPubs);
        }

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

                if (admin == 1) { //change
                    l_nameStyles.setFgColor(0xffffff);

                    cardTitleStyles.setBgColor(0x343a40);
                }
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
                if (admin == 1)//change
                {

                    pubdesc.getAllStyles().setFgColor(0xffffff);
                    blacklinesStyles.setBgColor(0xffffff);
                    cardBodyStyles.setBgColor(0x343a40);

                    /**
                     * delete & add icons
                     */
                    Font del = FontImage.getMaterialDesignFont();
                    del = del.derive(80, Font.STYLE_PLAIN);
                    Button btn_del = new Button();
                    btn_del.setUIID("RedLabelRight");
                    btn_del.setIcon(FontImage.create("\ue92e", btn_del.getUnselectedStyle(), del));
                    Container con = new Container();
                    con.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                    con.add(btn_del);
                    cardTitle.add(BorderLayout.EAST, con);

                    /**
                     * delete pub
                     */
                    btn_del.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            /*  try {
                        Thread.sleep(2000l);
                    } catch (InterruptedException ex) {
                    }*/
                            Dialog dialog = new Dialog(BoxLayout.y());
                            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                            Style style = dialog.getDialogStyle();
                            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                            dialog.setDisposeWhenPointerOutOfBounds(true);
                            // body
                            SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir supprimer cette publication?");
                            dialog.add(bodyLabel);
                            // confirm supp button
                            Button confirm_btn = new Button("Oui");
                            confirm_btn.setUIID("IndianredRoundFilledBtn"); //change
                            confirm_btn.addActionListener(e2 -> {
                                //delete method
                                ClubPubService.getInstance().deletePub(p.getId());
                                // card.setHidden(true);
                                ToastBar.Status deltStat = ToastBar.getInstance().createStatus();
                                deltStat.setMessage("Publication supprimée");
                                deltStat.show();
                                deltStat.setExpires(2000);
                                dialog.dispose();
                                try {
                                    setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));
                                    new ClubRubrique(clubName, clubImg, clubDesc, clubId).show();
                                } catch (IOException ex) {
                                    System.out.println(ex.toString());
                                }
                            });
                            // deny button
                            Button deny_btn = new Button("Non");
                            deny_btn.setUIID("IndianredRoundBtn");
                            deny_btn.addActionListener(e3 -> {
                                dialog.dispose();
                            });
                            dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
                            dialog.show();

                        }
                    });
                } else {
                    /**
                     * facebook btn
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
                }

                /**
                 * * card footer
                 */
                Container cardFooter = new Container();
                cardFooter.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                Style cardFooterStyles = cardFooter.getUnselectedStyle();
                cardFooterStyles.setBgColor(0xF3F3F3);
                if (admin == 1) {//change
                    cardFooterStyles.setBgColor(0x343a40);
                }
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

                if (admin == 1) {//change
                    pubDateStyles.setFgColor(0xffffff);
                    cardFooterStyles.setBgColor(0x343a40);

                }
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
