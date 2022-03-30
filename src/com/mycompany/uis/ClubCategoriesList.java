/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import com.mycompany.entities.ClubCategory;
import com.mycompany.services.ClubCategoryService;

/**
 *
 * @author anash
 */
public class ClubCategoriesList extends Form {

    public ClubCategoriesList() {
        this(Resources.getGlobalResources());
    }

    public ClubCategoriesList(Resources resourceObjectInstance) {
        //SKELETON
        setLayout(BoxLayout.y());
        setTitle("Catégories clubs");
        Form previous = Display.getInstance().getCurrent();
        Toolbar tb = getToolbar();
        tb.setBackCommand("", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));

                new ClubsList().show();
            }
        });
        //floating button add
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
             new ClubCategoryAdd().show();
        });
        //init arralyslits
        ArrayList<ClubCategory> categories;
        categories = ClubCategoryService.getInstance().getAllClubCategories();

        //BODY
        initGuiBuilderComponents(resourceObjectInstance, categories, previous);
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance, ArrayList<ClubCategory> categories, Form previous) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular30 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(30, Font.STYLE_PLAIN);

        if (categories.isEmpty()) {
            setLayout(new FlowLayout(CENTER, CENTER));
            //if no matiere found
            Label lempty = new Label("Aucune catégorie n'a été ajoutée");
            lempty.setUIID("CenterLabel");
            Style s_lempty = lempty.getUnselectedStyle();
            s_lempty.setFont(poppinsRegular55);
            add(lempty);
        } else {
            for (ClubCategory m : categories) {
                //init vars
                String nomCateg = m.getCategorie();
                //list of matieres set
                Container gui_Container_1 = new Container(new BorderLayout());
                Container gui_Container_2 = new Container(new FlowLayout());
                Container gui_Container_4 = new Container(new FlowLayout());
                Label gui_Label_4 = new Label();
                Container gui_Container_3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label gui_Label_3 = new Label();
                add(gui_Container_1);
                gui_Container_1.addComponent(BorderLayout.EAST, gui_Container_2);
                gui_Container_1.setUIID("Margin2");
                gui_Container_1.addComponent(BorderLayout.WEST, gui_Container_4);
                ((FlowLayout) gui_Container_4.getLayout()).setAlign(Component.CENTER);
                gui_Container_4.addComponent(gui_Label_4);
                gui_Label_4.setUIID("Padding2");
                gui_Label_4.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
                gui_Container_1.addComponent(BorderLayout.CENTER, gui_Container_3);
                gui_Container_3.addComponent(gui_Label_3);
                gui_Label_3.setText(nomCateg);
                Style s_gui_Label_3 = gui_Label_3.getUnselectedStyle();
                s_gui_Label_3.setFont(poppinsRegular55);

                //sheet
                Button displaySheet_btn = new Button();
                displaySheet_btn.addActionListener(e -> {
                    ClubCategSheet sheet = new ClubCategSheet(null, m, previous);
                    sheet.show();
                });
                gui_Container_1.setLeadComponent(displaySheet_btn);
            }
        }
    }
}
