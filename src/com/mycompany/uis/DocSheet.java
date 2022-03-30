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
import com.mycompany.entities.Document;
import com.mycompany.entities.DocumentFavoris;
import com.mycompany.services.ServiceDocument;
import com.mycompany.services.ServiceDocumentFavoris;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MeriamBI
 */
public class DocSheet extends Sheet {

    DocSheet(Sheet parent, Document doc, Form previous, int admin, boolean ignore) {
        super(parent, "");
        setUIID("CustomSheet");
        String propDoc = doc.getProp();
        int idDoc = doc.getId();
        int userId = 5;
        Container cnt = getContentPane();
        cnt.setLayout(BoxLayout.y());
        Font poppinsRegular = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(50, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        //open btn
        Button open_btn = new Button("Ouvrir");
        open_btn.setUIID("CustomItem");
        Style s_open_btn = open_btn.getUnselectedStyle();
        s_open_btn.setFont(poppinsRegular);
        open_btn.addActionListener(e -> {
            ServiceDocument.getInstance().DisplayDoc(doc);
            back();
        });
        cnt.add(open_btn);

        if (admin != 1) {//to_change
            //share btn
            Button share_btn = new Button("Partager");
            share_btn.setUIID("CustomItem");
            Style s_share_btn = share_btn.getUnselectedStyle();
            s_share_btn.setFont(poppinsRegular);
            share_btn.addActionListener(e -> {
                back();
                new DocShare(doc).show();

            });
            //PIN/UNPIN check
            ArrayList<DocumentFavoris> favs;
            favs = ServiceDocumentFavoris.getInstance().getAllPinnedDocs();
            List<Integer> favIds = new ArrayList<Integer>();
            for (int i = 0; i < favs.size(); i++) {
                favIds.add(favs.get(i).getDoc().getId());
            }
            Button unpin_btn = new Button("Détacher");
            unpin_btn.setUIID("CustomItem");
            Style s_unpin_btn = unpin_btn.getUnselectedStyle();
            s_unpin_btn.setFont(poppinsRegular);
            Button pin_btn = new Button("Épingler");
            pin_btn.setUIID("CustomItem");
            Style s_pin_btn = pin_btn.getUnselectedStyle();
            s_pin_btn.setFont(poppinsRegular);
            boolean pinned = favIds.contains(idDoc);
            if (pinned) {
                cnt.add(unpin_btn);
                //unpin btn
                unpin_btn.addActionListener(e -> {
                    ServiceDocumentFavoris.getInstance().UnpinDoc(idDoc, userId);
                    back();
                });
            } else {
                cnt.add(pin_btn);
                //pin btn
                pin_btn.addActionListener(e -> {
                    //ServiceDocumentFavoris.getInstance().PinDoc(doc, user); //get current user
                    back();
                });
            }
            cnt.add(share_btn);
        }

        if (ignore) {
            //ignore report btn
            Button ignore_btn = new Button("Ignorer le signal");
            ignore_btn.setUIID("CustomItem");
            Style s_ignore_btn = ignore_btn.getUnselectedStyle();
            s_ignore_btn.setFont(poppinsRegular);
            ignore_btn.addActionListener(e -> {
                //add confirm
                Dialog dialog = new Dialog(BoxLayout.y());
                dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                Style style = dialog.getDialogStyle();
                style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                dialog.setDisposeWhenPointerOutOfBounds(true);
                // title
                Label title = new Label();
                title.setText("Ignorer ce signal");
                title.setUIID("CenterNoPadd");
                Style s_title = title.getUnselectedStyle();
                s_title.setFont(poppinsRegular);
                dialog.add(title);
                // body
                SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir ignorer ce signal?");
                Style s_bodyLabel = bodyLabel.getTextAllStyles();
                s_bodyLabel.setFont(poppinsRegular40);
                dialog.add(bodyLabel);
                // confirm supp button
                Button confirm_btn = new Button("Oui");
                confirm_btn.setUIID("BlueRoundFilledBtn");
                Style s_confirm_btn = confirm_btn.getUnselectedStyle();
                s_confirm_btn.setFont(poppinsRegular);
                confirm_btn.addActionListener(e2 -> {
                    if (ServiceDocument.getInstance().ignoreSignal(doc)) {
                        ToastBar.showMessage("Signal ignoré", FontImage.MATERIAL_CHECK_CIRCLE);
                    } else {
                        ToastBar.showMessage("Une erreur est survenue lors de la suppression du document", FontImage.MATERIAL_ERROR);
                    }
                    dialog.dispose();
                    back();
                });
                // deny button
                Button deny_btn = new Button("Non");
                deny_btn.setUIID("BlueRoundBtn");
                Style s_deny_btn = deny_btn.getUnselectedStyle();
                s_deny_btn.setFont(poppinsRegular);
                deny_btn.addActionListener(e3 -> {
                    dialog.dispose();
                    back();
                });
                dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
                dialog.show();
            });
            cnt.add(ignore_btn);
        }

        //if owner or admin add a delete btn
        //String currentUser=SessionManager.getUsername()+" "+SessionManager.getPrenom();
        String currentUser = "Anas Houissa"; //to_change
        Button supp_btn = new Button("Supprimer");
        supp_btn.setUIID("CustomItem");
        Style s_supp_btn = supp_btn.getUnselectedStyle();
        s_supp_btn.setFont(poppinsRegular);
        Button report_btn = new Button("Signaler");
        report_btn.setUIID("CustomItem");
        Style s_report_btn = report_btn.getUnselectedStyle();
        s_report_btn.setFont(poppinsRegular);
        if (currentUser.equals(propDoc) || admin == 1) {
            supp_btn.addActionListener(e -> {
                //add confirm
                Dialog dialog = new Dialog(BoxLayout.y());
                dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                Style style = dialog.getDialogStyle();
                style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                dialog.setDisposeWhenPointerOutOfBounds(true);
                // title
                Label title = new Label();
                title.setText("Supprimer document");
                title.setUIID("CenterNoPadd");
                Style s_title = title.getUnselectedStyle();
                s_title.setFont(poppinsRegular);
                dialog.add(title);
                // body
                SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir supprimer ce document?");
                Style s_bodyLabel = bodyLabel.getTextAllStyles();
                s_bodyLabel.setFont(poppinsRegular40);
                dialog.add(bodyLabel);
                // confirm supp button
                Button confirm_btn = new Button("Oui");
                confirm_btn.setUIID("IndianredRoundFilledBtn");
                Style s_confirm_btn = confirm_btn.getUnselectedStyle();
                s_confirm_btn.setFont(poppinsRegular);
                confirm_btn.addActionListener(e2 -> {
                    if (ServiceDocument.getInstance().deleteDoc(doc.getId())) {
                        ToastBar.showMessage("Document supprimé", FontImage.MATERIAL_CHECK_CIRCLE);
                    } else {
                        ToastBar.showMessage("Une erreur est survenue lors de la suppression du document", FontImage.MATERIAL_ERROR);
                    }
                    dialog.dispose();
                    back();
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
        } //else if not owner add a report btn
        else {
            //report btn
            report_btn.addActionListener(e -> {
                //add confirm
                Dialog dialog = new Dialog(BoxLayout.y());
                dialog.setUIID("Container"); // this line has no effect, the outside dialog component is still visible
                Style style = dialog.getDialogStyle();
                style.setMargin(5, 5, 5, 5); // adding some margin between contentpane and Dailog container, to be more obvious
                dialog.setDisposeWhenPointerOutOfBounds(true);
                // title
                Label title = new Label();
                title.setText("Signaler document");
                title.setUIID("CenterNoPadd");
                Style s_title = title.getUnselectedStyle();
                s_title.setFont(poppinsRegular);
                dialog.add(title);
                // body
                SpanLabel bodyLabel = new SpanLabel("Êtes-vous sûr de vouloir signaler ce document?");
                Style s_bodyLabel = bodyLabel.getTextAllStyles();
                s_bodyLabel.setFont(poppinsRegular40);
                dialog.add(bodyLabel);
                // confirm signal button
                Button confirm_btn = new Button("Oui");
                confirm_btn.setUIID("YellowRoundFilledBtn");
                Style s_confirm_btn = confirm_btn.getUnselectedStyle();
                s_confirm_btn.setFont(poppinsRegular);
                confirm_btn.addActionListener(e2 -> {
                    if (ServiceDocument.getInstance().signalDoc(doc)) {
                        ToastBar.showMessage("Le document a été signalé", FontImage.MATERIAL_CHECK_CIRCLE);
                    } else {
                        ToastBar.showMessage("Une erreur est survenue lors du signal du document", FontImage.MATERIAL_ERROR);
                    }
                    dialog.dispose();
                    back();
                });
                // deny button
                Button deny_btn = new Button("Non");
                deny_btn.setUIID("YellowRoundBtn");
                Style s_deny_btn = deny_btn.getUnselectedStyle();
                s_deny_btn.setFont(poppinsRegular);
                deny_btn.addActionListener(e3 -> {
                    dialog.dispose();
                    back();
                });
                dialog.add(GridLayout.encloseIn(2, confirm_btn, deny_btn));
                dialog.show();
            });
            cnt.add(report_btn);
        }

    }
}
