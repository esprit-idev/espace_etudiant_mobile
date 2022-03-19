package com.mycompany.uis;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());

        //BODY
        TextField tfNomDoc = new TextField("", "Nom");
        TextField tfUrl = new TextField("", "URL", 10000, TextArea.URL);
        //url validator
        Validator validatorUrl = new Validator();
        validatorUrl.addConstraint(tfUrl, RegexConstraint.validURL());
        //combobox setup
        ArrayList<Niveau> niveaux;
        niveaux = ServiceNiveau.getInstance().getAllNiveaux();
        ArrayList<Matiere> matieres;
        matieres = ServiceMatiere.getInstance().getAllMatieres();
        ComboBox cbNiveau = new ComboBox();
        for (Niveau n : niveaux) {
            cbNiveau.addItem(n.getId());
        }
        ComboBox cbMatiere = new ComboBox();
        for (Matiere m : matieres) {
            cbMatiere.addItem(m.getId());
        }
        //add btn
        Button ajouter = new Button("Ajouter");
        cnt.addAll(tfNomDoc, tfUrl, cbNiveau, cbMatiere, ajouter);
        //action add btn
        ajouter.addActionListener(new ActionListener() {
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
