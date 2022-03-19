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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.entities.Document;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceDocument;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDoc extends Form {

    public AddDoc() {
        //SKELETON
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Ajouter une URL");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(35, Font.STYLE_PLAIN);
        
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());

        //BODY
        Label lNomDoc=new Label("Nom du document");
        lNomDoc.setUIID("CustomLabel");
        Style s_lNomDoc = lNomDoc.getUnselectedStyle();
        s_lNomDoc.setFont(poppinsRegular40);
        TextField tfNomDoc = new TextField("", "Veuillez saisir le nom");
        tfNomDoc.getHintLabel().setUIID("CustomHint");
        Style s_tfNomDocHint =tfNomDoc.getHintLabel().getUnselectedStyle();
        s_tfNomDocHint.setFont(poppinsRegular35);
        Label lUrlDoc=new Label("URL à attacher");
        lUrlDoc.setUIID("CustomLabel");
        Style s_lUrlDoc = lUrlDoc.getUnselectedStyle();
        s_lUrlDoc.setFont(poppinsRegular40);
        TextField tfUrl = new TextField("", "Veuillez saisir l'URL", 10000, TextArea.URL);
        tfUrl.getHintLabel().setUIID("CustomHint");
        Style s_tfUrlHint =tfUrl.getHintLabel().getUnselectedStyle();
        s_tfUrlHint.setFont(poppinsRegular35);
        //url validator
        Validator validatorUrl = new Validator();
        validatorUrl.addConstraint(tfUrl, RegexConstraint.validURL());
        //combobox setup
        ArrayList<Niveau> niveaux;
        niveaux = ServiceNiveau.getInstance().getAllNiveaux();
        ArrayList<Matiere> matieres;
        matieres = ServiceMatiere.getInstance().getAllMatieres();
        Label lNiveau=new Label("Niveau concerné");
        lNiveau.setUIID("CustomLabel");
        Style s_lNiveau = lNiveau.getUnselectedStyle();
        s_lNiveau.setFont(poppinsRegular40);
        ComboBox cbNiveau = new ComboBox();
        for (Niveau n : niveaux) {
            cbNiveau.addItem(n.getId());
        }
        Label lMatiere=new Label("Matière concernée");
        lMatiere.setUIID("CustomLabel");
        Style s_lMatiere = lMatiere.getUnselectedStyle();
        s_lMatiere.setFont(poppinsRegular40);
        ComboBox cbMatiere = new ComboBox();
        for (Matiere m : matieres) {
            cbMatiere.addItem(m.getId());
        }
        //add btn
        Button add_btn = new Button("Ajouter");
        add_btn.setUIID("BlackRoundFilledBtn");
        Style s_add_btn=add_btn.getUnselectedStyle();
        s_add_btn.setFont(poppinsRegular55);
        cnt.addAll(lNomDoc,tfNomDoc,lUrlDoc,tfUrl,lNiveau,cbNiveau,lMatiere,cbMatiere,add_btn);
        //action add btn
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //ajout data
                if (tfNomDoc.getText().isEmpty() || tfUrl.getText().isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else if (!validatorUrl.isValid()) {
                    //toast if invalid url
                    ToastBar.showErrorMessage("URL saisie invalide", FontImage.MATERIAL_ERROR);
                } else {
                    //create new doc
                    String insert_date = new SimpleDateFormat("dd/MM/yy").format(new Date());
                    Document doc = new Document(tfNomDoc.getText(), insert_date, "Anas Houissa"/*to_change*/, cbMatiere.getSelectedItem().toString(), cbNiveau.getSelectedItem().toString(), tfUrl.getText(), null);
                    if (ServiceDocument.getInstance().addUrl(doc)) {
                        //success toast
                        ToastBar.showMessage("URL ajoutée", FontImage.MATERIAL_CHECK_CIRCLE);
                        previous.showBack();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de l'ajout de l'URL", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });
        addAll(cnt);
    }
}
