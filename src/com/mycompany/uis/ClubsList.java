package com.mycompany.uis;

import com.codename1.components.Accordion;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;

import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Club;
import com.mycompany.services.ClubService;
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.io.Util;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextArea;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;

import com.codename1.util.StringUtil;
import com.mycompany.entities.ClubCategory;
import com.mycompany.services.ClubCategoryService;
import com.mycompany.utils.Static;
import org.jsoup.Jsoup;

public class ClubsList extends Form {

    public ClubsList() {
        this(Resources.getGlobalResources());
    }

    public ClubsList(Resources resourceObjectInstance) {
        int admin = 1; //change
        String CurrentUserClubID = "6"; //change

        ArrayList<Club> clubs;
        ArrayList<ClubCategory> categories;
        setScrollableY(true);
        Toolbar tb = getToolbar();
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Club");
        tb.setBackCommand("", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
                new TabAff().show();
            }
        });
        if (admin == 1) {//to_change
            tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_CATEGORY, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new ClubCategoriesList(resourceObjectInstance).show();
                }
            });
            //floating button add
            FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
            RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
            rb.uiid(true);
            fab.bindFabToContainer(getContentPane());
            fab.addActionListener(e -> {
                new ClubAdd().show();
            });
        }

        clubs = ClubService.getInstance().getAllClubs();

        categories = ClubCategoryService.getInstance().getAllClubCategories();

        ComboBox<String> filter = new ComboBox();
        filter.addItem("--Categories--");
        for (ClubCategory cat : categories) {
            filter.addItem(cat.getCategorie().toUpperCase());
        }
        add(filter);
        //  Button test = new Button("test");
        //  add(test);
        ArrayList<Club> waz = new ArrayList<>();

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (filter.getSelectedItem().equals("--Categories--")) {
                } else {
                    for (int i = 0; i < clubs.size(); i++) {

                        if (clubs.get(i).getClubCategorie().toUpperCase().contains(filter.getSelectedItem())) {
                            waz.add(clubs.get(i));
                        }

                    }
                    setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, true, Integer.parseInt("0")));

                    new ClubsListFiltred(waz, clubs, resourceObjectInstance).show();
                }

            }
        });

        if (!clubs.isEmpty()) {
            for (Club c : clubs) {

                /*SpanLabel s = new SpanLabel();
            s.setText(c.getClubName()
                    + " "
                    + c.getClubCategorie().split("=")[1].replaceAll("}", "")
                    + " "
                    + c.getClubDesc()
                    + " "
                    + c.getClubPic()
                    + " "
                    + c.getClubRespo().split("=")[1].replaceAll("}", "")
            );*/
                String clubID = c.getClubId();
                Label clubName = new Label(c.getClubName().toUpperCase());
                Font poppinsAlert = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                        derive(50, Font.STYLE_BOLD);
                clubName.getAllStyles().setFgColor(0xffffff);
                clubName.getAllStyles().setFont(poppinsAlert);
                clubName.setAlignment(CENTER);

                //rounded image
                ImageViewer clubImg = new ImageViewer();

                try {
                    Image picClub = Image.createImage(Static.ClubPic + c.getClubPic()).fill(500, 500);
                    Image roundMaskClub = Image.createImage(picClub.getWidth(), picClub.getHeight(), 0xff000000);
                    Graphics graphics = roundMaskClub.getGraphics();
                    graphics.setColor(0xffffff);
                    graphics.fillArc(0, 0, picClub.getWidth(), picClub.getWidth(), 0, 360);
                    Object maskClub = roundMaskClub.createMask();
                    picClub = picClub.applyMask(maskClub);
                    clubImg = new ImageViewer(picClub);

                } catch (Exception e) {
                    System.out.println(e.toString()
                    );
                }
                //categorie
                Label clubDescTitles = new Label("Categorie : " + c.getClubCategorie());
                Font poppinsCat = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                        derive(50, Font.STYLE_BOLD);
                clubDescTitles.getAllStyles().setFont(poppinsCat);

                //club desc
                Font poppinsDesc = Font.createTrueTypeFont("dss", "Poppins-Regular.ttf").
                        derive(40, Font.STYLE_PLAIN);
                TextArea clubDesc = new TextArea(
                        Jsoup.parse(c.getClubDesc()).text()
                );
                clubDesc.setEditable(false);
                clubDesc.getAllStyles().setAlignment(CENTER);
                clubDesc.setEnabled(false);
                clubDesc.getAllStyles().setFont(poppinsDesc);
                Label clubRespo = new Label("Contact : "
                        + c.getClubRespo());
                clubRespo.setAlignment(CENTER);
                clubRespo.getAllStyles().setFont(poppinsCat);

                clubDescTitles.setAlignment(CENTER);
                Button btnConsult = new Button("Consulter");
                btnConsult.setUIID("addPubBtn");
                btnConsult.addActionListener((ActionListener) new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        try {
                            if (admin == 1) {//change
                                new ClubRubriqueEtudiant(clubName.getText(), c.getClubPic(), clubDesc.getText(), clubID, c.getClubCategorie(), c.getClubRespo()).show();
                            } else {
                                if (CurrentUserClubID.equals(c.getClubId())) {
                                    new ClubRubrique(clubName.getText(), c.getClubPic(), clubDesc.getText(), clubID).show();
                                } else {
                                    new ClubRubriqueEtudiant(clubName.getText(), c.getClubPic(), clubDesc.getText(), clubID, c.getClubCategorie(), c.getClubRespo()).show();

                                }
                            }
                        } catch (IOException ex) {
                            ToastBar.Status status = ToastBar.getInstance().createStatus();
                            status.setMessage(ex.toString());
                            status.setExpires(3000);  // only show the status for 3 seconds, then have it automatically clear
                            status.show();
                        }
                    }
                });

                Accordion accr = new Accordion();
                accr.setHeaderUIID("accrHead");

                Font edit = FontImage.getMaterialDesignFont();
                edit = edit.derive(60, Font.STYLE_PLAIN);
                Button btn_edit_C_desc = new Button();
                btn_edit_C_desc.setUIID("SmallWhileLabel");
                Image f1 = FontImage.create("\ue5cc", btn_edit_C_desc.getUnselectedStyle(), edit);
                Image f2 = FontImage.create("\ue5cf", btn_edit_C_desc.getUnselectedStyle(), edit);
                accr.setCloseIcon(f1);
                accr.setOpenIcon(f2);

                Container cs = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                cs.add(clubImg);
                cs.add(clubDescTitles);
                cs.add(clubDesc);
                cs.add(clubRespo);
                cs.add(btnConsult);

                /* accr.setOpenIcon(FontImage.MATERIAL_ARROW_DOWNWARD, "accrHead");
                accr.setCloseIcon(FontImage.MATERIAL_ARROW_DOWNWARD, "accrHead");*/
                accr.addContent(
                        clubName,
                        cs
                );

                add(accr);

            }
        } else {
            add(new Label("Pas de club"));
        }

    }

}
