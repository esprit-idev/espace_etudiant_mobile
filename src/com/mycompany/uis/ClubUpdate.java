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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.Club;
import com.mycompany.entities.ClubCategory;
import com.mycompany.entities.User;
import com.mycompany.services.ClubCategoryService;
import com.mycompany.services.ClubService;
import java.util.ArrayList;

/**
 *
 * @author anash
 */
public class ClubUpdate extends Form {

    public ClubUpdate(String idClub, String nomClub, String clubDesc, String clubCat, String clubRespo) {
        //SKELETON
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Modifier club");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> new ClasseList().show());
        Container cnt = new Container(BoxLayout.y());

        //BODY
        Label lNomClub = new Label("Nom du club");
        lNomClub.setUIID("CustomLabel");
        Style s_lNomClub = lNomClub.getUnselectedStyle();
        s_lNomClub.setFont(poppinsRegular40);
        TextField tfNomClub = new TextField(nomClub, "Veuillez saisir le nom");
        tfNomClub.getHintLabel().setUIID("CustomHint");
        Style s_tfNomClubHint = tfNomClub.getHintLabel().getUnselectedStyle();
        s_tfNomClubHint.setFont(poppinsRegular35);
        Label lDesc = new Label("Description");
        lDesc.setUIID("CustomLabel");
        Style s_lDesc = lDesc.getUnselectedStyle();
        s_lDesc.setFont(poppinsRegular40);
        TextArea tfDesc = new TextArea(clubDesc);

        //combobox setup
        ArrayList<ClubCategory> categories;
        categories = ClubCategoryService.getInstance().getAllClubCategories();

            ArrayList<User> users;
            users = ClubService.getInstance().updateClubUsers(idClub);//to change
        Label lcat = new Label("Categorie");
        lcat.setUIID("CustomLabel");
        Style s_lCat = lcat.getUnselectedStyle();
        s_lCat.setFont(poppinsRegular40);

        ComboBox cbCat = new ComboBox();
        for (ClubCategory n : categories) {
            cbCat.addItem(n.getCategorie());
        }
        cbCat.setSelectedItem(clubCat);

        Label lRespo = new Label("Responsable");
        lRespo.setUIID("CustomLabel");
        Style s_lRespo = lRespo.getUnselectedStyle();
        s_lRespo.setFont(poppinsRegular40);
        
        ComboBox cbRespo = new ComboBox();
        for (User m : users) {//tocchange
            cbRespo.addItem(m.getEmail());
        }

      /*  ComboBox cbRespo = new ComboBox();

        cbRespo.addItem("hend@hend.com");
        cbRespo.addItem("imen@imen.test");
        cbRespo.addItem("Sana@rtar.com");
        cbRespo.addItem("Bassem@adaz.com");*/
        cbCat.setSelectedItem(clubRespo);

        //update btn
        Button update_btn = new Button("Modifier");
        update_btn.setUIID("BlackRoundFilledBtn");
        Style s_update_btn = update_btn.getUnselectedStyle();
        s_update_btn.setFont(poppinsRegular55);
        cnt.addAll(lNomClub, tfNomClub, lDesc, tfDesc, lcat, cbCat, lRespo, cbRespo, update_btn);
        //action add btn
        update_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //update data
                if (tfNomClub.getText().isEmpty() || tfDesc.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else {
                    //create new doc
                    if (ClubService.getInstance().updateClub(idClub,tfNomClub.getText(),tfDesc.getText(),cbRespo.getSelectedItem().toString(),cbCat.getSelectedItem().toString())) {
                        //success toast
                        ToastBar.showMessage("Club modifié", FontImage.MATERIAL_CHECK_CIRCLE);
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));

                        new ClubsList().show();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de la modification ", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });
        addAll(cnt);
    }
}
