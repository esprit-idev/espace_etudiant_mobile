package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Document;
import com.mycompany.entities.Matiere;
import com.mycompany.entities.Niveau;
import com.mycompany.services.ServiceDocument;
import com.mycompany.services.ServiceMatiere;
import com.mycompany.services.ServiceNiveau;
import java.io.IOException;
import java.util.ArrayList;

public class CentrePartage extends Form{
    public CentrePartage() {
        this(Resources.getGlobalResources());
    }
    public CentrePartage(Resources resourceObjectInstance) {
        //SKELETON
        setLayout(BoxLayout.y());
	setTitle("Centre de partage");
        Form previous = Display.getInstance().getCurrent();
        Toolbar tb=getToolbar();
	tb.setBackCommand("", e -> previous.showBack());
        tb.addMaterialCommandToRightBar("",FontImage.MATERIAL_BOOKMARKS,new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new FavoriteDocs(resourceObjectInstance).show();
            }
        });
        //floating button add
	FloatingActionButton fab  = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder)fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            new AddDoc().show();
        });
        //init arralyslits
        ArrayList<Document> docs;
        docs=ServiceDocument.getInstance().getAllDocs();
        ArrayList<Niveau> niveaux;
        niveaux=ServiceNiveau.getInstance().getAllNiveaux();
        ArrayList<Matiere> matieres;
        matieres=ServiceMatiere.getInstance().getAllMatieres();
        //BODY
        initGuiBuilderComponents(resourceObjectInstance,docs,niveaux,matieres,previous);
    }
                         
    private void initGuiBuilderComponents(Resources resourceObjectInstance,ArrayList<Document> docs,ArrayList<Niveau> niveaux,ArrayList<Matiere> matieres,Form previous) {
        String currentUser="Anas Houissa"; //to_change
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        //FILTRES
        //filtre niveau
        ComboBox cbNiveau=new ComboBox();
        for(Niveau n : niveaux){
            cbNiveau.addItem(n.getId());          
        }
        //filtre matiere
        ComboBox cbMatiere=new ComboBox();
        for(Matiere m : matieres){
                    cbMatiere.addItem(m.getId());          
        }
        Button filter_btn=new Button("Filtrer");
        filter_btn.setUIID("Button2");
        addAll(cbNiveau,cbMatiere,filter_btn);
        //filter action
        filter_btn.addActionListener(
                new ActionListener<ActionEvent>() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new FilteredDocs(resourceObjectInstance,cbNiveau.getSelectedItem().toString(),cbMatiere.getSelectedItem().toString()).show();
                    }
                });
        
        for (Document d : docs){
            //init vars
            String nomDoc=d.getNom();
            String niveauDoc=d.getNiveau();
            String matiereDoc=d.getMatiere();
            String dateDoc=d.getDate_insert();
            String propDoc=d.getProp();
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
            gui_Label_2.setText(niveauDoc+" | "+matiereDoc);
            gui_Label_2.setUIID("GreenLabel");
            gui_Text_Area_1.setText("Propriétaire: "+propDoc);
            gui_Text_Area_1.setUIID("SmallFontLabel");
            //sheet
            Button displaySheet_btn = new Button();
                    displaySheet_btn.addActionListener(e -> {
                        DocSheet sheet = new DocSheet(null,d,previous);
                        sheet.show();
                    });
            gui_Container_1.setLeadComponent(displaySheet_btn);
        }
    }
    
    public static Image getImageFromTheme(String name) {
        try {
            Resources resFile = Resources.openLayered("/theme");
            Image image = resFile.getImage(name);
            return image;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }    
}

