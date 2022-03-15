/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.mycompany.entities.Document;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceDocument;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.util.ArrayList;

/**
 *
 * @author MeriamBI
 */
public class FilteredDocs extends Form{
    public FilteredDocs(String niveauSelect,String matiereSelect) {
        ArrayList<Document> docs;
        docs=ServiceDocument.getInstance().getAllDocs();
        initGuiBuilderComponents(docs,niveauSelect,matiereSelect);
        Toolbar tb=getToolbar();
	setLayout(BoxLayout.y());
	setTitle(niveauSelect+" | "+matiereSelect);
	Form previous = Display.getInstance().getCurrent();
	tb.setBackCommand("", e -> previous.showBack());
	FloatingActionButton fab  = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder)fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            new AddDoc().show();
        });
    }
    
     private void initGuiBuilderComponents(ArrayList<Document> docs,String niveauSelect,String matiereSelect) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        for (Document d : docs){
            String niveauDoc=d.getNiveau().substring(4,d.getNiveau().length()-1);
            String matiereDoc=d.getMatiere().substring(d.getMatiere().indexOf('=')+1, d.getMatiere().indexOf(','));
            if(niveauDoc.equals(niveauSelect) && matiereDoc.equals(matiereSelect)){
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
                gui_Label_1.setText("inséré le "+d.getDate_insert());
                gui_Label_1.setUIID("SmallFontLabel");
                gui_Container_1.addComponent(BorderLayout.WEST, gui_Container_4);
                ((FlowLayout)gui_Container_4.getLayout()).setAlign(Component.CENTER);
                gui_Container_4.addComponent(gui_Label_4);
                gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3);
                gui_Container_3.addComponent(gui_Label_3);
                gui_Container_3.addComponent(gui_Label_2);
                gui_Container_3.addComponent(gui_Text_Area_1);
                gui_Label_3.setText(d.getNom());
                gui_Label_2.setText(niveauDoc+" | "+matiereDoc);
                gui_Label_2.setUIID("GreenLabel");
                gui_Text_Area_1.setText("Propriétaire: "+d.getProp());
                gui_Text_Area_1.setUIID("SmallFontLabel");
                Button displayDoc_btn = new Button();
                        displayDoc_btn.addActionListener(e -> {
                            new DisplayDocument(d).show();
                        });
                        //Cx.setLeadComponent(displayDoc_btn);
                gui_Container_1.setLeadComponent(displayDoc_btn);
            }  
        }

        
        
    }
}
