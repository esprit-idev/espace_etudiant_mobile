/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
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
import com.mycompany.entities.CategoryEmploi;
import com.mycompany.services.ServiceCategoryEmploi;

/**
 *
 * @author eslem
 */
public class OffreCategoryAdd extends Form{
        public OffreCategoryAdd(){
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Ajouter une Categorie d' Emploi");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());
        
        // label name cat
        Label categoryName = new Label("Nom de Categorie");
        categoryName.setUIID("CustomLabel");
        Style s_lNomCat = categoryName.getUnselectedStyle();
        s_lNomCat.setFont(poppinsRegular40);
        TextField tfcategoryName = new TextField("", "le nom du categorie");
        tfcategoryName.getHintLabel().setUIID("CustomHint");
        Style s_tfNomCatHint = tfcategoryName.getHintLabel().getUnselectedStyle();
        s_tfNomCatHint.setFont(poppinsRegular35);
        
        cnt.add(categoryName);
        cnt.add(tfcategoryName);
        
        //button add 
        Button add_btn = new Button("Ajouter");
        add_btn.setUIID("BlackRoundFilledBtn");
        Style s_add_btn = add_btn.getUnselectedStyle();
        s_add_btn.setFont(poppinsRegular55);
        cnt.add(add_btn);
        //add event
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //ajout data
                if (tfcategoryName.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir le champs", FontImage.MATERIAL_ERROR);
                } else {
                    //create new doc
                    CategoryEmploi cat = new CategoryEmploi(tfcategoryName.getText());
                    if (ServiceCategoryEmploi.getInstance().addCategory(cat)) {
                        //success toast
                        ToastBar.showMessage("Categorie ajoutée", FontImage.MATERIAL_CHECK_CIRCLE);
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));

                        new OffreCategory().show();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de l'ajout", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });    
        
        addAll(cnt);
    }
}
