/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.util.ArrayList;

/**
 *
 * @author MeriamBI
 */
public class MatiereAdd extends Form {

    public MatiereAdd() {
        //SKELETON
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Ajouter une matière");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> {
            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
            new MatiereList().show();
        });
        Container cnt = new Container(BoxLayout.y());

        //BODY
        Label lNomMatiere = new Label("Nom de la matière");
        lNomMatiere.setUIID("CustomLabel");
        Style s_lNomMatiere = lNomMatiere.getUnselectedStyle();
        s_lNomMatiere.setFont(poppinsRegular40);
        TextField tfNomMatiere = new TextField("", "Veuillez saisir le nom");
        tfNomMatiere.getHintLabel().setUIID("CustomHint");
        Style s_tfNomMatiereHint = tfNomMatiere.getHintLabel().getUnselectedStyle();
        s_tfNomMatiereHint.setFont(poppinsRegular35);

        //combobox setup
        ArrayList<Niveau> niveaux;
        niveaux = ServiceNiveau.getInstance().getAllNiveaux();
        Label lNiveau = new Label("Niveau concerné");
        lNiveau.setUIID("CustomLabel");
        Style s_lNiveau = lNiveau.getUnselectedStyle();
        s_lNiveau.setFont(poppinsRegular40);
        ComboBox cbNiveau = new ComboBox();
        for (Niveau n : niveaux) {
            cbNiveau.addItem(n.getId());
        }
        //add btn
        Button add_btn = new Button("Ajouter");
        add_btn.setUIID("BlackRoundFilledBtn");
        Style s_add_btn = add_btn.getUnselectedStyle();
        s_add_btn.setFont(poppinsRegular55);
        cnt.addAll(lNomMatiere, tfNomMatiere, lNiveau, cbNiveau, add_btn);
        //action add btn
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ArrayList<Matiere> matieres;
                matieres = ServiceMatiere.getInstance().getAllMatieres();
                boolean exist = false;
                for (Matiere m : matieres) {
                    if (m.getId().equals(tfNomMatiere.getText())) {
                        exist = true;
                    }
                }
                //ajout data
                if (tfNomMatiere.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else if (exist) {
                    //if matiere exists in db
                    ToastBar.showMessage("Cette matière existe déjà", FontImage.MATERIAL_WARNING);
                } else {
                    //create new doc
                    if (ServiceMatiere.getInstance().addMatiere(tfNomMatiere.getText(), cbNiveau.getSelectedItem().toString())) {
                        //success toast
                        ToastBar.showMessage("Matière ajoutée", FontImage.MATERIAL_CHECK_CIRCLE);
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
                        new MatiereList().show();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de l'ajout de la matière", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });
        addAll(cnt);
    }
}
