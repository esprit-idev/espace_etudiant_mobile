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
import com.codename1.ui.TextArea;
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
import com.mycompany.entities.Document;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceDocument;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.util.ArrayList;

public class DocsList extends Form {

    public DocsList() {
        this(Resources.getGlobalResources());
    }

    public DocsList(Resources resourceObjectInstance) {
        int admin;
        if (SessionManager.getRoles().equals("ROLE_ADMIN"))
            admin = 1;
        else
            admin = 0;

        //SKELETON
        setLayout(BoxLayout.y());
        setTitle("Centre de partage");
        Form previous = Display.getInstance().getCurrent();
        Toolbar tb = getToolbar();
        tb.setBackCommand("", e -> {
            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
            new TabAff(resourceObjectInstance).show();
        });
        if (admin != 1) {
            tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_BOOKMARKS, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new DocsFavorite(resourceObjectInstance).show();
                }
            });
        } else {
            tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_REPORT, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new DocsReported(resourceObjectInstance).show();
                }
            });
        }
        if (admin != 1) {
            //floating button add
            FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
            RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
            rb.uiid(true);
            fab.bindFabToContainer(getContentPane());
            fab.addActionListener(e -> {
                new DocAdd().show();
            });
        }
        //init arralyslits
        ArrayList<Document> docs;
        docs = ServiceDocument.getInstance().getAllDocs();
        ArrayList<Niveau> niveaux;
        niveaux = ServiceNiveau.getInstance().getAllNiveaux();
        ArrayList<Matiere> matieres;
        matieres = ServiceMatiere.getInstance().getAllMatieres();
        //BODY

        initGuiBuilderComponents(resourceObjectInstance, docs, niveaux, matieres, previous, admin);
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance, ArrayList<Document> docs, ArrayList<Niveau> niveaux, ArrayList<Matiere> matieres, Form previous, int admin) {
        String currentUser=SessionManager.getUserName()+" "+SessionManager.getPrenom();//to_check
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular30 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(30, Font.STYLE_PLAIN);

        if (docs.isEmpty()) {
            setLayout(new FlowLayout(CENTER, CENTER));
            //if no document found
            Label lempty = new Label("Aucun document n'a été trouvé");
            lempty.setUIID("CenterLabel");
            Style s_lempty = lempty.getUnselectedStyle();
            s_lempty.setFont(poppinsRegular55);
            add(lempty);
        } else {
            //FILTRES
            //filtre niveau
            ComboBox cbNiveau = new ComboBox();
            for (Niveau n : niveaux) {
                cbNiveau.addItem(n.getId());
            }
            //filtre matiere
            ComboBox cbMatiere = new ComboBox();
            for (Matiere m : matieres) {
                cbMatiere.addItem(m.getId());
            }
            Button filter_btn = new Button("Filtrer");
            filter_btn.setUIID("BlackRoundFilledBtn");
            Style s_filter_btn = filter_btn.getUnselectedStyle();
            s_filter_btn.setFont(poppinsRegular55);
            addAll(cbNiveau, cbMatiere, filter_btn);
            //filter action
            filter_btn.addActionListener(
                    new ActionListener<ActionEvent>() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new DocsFiltered(resourceObjectInstance, cbNiveau.getSelectedItem().toString(), cbMatiere.getSelectedItem().toString(), admin).show();
                }
            });
            for (Document d : docs) {
                //init vars
                String nomDoc = d.getNom();
                String niveauDoc = d.getNiveau();
                String matiereDoc = d.getMatiere();
                String dateDoc = d.getDate_insert();
                String propDoc = d.getProp();
                //list of docs set
                Container gui_Container_1 = new Container(new BorderLayout());
                Container gui_Container_2 = new Container(new FlowLayout());
                Label gui_Label_1 = new Label();
                Container gui_Container_4 = new Container(new FlowLayout());
                Label gui_Label_4 = new Label();
                Container gui_Container_3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label gui_Label_3 = new Label();
                Label gui_Label_2 = new Label();
                TextArea gui_Text_Area_1 = new TextArea();
                add(gui_Container_1);
                gui_Container_1.addComponent(BorderLayout.EAST, gui_Container_2);
                gui_Container_1.setUIID("Margin2");
                gui_Container_2.addComponent(gui_Label_1);
                gui_Label_1.setText("inséré le " + dateDoc);
                gui_Label_1.setUIID("SmallFontLabel");
                Style s_gui_Label_1 = gui_Label_1.getUnselectedStyle();
                s_gui_Label_1.setFont(poppinsRegular30);
                gui_Container_1.addComponent(BorderLayout.WEST, gui_Container_4);
                ((FlowLayout) gui_Container_4.getLayout()).setAlign(Component.CENTER);
                gui_Container_4.addComponent(gui_Label_4);
                gui_Label_4.setUIID("Padding2");
                if (propDoc.equals(currentUser)) {
                    gui_Label_4.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
                } else {
                    gui_Label_4.setIcon(resourceObjectInstance.getImage("label_round.png"));
                }
                gui_Container_1.addComponent(BorderLayout.CENTER, gui_Container_3);
                gui_Container_3.addComponent(gui_Label_3);
                gui_Container_3.addComponent(gui_Label_2);
                gui_Container_3.addComponent(gui_Text_Area_1);
                gui_Label_3.setText(nomDoc);
                Style s_gui_Label_3 = gui_Label_3.getUnselectedStyle();
                s_gui_Label_3.setFont(poppinsRegular55);
                gui_Label_2.setText(niveauDoc + " | " + matiereDoc);
                gui_Label_2.setUIID("GreenLabel");
                Style s_gui_Label_2 = gui_Label_2.getUnselectedStyle();
                s_gui_Label_2.setFont(poppinsRegular40);
                gui_Text_Area_1.setText("Propriétaire: " + propDoc);
                gui_Text_Area_1.setUIID("SmallFontLabel");
                Style s_gui_Text_Area_1 = gui_Text_Area_1.getUnselectedStyle();
                s_gui_Text_Area_1.setFont(poppinsRegular30);
                //sheet
                Button displaySheet_btn = new Button();
                displaySheet_btn.addActionListener(e -> {
                    DocSheet sheet = new DocSheet(null, d, previous, admin, false,"doclist");
                    sheet.show();
                });
                gui_Container_1.setLeadComponent(displaySheet_btn);
            }
        }
    }
}
