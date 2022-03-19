/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Document;
import com.mycompany.entities.DocumentFavoris;
import com.mycompany.services.ServiceDocumentFavoris;
import java.util.ArrayList;


public class FavoriteDocs extends Form{
    public FavoriteDocs(Resources resourceObjectInstance){
        //SKELETON
        setTitle("Mes favoris");
        setLayout(BoxLayout.y());
        
        Toolbar tb=getToolbar();
        Form previous = Display.getInstance().getCurrent();
	tb.setBackCommand("", e -> previous.showBack());
        //floating button add
        FloatingActionButton fab  = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder)fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            new AddDoc().show();
        });
        //init arraylist
        ArrayList<DocumentFavoris> favs;
        favs=ServiceDocumentFavoris.getInstance().getAllPinnedDocs();
        //BODY
        initGuiBuilderComponents(resourceObjectInstance,favs,previous);
    }
    
     private void initGuiBuilderComponents(Resources resourceObjectInstance,ArrayList<DocumentFavoris> favs,Form previous) {
         Font poppinsRegular55 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular30 = Font.createTrueTypeFont("regular","Poppins-Regular.ttf").
                    derive(30, Font.STYLE_PLAIN);
        String currentUser="Anas Houissa"; //to_change
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        for (DocumentFavoris f : favs){
            //init vars
            Document doc=f.getDoc();
            String niveauDoc=doc.getNiveau();
            String matiereDoc=doc.getMatiere();
            String nomDoc=doc.getNom();
            String propDoc=doc.getProp();
            String dateDoc=doc.getDate_insert();
            //list of docs set
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
            gui_Label_1.setText("inséré le "+dateDoc);
            gui_Label_1.setUIID("SmallFontLabel");
            Style s_gui_Label_1 = gui_Label_1.getUnselectedStyle();
            s_gui_Label_1.setFont(poppinsRegular30);
            gui_Container_1.addComponent(BorderLayout.WEST, gui_Container_4);
            ((FlowLayout)gui_Container_4.getLayout()).setAlign(Component.CENTER);
            gui_Container_4.addComponent(gui_Label_4);
            gui_Label_4.setUIID("Padding2");
            if(propDoc.equals(currentUser)){
                gui_Label_4.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
            } else{
                gui_Label_4.setIcon(resourceObjectInstance.getImage("label_round.png"));
            }
            gui_Container_1.addComponent(BorderLayout.CENTER, gui_Container_3);
            gui_Container_3.addComponent(gui_Label_3);
            gui_Container_3.addComponent(gui_Label_2);
            gui_Container_3.addComponent(gui_Text_Area_1);
            gui_Label_3.setText(nomDoc);
            Style s_gui_Label_3 = gui_Label_3.getUnselectedStyle();
            s_gui_Label_3.setFont(poppinsRegular55);
            gui_Label_2.setText(niveauDoc+" | "+matiereDoc);
            gui_Label_2.setUIID("GreenLabel");
            Style s_gui_Label_2 = gui_Label_2.getUnselectedStyle();
            s_gui_Label_2.setFont(poppinsRegular40);
            gui_Text_Area_1.setText("Propriétaire: "+propDoc);
            gui_Text_Area_1.setUIID("SmallFontLabel");
            Style s_gui_Text_Area_1 = gui_Text_Area_1.getUnselectedStyle();
            s_gui_Text_Area_1.setFont(poppinsRegular30);
            //sheet
            Button displaySheet_btn = new Button();
                    displaySheet_btn.addActionListener(e -> {
                        DocSheet sheet = new DocSheet(null,doc,previous);
                        sheet.show();
                    });
            gui_Container_1.setLeadComponent(displaySheet_btn);
        }
    }
    
}
