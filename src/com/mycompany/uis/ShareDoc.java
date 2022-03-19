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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.entities.Document;
import com.mycompany.services.ServiceDocument;

/**
 *
 * @author MeriamBI
 */
public class ShareDoc extends Form{
    public ShareDoc(Document doc) {
        //SKELETON
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Partager le document");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());
        //BODY
        TextField tfEmailDest = new TextField("", "Email de destination",10000, TextArea.EMAILADDR);
        TextField tfObjet = new TextField("", "Objet");
        TextField tfBody = new TextField("", "Contenu");
        //url validator
        Validator validatorEmail = new Validator();
        validatorEmail.addConstraint(tfEmailDest, RegexConstraint.validEmail());
        
        //send btn
        Button redirect_btn = new Button("Redirection vers la messagerie");
        cnt.addAll(tfEmailDest, tfObjet, tfBody,redirect_btn);
        //action add btn
        redirect_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //init vars
                String nomDoc=doc.getNom();
                String urlDoc=doc.getUrl();
                String base64Doc=doc.getBase64();
                //ajout data
                if (tfEmailDest.getText().isEmpty() || tfObjet.getText().isEmpty() || tfBody.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else if (!validatorEmail.isValid()) {
                    //toast if invalid url
                    ToastBar.showErrorMessage("Email de destination saisi invalide", FontImage.MATERIAL_ERROR);
                } else {
                    
                    if (ServiceDocument.getInstance().shareDoc(doc,tfEmailDest.getText(),tfBody.getText(),tfObjet.getText())) {
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
