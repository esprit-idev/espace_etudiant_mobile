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
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceCategoryEmploi;

/**
 *
 * @author eslem
 */
public class OffreCategoryUpdate extends Form{
    public OffreCategoryUpdate(int id, String catName) {
          Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Modifier categorie");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());

        //BODY
        Label lNomCat = new Label("Categorie");
        lNomCat.setUIID("CustomLabel");
        Style s_lNomCat = lNomCat.getUnselectedStyle();
        s_lNomCat.setFont(poppinsRegular40);
        TextField tfNomCat = new TextField(catName, "Veuillez saisir le nom");
        tfNomCat.getHintLabel().setUIID("CustomHint");
        Style s_tfNomCatHint = tfNomCat.getHintLabel().getUnselectedStyle();
        s_tfNomCatHint.setFont(poppinsRegular35);

        //update btn
        Button update_btn = new Button("Modifier");
        update_btn.setUIID("BlackRoundFilledBtn");
        Style s_update_btn = update_btn.getUnselectedStyle();
        s_update_btn.setFont(poppinsRegular55);
        cnt.addAll(lNomCat, tfNomCat, update_btn);
        //action add btn
        update_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //update data
                if (tfNomCat.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir le champs", FontImage.MATERIAL_ERROR);
                } else {
                    //update cat
                    if (ServiceCategoryEmploi.getInstance().updateCategory(id, catName)) {
                        //success toast
                        ToastBar.showMessage("Categorie modifiée", FontImage.MATERIAL_CHECK_CIRCLE);
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));

                        new ClubCategoriesList(Resources.getGlobalResources()).show();

                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de la modification", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });
        addAll(cnt);
    }
}
