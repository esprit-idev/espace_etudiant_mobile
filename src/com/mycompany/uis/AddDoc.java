package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

public class AddDoc extends Form{
	public AddDoc() {
		Toolbar tb=getToolbar();
		setTitle("Centre de partage");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cnt = new Container(BoxLayout.y());
		TextField tfNomDoc=new TextField("","Nom du document");
        TextField tfDoc=new TextField("","Document");
        ComboBox<String> cbNiveau=new ComboBox<String>();
        //get niveau from db and add to cb
        cbNiveau.addItem("3A");          
        cbNiveau.addItem("3B");
        cbNiveau.addItem("4SIM");
        ComboBox<String> cbMatiere=new ComboBox<String>();
        //get niveau from db and add to cb
        cbMatiere.addItem("Analyse");          
        cbMatiere.addItem("Proba");
        cbMatiere.addItem("Calcul scientifique");
        Button ajouter=new Button("Ajouter");
        cnt.addAll(tfNomDoc,tfDoc,cbNiveau,cbMatiere,ajouter);
        ajouter.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		//ajout a la bd              		
        	}
		});    
        addAll(cnt);
	}
}
