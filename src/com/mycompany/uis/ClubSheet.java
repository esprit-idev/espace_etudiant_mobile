/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.mycompany.services.ClubService;

/**
 *
 * @author anash
 */
public class ClubSheet extends Sheet {

    public ClubSheet(Sheet parent, String idClub, String nomClub, String clubDesc, String clubCat, String clubRespo, Form previous) {
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
            new ClubUpdate(idClub, nomClub, clubDesc, clubCat, clubRespo).show();
            
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
            Label title = new Label();
            title.setText("Supprimer club");
            title.setUIID("CenterNoPadd");
            Style s_title = title.getUnselectedStyle();
            s_title.setFont(poppinsRegular);
            dialog.add(title);
            // body
            SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir supprimer ce club?");
            Style s_bodyLabel = bodyLabel.getTextAllStyles();
            s_bodyLabel.setFont(poppinsRegular40);
            dialog.add(bodyLabel);
            // confirm supp button
            Button confirm_btn = new Button("Oui");
            confirm_btn.setUIID("IndianredRoundFilledBtn");
            Style s_confirm_btn = confirm_btn.getUnselectedStyle();
            s_confirm_btn.setFont(poppinsRegular);
            confirm_btn.addActionListener(e2 -> {
                if (ClubService.getInstance().deleteClub(idClub)) {
                    ToastBar.showMessage("Club supprimée", FontImage.MATERIAL_CHECK_CIRCLE);

                    new ClubsList().show();

                } else {
                    ToastBar.showMessage("Une erreur est survenue lors de la suppression du club", FontImage.MATERIAL_ERROR);
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
