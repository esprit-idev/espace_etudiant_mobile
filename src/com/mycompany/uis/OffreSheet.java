/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Sheet;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.CategoryEmploi;
import com.mycompany.services.ServiceCategoryEmploi;
import com.mycompany.services.ServiceEmploi;

/**
 *
 * @author eslem
 */
public class OffreSheet extends Sheet{
    //offre 
       
        public OffreSheet(Sheet parent,int id, String title,String content,String categoryName,Form previous){
         super(parent, "");
         setUIID("CustomSheet");
        Container cnt = getContentPane();
        cnt.setLayout(BoxLayout.y());
        Font poppinsRegular = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(50, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);

        //update btn
        Button update_btn = new Button("Modifier");
        update_btn.setUIID("CustomItem");
        Style s_update_btn = update_btn.getUnselectedStyle();
        s_update_btn.setFont(poppinsRegular);
        update_btn.addActionListener(e -> {
            new OffreUpdate(id, title,content,categoryName).show();
            
        });
        cnt.add(update_btn);

        Button supp_btn = new Button("Supprimer");
        supp_btn.setUIID("CustomItem");
        Style s_supp_btn = supp_btn.getUnselectedStyle();
        s_supp_btn.setFont(poppinsRegular);
        supp_btn.addActionListener(e -> {
            //add confirm
            Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
            dialog.setDisposeWhenPointerOutOfBounds(true);
            // title
            Label titleTxt = new Label("Supprimer");
            titleTxt.setUIID("CenterNoPadd");
            Style s_title = titleTxt.getUnselectedStyle();
            s_title.setFont(poppinsRegular);
            dialog.add(title);
            // body
            SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir supprimer cette publication?");
            Style s_bodyLabel = bodyLabel.getTextAllStyles();
            s_bodyLabel.setFont(poppinsRegular40);
            dialog.add(bodyLabel);
            // confirm supp button
            Button confirm_btn = new Button("Oui");
            confirm_btn.setUIID("IndianredRoundFilledBtn");
            Style s_confirm_btn = confirm_btn.getUnselectedStyle();
            s_confirm_btn.setFont(poppinsRegular);
            confirm_btn.addActionListener(e2 -> {
                if (ServiceEmploi.getInstance().deleteOffre(id)) {
                    ToastBar.showMessage("Publication supprimée", FontImage.MATERIAL_CHECK_CIRCLE);

                    new OffresEmplois().show();

                } else {
                    ToastBar.showMessage("Une erreur est survenue lors de la suppression", FontImage.MATERIAL_ERROR);
                }
                //dialog.dispose();
                // back();
            });
            // deny button
            Button deny_btn = new Button("Non");
            deny_btn.setUIID("IndianredRoundBtn");
            Style s_deny_btn = deny_btn.getUnselectedStyle();
            s_deny_btn.setFont(poppinsRegular);
            deny_btn.addActionListener(e3 -> {
                dialog.dispose();
                back();
            });
            dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
            dialog.show();
        });
        cnt.add(supp_btn);
           
        }
        //categorie offre
        public OffreSheet(Sheet parent, CategoryEmploi cat, Form previous) {
        super(parent, "");
        setUIID("CustomSheet");
        int id = cat.getId();
       System.out.println(id);
        String nomCat = cat.getCategoryName();
        System.out.println(nomCat);
        Container cnt = getContentPane();
        cnt.setLayout(BoxLayout.y());
        Font poppinsRegular = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(50, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);

        //update btn
        Button update_btn = new Button("Modifier");
        update_btn.setUIID("CustomItem");
        Style s_update_btn = update_btn.getUnselectedStyle();
        s_update_btn.setFont(poppinsRegular);
        update_btn.addActionListener(e -> {
            System.out.print(id);
                new OffreCategoryUpdate(id,nomCat).show();
        });
        cnt.add(update_btn);

        Button supp_btn = new Button("Supprimer");
        supp_btn.setUIID("CustomItem");
        Style s_supp_btn = supp_btn.getUnselectedStyle();
        s_supp_btn.setFont(poppinsRegular);
        supp_btn.addActionListener(e -> {
            //add confirm
            Dialog dialog = new Dialog(BoxLayout.y());
            dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
            Style style = dialog.getDialogStyle();
            style.setMargin(5, 5, 5, 5); 
            dialog.setDisposeWhenPointerOutOfBounds(true);
            // title
            Label title = new Label();
            title.setText("Supprimer categorie");
            title.setUIID("CenterNoPadd");
            Style s_title = title.getUnselectedStyle();
            s_title.setFont(poppinsRegular);
            dialog.add(title);
            // body
            SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir supprimer cette categorie?");
            Style s_bodyLabel = bodyLabel.getTextAllStyles();
            s_bodyLabel.setFont(poppinsRegular40);
            dialog.add(bodyLabel);
            // confirm supp button
            Button confirm_btn = new Button("Oui");
            confirm_btn.setUIID("IndianredRoundFilledBtn");
            Style s_confirm_btn = confirm_btn.getUnselectedStyle();
            s_confirm_btn.setFont(poppinsRegular);
            confirm_btn.addActionListener(e2 -> {
                if (ServiceCategoryEmploi.getInstance().deleteCategory(id)) {
                    ToastBar.showMessage("Categorie supprimée", FontImage.MATERIAL_CHECK_CIRCLE);
                    new OffreCategory().show();

                } else {
                    ToastBar.showMessage("Une erreur est survenue lors de la suppression de la Categorie", FontImage.MATERIAL_ERROR);
                }
                //dialog.dispose();
               // back();
            });
            // deny button
            Button deny_btn = new Button("Non");
            deny_btn.setUIID("IndianredRoundBtn");
            Style s_deny_btn = deny_btn.getUnselectedStyle();
            s_deny_btn.setFont(poppinsRegular);
            deny_btn.addActionListener(e3 -> {
                dialog.dispose();
                back();
            });
            dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
            dialog.show();
        });
        cnt.add(supp_btn);

    }
}
