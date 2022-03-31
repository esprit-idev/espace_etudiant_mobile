/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.entities.Document;
import com.mycompany.services.ServiceDocument;

/**
 *
 * @author MeriamBI
 */
public class DocShare extends Form {

    public DocShare(Document doc) {
        //SKELETON
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Partager le document");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> {
            setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
            new DocsList().show();
        });
        Container cnt = new Container(BoxLayout.y());
        //BODY
        Label lEmailDest = new Label("Email de destination");
        lEmailDest.setUIID("CustomLabel");
        Style s_lEmailDest = lEmailDest.getUnselectedStyle();
        s_lEmailDest.setFont(poppinsRegular40);
        TextField tfEmailDest = new TextField("", "Veuillez saisir l'email de destination", 10000, TextArea.EMAILADDR);
        tfEmailDest.getHintLabel().setUIID("CustomHint");
        Style s_tfEmailDestHint = tfEmailDest.getHintLabel().getUnselectedStyle();
        s_tfEmailDestHint.setFont(poppinsRegular35);
        Label lObjet = new Label("Objet de l'email");
        lObjet.setUIID("CustomLabel");
        Style s_lObjet = lObjet.getUnselectedStyle();
        s_lObjet.setFont(poppinsRegular40);
        TextField tfObjet = new TextField("", "Veuillez saisir l'objet");
        tfObjet.getHintLabel().setUIID("CustomHint");
        Style s_tfObjetHint = tfObjet.getHintLabel().getUnselectedStyle();
        s_tfObjetHint.setFont(poppinsRegular35);
        Label lBody = new Label("Contenu de l'email");
        lBody.setUIID("CustomLabel");
        Style s_lBody = lBody.getUnselectedStyle();
        s_lBody.setFont(poppinsRegular40);
        TextField tfBody = new TextField("", "Veuillez saisir le contenu");
        tfBody.getHintLabel().setUIID("CustomHint");
        Style s_tfBodyHint = tfBody.getHintLabel().getUnselectedStyle();
        s_tfBodyHint.setFont(poppinsRegular35);
        //url validator
        Validator validatorEmail = new Validator();
        validatorEmail.addConstraint(tfEmailDest, RegexConstraint.validEmail());

        //send btn
        Button send_btn = new Button("Envoyer");
        send_btn.setUIID("BlackRoundFilledBtn");
        Style s_send_btn = send_btn.getUnselectedStyle();
        s_send_btn.setFont(poppinsRegular55);
        cnt.addAll(lEmailDest, tfEmailDest, lObjet, tfObjet, lBody, tfBody, send_btn);
        //action add btn
        send_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                //ajout data
                if (tfEmailDest.getText().isEmpty() || tfObjet.getText().isEmpty() || tfBody.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else if (!validatorEmail.isValid()) {
                    //toast if invalid url
                    ToastBar.showErrorMessage("Email de destination saisi invalide", FontImage.MATERIAL_ERROR);
                } else {

                    if (ServiceDocument.getInstance().shareDoc(doc, tfEmailDest.getText(), tfBody.getText(), tfObjet.getText())) {
                        //success toast
                        ToastBar.showMessage("Email envoyé", FontImage.MATERIAL_CHECK_CIRCLE);
                        previous.showBack();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de l'envoi de l'email", FontImage.MATERIAL_ERROR);
                    }

                }
            }
        });
        addAll(cnt);
    }

}
