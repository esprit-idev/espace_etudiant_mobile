/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;

import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
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
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.Club;
import com.mycompany.entities.ClubPub;
import com.mycompany.services.ClubPubService;
import com.mycompany.services.ClubService;
import com.mycompany.utils.Static;
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
        ArrayList<ClubPub> hangingPubs;
        ArrayList<ClubPub> refusedPubs;

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

        /*container for pubs buttons*/
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

        pub_refused.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UNPUBLISHED, sIcons));
        pub_refused.getAllStyles().setMarginTop(30);
        pub_refused.getAllStyles().setMarginRight(30);

        refusedPubs = ClubPubService.getInstance().getRefusedPubs(clubId);
        FloatingActionButton pub_refusedbadge = FloatingActionButton.createBadge(Integer.toString(refusedPubs.size()));
        pubsz.add(pub_refusedbadge.bindFabToContainer(pub_refused, Component.RIGHT, Component.TOP));
        pub_refusedbadge.getParent().revalidate();
        pub_refusedbadge.setHidden(true);

        if (!refusedPubs.isEmpty()) {
            pub_refusedbadge.setHidden(false);

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
                    Button btn_update_pub = new Button("Valider");
                    btn_update_pub.setUIID("addImgBtn");
                    btn_update_pub.setHidden(true);
                    cardBody.add(btn_update_pub);
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
                    blacklinesStyles.setBgColor(0xA0A0A0);
                    blacklinesStyles.setBgTransparency(255);
                    blacklinesStyles.setMarginRight(30);
                    blacklinesStyles.setMarginLeft(30);
                    blacklinesStyles.setPadding(1, 1, 0, 0);

                    blacklines.add(cardBody);

                    /**
                     * delete & add icons
                     */
                    Font del = FontImage.getMaterialDesignFont();
                    del = del.derive(80, Font.STYLE_PLAIN);
                    Button btn_del = new Button();
                    btn_del.setUIID("RedLabelRight");
                    btn_del.setIcon(FontImage.create("\ue92e", btn_del.getUnselectedStyle(), del));
                    Button btn_edit = new Button();
                    btn_edit.setUIID("GreenLabel");
                    btn_edit.setIcon(FontImage.create("\ue3c9", btn_edit.getUnselectedStyle(), del));
                    Container con = new Container();
                    con.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                    con.add(btn_edit).add(btn_del);
                    cardTitle.add(BorderLayout.CENTER, l_name);
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

                    /**
                     * edit pub
                     */
                    btn_edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            if (btn_update_pub.isHidden()) {
                                btn_update_pub.setHidden(false);
                                pubdesc.setEditable(true);
                                pubdesc.setEnabled(true);
                                cardBody.animateLayout(100);
                            } else if (!btn_update_pub.isHidden() && pubdesc.getText().equals(p.getPubDesc())) {

                                btn_update_pub.setHidden(true);
                                pubdesc.setEditable(false);
                                pubdesc.setEnabled(false);
                                cardBody.animateLayout(100);
                            }
                        }
                    });

                    btn_update_pub.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            pubsz.animateLayout(100);

                            if (pubdesc.getText().length() == 0) {
                                // Dialog.show("Alert", "La publication ne peut pas étre vide", new Command("ok"));
                                Dialog d = new Dialog("Alert");

                                TextArea popupBody = new TextArea("La publication ne peut pas étre vide", 3, 10);
                                Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                                        derive(40, Font.STYLE_PLAIN);
                                popupBody.setUIID("PopupBody");
                                popupBody.setEditable(false);
                                popupBody.getAllStyles().setFont(poppinsAlert);

                                d.setLayout(new BorderLayout());
                                d.add(BorderLayout.CENTER, popupBody);
                                d.showPopupDialog(pubdesc);
                            } else {

                                pubdesc.setEditable(false);
                                pubdesc.setEnabled(false);
                                btn_update_pub.setHidden(true);

                                ToastBar.Status statuss = ToastBar.getInstance().createStatus();
                                statuss.setMessage("Modification de la publication...");
                                statuss.setShowProgressIndicator(true);
                                statuss.show();
                                statuss.setExpires(2000);
                                ClubPubService.getInstance().editPubDesc(p.getId(), pubdesc.getText());

                                ToastBar.Status status = ToastBar.getInstance().createStatus();
                                status.setMessage("Publication modifiée, attendez l'approbation de l'administrateur");
                                Font materialFont = FontImage.getMaterialDesignFont();
                                materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
                                Image icOKay = FontImage.create("\ue876", btn_update_pub.getUnselectedStyle(), materialFont);
                                status.setIcon(icOKay);
                                status.showDelayed(5000);
                                status.setExpires(6000);
                                try {
                                    setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));

                                    new ClubRubrique(clubName, clubImg, clubDesc, clubId).show();
                                } catch (IOException ex) {
                                    System.out.println(ex.toString());
                                }

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
                    dialog.add(card);

                }

                dialog.show();
            }
        });
        //display refused pubs
        pub_refused.addActionListener(new ActionListener() {
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
                for (ClubPub p : refusedPubs) {
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
                    Button btn_update_pub = new Button("Valider");
                    btn_update_pub.setUIID("addImgBtn");
                    btn_update_pub.setHidden(true);
                    cardBody.add(btn_update_pub);

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

                    /**
                     * delete & add icons
                     */
                    Font del = FontImage.getMaterialDesignFont();
                    del = del.derive(80, Font.STYLE_PLAIN);
                    Button btn_del = new Button();
                    btn_del.setUIID("RedLabelRight");
                    btn_del.setIcon(FontImage.create("\ue92e", btn_del.getUnselectedStyle(), del));
                    Button btn_edit = new Button();
                    btn_edit.setUIID("GreenLabel");
                    btn_edit.setIcon(FontImage.create("\ue3c9", btn_edit.getUnselectedStyle(), del));
                    Container con = new Container();
                    con.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                    con.add(btn_edit).add(btn_del);
                    cardTitle.add(BorderLayout.CENTER, l_name);
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

                    /**
                     * edit pub
                     */
                    btn_edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            if (btn_update_pub.isHidden()) {
                                btn_update_pub.setHidden(false);
                                pubdesc.setEditable(true);
                                pubdesc.setEnabled(true);
                                cardBody.animateLayout(100);
                            } else if (!btn_update_pub.isHidden() && pubdesc.getText().equals(p.getPubDesc())) {

                                btn_update_pub.setHidden(true);
                                pubdesc.setEditable(false);
                                pubdesc.setEnabled(false);
                                cardBody.animateLayout(100);
                            }
                        }
                    });

                    btn_update_pub.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            pubsz.animateLayout(100);

                            if (pubdesc.getText().length() == 0) {
                                // Dialog.show("Alert", "La publication ne peut pas étre vide", new Command("ok"));
                                Dialog d = new Dialog("Alert");

                                TextArea popupBody = new TextArea("La publication ne peut pas étre vide", 3, 10);
                                Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                                        derive(40, Font.STYLE_PLAIN);
                                popupBody.setUIID("PopupBody");
                                popupBody.setEditable(false);
                                popupBody.getAllStyles().setFont(poppinsAlert);

                                d.setLayout(new BorderLayout());
                                d.add(BorderLayout.CENTER, popupBody);
                                d.showPopupDialog(pubdesc);
                            } else {

                                pubdesc.setEditable(false);
                                pubdesc.setEnabled(false);
                                btn_update_pub.setHidden(true);

                                ToastBar.Status statuss = ToastBar.getInstance().createStatus();
                                statuss.setMessage("Modification de la publication...");
                                statuss.setShowProgressIndicator(true);
                                statuss.show();
                                statuss.setExpires(2000);
                                ClubPubService.getInstance().editPubDesc(p.getId(), pubdesc.getText());

                                ToastBar.Status status = ToastBar.getInstance().createStatus();
                                status.setMessage("Publication modifiée, attendez l'approbation de l'administrateur");
                                Font materialFont = FontImage.getMaterialDesignFont();
                                materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
                                Image icOKay = FontImage.create("\ue876", btn_update_pub.getUnselectedStyle(), materialFont);
                                status.setIcon(icOKay);
                                status.showDelayed(5000);
                                status.setExpires(6000);
                                try {
                                    setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));
                                    new ClubRubrique(clubName, clubImg, clubDesc, clubId).show();
                                } catch (IOException ex) {
                                    System.out.println(ex.toString());
                                }

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
                    dialog.add(card);

                }

                dialog.show();
            }
        });

        imgandPubs.add(BorderLayout.EAST, pubsz);

        clubC.add(imgandPubs);
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

        Font edit = FontImage.getMaterialDesignFont();
        edit = edit.derive(60, Font.STYLE_PLAIN);
        Button btn_edit_C_desc = new Button();
        btn_edit_C_desc.setUIID("SmallWhileLabel");
        btn_edit_C_desc.setIcon(FontImage.create("\ue3c9", btn_edit_C_desc.getUnselectedStyle(), edit));

        /*  button valider edition club desc*/
        Button btn_valid_edit = new Button("Valider");
        btn_valid_edit.setUIID("addPubBtn");
        btn_valid_edit.setHidden(true);
        btn_valid_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (clubDescr.getText().length() == 0) {
                    // Dialog.show("Alert", "La publication ne peut pas étre vide", new Command("ok"));
                    Dialog d = new Dialog("Alert");

                    TextArea popupBody = new TextArea("La description ne peut pas étre vide", 3, 10);
                    Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                            derive(40, Font.STYLE_PLAIN);
                    popupBody.setUIID("PopupBody");
                    popupBody.setEditable(false);
                    popupBody.getAllStyles().setFont(poppinsAlert);

                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, popupBody);
                    d.showPopupDialog(clubDescr);
                } else {

                    clubDescr.setEditable(false);
                    clubDescr.setEnabled(false);
                    btn_valid_edit.setHidden(true);
                    /* toast desc club edited*/
                    ToastBar.Status statuss = ToastBar.getInstance().createStatus();
                    statuss.setMessage("Modification de la description...");
                    statuss.setShowProgressIndicator(true);

                    statuss.show();
                    statuss.setExpires(2000);
                    ClubService.getInstance().editClubDesc(clubId, clubDescr.getText());

                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setMessage("Description modifiée");
                    Font materialFont = FontImage.getMaterialDesignFont();
                    materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
                    Image icOKay = FontImage.create("\ue876", btn_valid_edit.getUnselectedStyle(), materialFont);
                    status.setIcon(icOKay);
                    status.showDelayed(5000);
                    status.setExpires(6000);
                }

            }
        });


        /*editClub desc icon*/
        btn_edit_C_desc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (btn_valid_edit.isHidden()) {
                    btn_valid_edit.setHidden(false);
                    clubC.animateLayout(100);
                    clubDescr.setEditable(true);
                    clubDescr.setEnabled(true);
                } else if (!btn_valid_edit.isHidden() && clubDescr.getText().equals(clubDesc)) {
                    btn_valid_edit.setHidden(true);
                    clubC.animateLayout(100);
                    clubDescr.setEditable(false);
                    clubDescr.setEnabled(false);
                }

            }
        });
        /*desc title*/
        titleDesc.add(descC);
        titleDesc.add(btn_edit_C_desc);

        clubC.add(titleDesc);
        clubC.add(clubDescr);
        clubC.add(btn_valid_edit);

        /**
         * add club pub
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
        Font fontPubDesc = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_BOLD);
        postStyle.setFont(fontPubDesc);

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
        Label filename = new Label("");
        Style filenamestyles = filename.getUnselectedStyle();
        filenamestyles.setMarginTop(1);
        c.add(filename);
        /**
         * icon
         */
        Font materialFont = FontImage.getMaterialDesignFont();
        materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
        Button btn_attach = new Button("Image");
        btn_attach.setUIID("addImgBtn");
        /*add imagefile*/
        Style sIcon = new Style(0xffffff, 0x000000, materialFont, (byte) 0l);
        btn_attach.setIcon(FontImage.create("\ue412", sIcon));
        /*select pic*/
        btn_attach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                try {
                    filename.setText(filePath);
                } catch (Exception e) {
                    filename.setText("");

                }
                System.out.println(filename.getText());
                c.animateLayout(100);

            }
        });
        c.add(btn_attach);
        add(c);
        /**
         * button add pub
         */
        Button btn_addPub = new Button("Ajouter");
        btn_addPub.setUIID("addPubBtn");
        /*btn_post.getStyle().setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120)
        );*/
        add(btn_addPub);
        btn_addPub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (postText.getText().length() == 0) {
                    // Dialog.show("Alert", "La publication ne peut pas étre vide", new Command("ok"));
                    Dialog d = new Dialog("Alert");

                    TextArea popupBody = new TextArea("La publication ne peut pas étre vide", 3, 10);
                    Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                            derive(40, Font.STYLE_PLAIN);
                    popupBody.setUIID("PopupBody");
                    popupBody.setEditable(false);
                    popupBody.getAllStyles().setFont(poppinsAlert);

                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, popupBody);
                    d.showPopupDialog(postText);
                } else {
                    ToastBar.Status statuss = ToastBar.getInstance().createStatus();
                    statuss.setMessage("Envoi de la publication...");
                    statuss.setShowProgressIndicator(true);
                    statuss.show();
                    statuss.setExpires(2000);
                    String pubPicName = "";
                    if (filename.getText().length() != 0) {
                        pubPicName = Util.getUUID() + ".jpg";
                        MultipartRequest cr = new MultipartRequest() {

                            @Override
                            protected void handleErrorResponseCode(int code, String message) {
                                if (code == 500) {
                                    //Do what you want here
                                }
                            }
                        };
                        String url = Static.BASE_URL + "/uploadPubPic";

                        cr.setUrl(url);
                        cr.setPost(false);
                        String mime = "image/jpeg";
                        System.out.println(cr.getUrl());

                        try {
                            cr.addData("file", filename.getText(), mime);
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                        cr.setFilename("file", pubPicName);//any unique name you want

                        InfiniteProgress prog = new InfiniteProgress();
                        //    Dialog dlg = prog.showInifiniteBlocking();
                        //   cr.setDisposeOnCompletion(dlg);
                        NetworkManager.getInstance().addToQueueAndWait(cr);

                    }
                    ClubPubService.getInstance().addPub(clubId, postText.getText(), pubPicName);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setMessage("Publication envoyée, attendez l'approbation de l'administrateur");
                    Font materialFont = FontImage.getMaterialDesignFont();
                    materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
                    Image icOKay = FontImage.create("\ue876", btn_addPub.getUnselectedStyle(), materialFont);
                    status.setIcon(icOKay);
                    status.showDelayed(5000);
                    status.setExpires(6000);

                    try {
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));

                        new ClubRubrique(clubName, clubImg, clubDesc, clubId).show();
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        });
        // add club picture
        FloatingActionButton addPicClub = FloatingActionButton.createFAB(FontImage.MATERIAL_CAMERA_ALT);
        addPicClub.setIconDefaultSize(3.0f);
        imgandPubs.add(BorderLayout.CENTER, addPicClub.bindFabToContainer(img, Component.CENTER, Component.TOP));
        addPicClub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                String clubPicName = Util.getUUID() + ".jpg";
                MultipartRequest cr = new MultipartRequest() {

                    @Override
                    protected void handleErrorResponseCode(int code, String message) {
                        if (code == 500) {
                            //Do what you want here
                        }
                    }
                };
                String url = Static.BASE_URL + "/uploadClubPic/" + clubId;

                cr.setUrl(url);
                cr.setPost(true);
                String mime = "image/jpeg";
                System.out.println(cr.getUrl());

                try {
                    cr.addData("file", filePath, mime);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                cr.setFilename("file", clubPicName);//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                //  
                /* try {
                    Image picClub = createImage(filePath).fill(300, 300);
                    Image roundMaskClub = Image.createImage(picClub.getWidth(), picClub.getHeight(), 0xff000000);
                    Graphics graphics = roundMaskClub.getGraphics();
                    graphics.setColor(0xffffff);
                    graphics.fillArc(0, 0, picClub.getWidth(), picClub.getWidth(), 0, 360);
                    Object maskClub = roundMaskClub.createMask();
                    picClub = picClub.applyMask(maskClub);
                    img.setImage(picClub);*/
                try {
                    Dialog.show("Success", "Image uploaded", "OK", null);
                    setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));
                    new ClubRubrique(clubName, ClubService.getInstance().getClubPic(clubId), clubDesc, clubId).show();
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
                //} catch (IOException ex) {}
            }
        });
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
                Button btn_update_pub = new Button("Valider");
                btn_update_pub.setUIID("addImgBtn");
                btn_update_pub.setHidden(true);
                cardBody.add(btn_update_pub);

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

                /**
                 * delete & add icons
                 */
                Font del = FontImage.getMaterialDesignFont();
                del = del.derive(80, Font.STYLE_PLAIN);
                Button btn_del = new Button();
                btn_del.setUIID("RedLabelRight");
                btn_del.setIcon(FontImage.create("\ue92e", btn_del.getUnselectedStyle(), del));
                Button btn_edit = new Button();
                btn_edit.setUIID("GreenLabel");
                btn_edit.setIcon(FontImage.create("\ue3c9", btn_edit.getUnselectedStyle(), del));
                Container con = new Container();
                con.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                con.add(btn_edit).add(btn_del);
                cardTitle.add(BorderLayout.WEST, iv_club);
                cardTitle.add(BorderLayout.CENTER, l_name);
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

                /**
                 * edit pub
                 */
                btn_edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (btn_update_pub.isHidden()) {
                            btn_update_pub.setHidden(false);
                            pubdesc.setEditable(true);
                            pubdesc.setEnabled(true);
                            cardBody.animateLayout(100);
                        } else if (!btn_update_pub.isHidden() && pubdesc.getText().equals(p.getPubDesc())) {

                            btn_update_pub.setHidden(true);
                            pubdesc.setEditable(false);
                            pubdesc.setEnabled(false);
                            cardBody.animateLayout(100);
                        }
                    }
                });

                btn_update_pub.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        pubsz.animateLayout(100);

                        if (pubdesc.getText().length() == 0) {
                            // Dialog.show("Alert", "La publication ne peut pas étre vide", new Command("ok"));
                            Dialog d = new Dialog("Alert");

                            TextArea popupBody = new TextArea("La publication ne peut pas étre vide", 3, 10);
                            Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                                    derive(40, Font.STYLE_PLAIN);
                            popupBody.setUIID("PopupBody");
                            popupBody.setEditable(false);
                            popupBody.getAllStyles().setFont(poppinsAlert);

                            d.setLayout(new BorderLayout());
                            d.add(BorderLayout.CENTER, popupBody);
                            d.showPopupDialog(pubdesc);
                        } else {

                            pubdesc.setEditable(false);
                            pubdesc.setEnabled(false);
                            btn_update_pub.setHidden(true);

                            ToastBar.Status statuss = ToastBar.getInstance().createStatus();
                            statuss.setMessage("Modification de la publication...");
                            statuss.setShowProgressIndicator(true);
                            statuss.show();
                            statuss.setExpires(2000);
                            ClubPubService.getInstance().editPubDesc(p.getId(), pubdesc.getText());

                            ToastBar.Status status = ToastBar.getInstance().createStatus();
                            status.setMessage("Publication modifiée, attendez l'approbation de l'administrateur");
                            Font materialFont = FontImage.getMaterialDesignFont();
                            materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
                            Image icOKay = FontImage.create("\ue876", btn_addPub.getUnselectedStyle(), materialFont);
                            status.setIcon(icOKay);
                            status.showDelayed(5000);
                            status.setExpires(6000);
                            try {
                                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("0")));

                                new ClubRubrique(clubName, clubImg, clubDesc, clubId).show();
                            } catch (IOException ex) {
                                System.out.println(ex.toString());
                            }

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
    /* LocalNotification ln = new LocalNotification();
            ln.setId("LnMessage");
            ln.setAlertTitle("Welcome");
            ln.setAlertBody("Thanks for arriving!");
            Display.getInstance().scheduleLocalNotification(ln, System.currentTimeMillis() + 10, LocalNotification.REPEAT_NONE);*/
}
