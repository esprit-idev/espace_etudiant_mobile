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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.util.ArrayList;

/**
 *
 * @author MeriamBI
 */
public class MatiereList extends Form {

    public MatiereList() {
        this(Resources.getGlobalResources());
    }

    public MatiereList(Resources resourceObjectInstance) {
        //SKELETON
        setLayout(BoxLayout.y());
        setTitle("Centre de partage");
        Form previous = Display.getInstance().getCurrent();
        Toolbar tb = getToolbar();
        tb.setBackCommand("", e -> previous.showBack());
        //floating button add
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            new MatiereAdd().show();
        });
        //init arralyslits
        ArrayList<Matiere> matieres;
        matieres = ServiceMatiere.getInstance().getAllMatieres();
        ArrayList<Niveau> niveaux;
        niveaux = ServiceNiveau.getInstance().getAllNiveaux();
        //BODY
        initGuiBuilderComponents(resourceObjectInstance, matieres, niveaux, previous);
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance, ArrayList<Matiere> matieres, ArrayList<Niveau> niveaux, Form previous) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        
        if (matieres.isEmpty()) {
            setLayout(new FlowLayout(CENTER, CENTER));
            //if no matiere found
            Label lempty = new Label("Aucune matière n'a été ajoutée");
            lempty.setUIID("CenterLabel");
            Style s_lempty = lempty.getUnselectedStyle();
            s_lempty.setFont(poppinsRegular55);
            add(lempty);
        } else {
            //filtre niveau
            ComboBox cbNiveau = new ComboBox();
            for (Niveau n : niveaux) {
                cbNiveau.addItem(n.getId());
            }
            Button filter_btn = new Button("Filtrer");
            filter_btn.setUIID("BlackRoundFilledBtn");
            Style s_filter_btn = filter_btn.getUnselectedStyle();
            s_filter_btn.setFont(poppinsRegular55);
            addAll(cbNiveau, filter_btn);
            //filter action
            filter_btn.addActionListener(
                    new ActionListener<ActionEvent>() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new MatieresFiltered(resourceObjectInstance, cbNiveau.getSelectedItem().toString()).show();
                }
            });
            for (Matiere m : matieres) {
                //init vars
                String nomMatiere = m.getId();
                String niveauMatiere = m.getNiveau();
                //list of matieres set
                Container gui_Container_1 = new Container(new BorderLayout());
                Container gui_Container_2 = new Container(new FlowLayout());
                Container gui_Container_4 = new Container(new FlowLayout());
                Label gui_Label_4 = new Label();
                Container gui_Container_3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label gui_Label_3 = new Label();
                Label gui_Label_2 = new Label();
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
                gui_Container_3.addComponent(gui_Label_2);
                gui_Label_3.setText(nomMatiere);
                Style s_gui_Label_3 = gui_Label_3.getUnselectedStyle();
                s_gui_Label_3.setFont(poppinsRegular55);
                gui_Label_2.setText("Niveau concerné: " + niveauMatiere);
                gui_Label_2.setUIID("GreenLabel");
                Style s_gui_Label_2 = gui_Label_2.getUnselectedStyle();
                s_gui_Label_2.setFont(poppinsRegular40);

                //sheet
                Button displaySheet_btn = new Button();
                displaySheet_btn.addActionListener(e -> {
                    MatiereSheet sheet = new MatiereSheet(null, m, previous);
                    sheet.show();
                    sheet.addBackListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            
                        }
                    });
                });
                gui_Container_1.setLeadComponent(displaySheet_btn);
            }
        }
    }
}
