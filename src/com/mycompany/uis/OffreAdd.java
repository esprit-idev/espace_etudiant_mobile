/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Calendar;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.mycompany.entities.CategoryEmploi;
import com.mycompany.entities.Emploi;
import com.mycompany.services.ServiceCategoryEmploi;
import com.mycompany.services.ServiceEmploi;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author eslem
 */
public class OffreAdd extends Form{
    public OffreAdd(){
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Ajouter une Publication");
        Font poppinsRegular55 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(55, Font.STYLE_PLAIN);
        Font poppinsRegular40 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(40, Font.STYLE_PLAIN);
        Font poppinsRegular35 = Font.createTrueTypeFont("regular", "Poppins-Regular.ttf").
                derive(35, Font.STYLE_PLAIN);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());
        //title
        Label title = new Label("titre");
        title.setUIID("CustomLabel");
        Style s_title = title.getUnselectedStyle();
        s_title.setFont(poppinsRegular40);
        TextField tftitle = new TextField("", "Ajouter un titre");
        tftitle.getHintLabel().setUIID("CustomHint");
        Style s_tfNomClubHint = tftitle.getHintLabel().getUnselectedStyle();
        s_tfNomClubHint.setFont(poppinsRegular35);
        //content
        Label lDesc = new Label("Description");
        lDesc.setUIID("CustomLabel");
        Style s_lDesc = lDesc.getUnselectedStyle();
        s_lDesc.setFont(poppinsRegular40);
        TextArea tfDesc = new TextArea("Ajoter une description",20,20, TextArea.ANY);
        //categories : comboBox 
        ArrayList<CategoryEmploi> categories;
        categories = ServiceCategoryEmploi.getInstance().displayCats();
        Label lcat = new Label("Categorie");
        lcat.setUIID("CustomLabel");
        Style s_lCat = lcat.getUnselectedStyle();
        s_lCat.setFont(poppinsRegular40);
        
        ComboBox cbCat = new ComboBox();
        for (CategoryEmploi cat : categories) {
            cbCat.addItem(cat.getCatgeoryName());
        }
        // add image 
        Button add_image = new Button("Image");
        //add btn
        Button add_btn = new Button("Ajouter");
        add_btn.setUIID("BlackRoundFilledBtn");
        Style s_add_btn=add_btn.getUnselectedStyle();
        s_add_btn.setFont(poppinsRegular55);

        cnt.addAll(title,tftitle,lDesc, tfDesc, lcat, cbCat, add_image,add_btn);
        add(cnt);
        //date 
        Calendar cl = new Calendar();
        String today = cl.getCurrentDate().toString();
        //upload an image
        CheckBox multiSelect = new CheckBox("Multi-Select");
        add_image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                                if (FileChooser.isAvailable()) {
                    FileChooser.setOpenFilesInPlace(true);
                    FileChooser.showOpenDialog(multiSelect.isSelected(),".jpeg, .jpg, .png/plain", e2-> {
        if (e2 == null || e2.getSource() == null) {
            add("No file was selected");
            revalidate();
        } else {
           String extension = null;
           if (multiSelect.isSelected()) {
               String[] paths = (String[]) e2.getSource();
               for(String path : paths){
                   System.out.println(path);
                   CN.execute(path);
               }          
           }
           String imgFile = (String) e2.getSource();
           if( imgFile == null){
               add("No file was selected");
               revalidate();
           }else{
               Image image;
               try {
                   image = Image.createImage(imgFile).scaledHeight(500);
                   add(image);
                   String f = FileSystemStorage.getInstance().getAppHomePath();
                   
                   try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(f)){
                   System.out.print(f);
                   ImageIO.getImageIO().save(image, os, ImageIO.FORMAT_PNG, 1);
                   } catch(IOException err){
                       
                   }
               } catch (IOException ex) {
                  System.out.println("exx : " +ex);
               }
           }
           String ex = null;
           if ( imgFile.lastIndexOf(".") > 0) {
               ex = imgFile.substring(imgFile.lastIndexOf(".") + 1 );
               StringBuilder file = new StringBuilder(imgFile); 
              if( imgFile.startsWith("file://")){
                  file.delete(0, 7);
              }
              int lastIndexPeriod = file.toString().lastIndexOf(".");
              Log.p(file.toString());
              String ext = file.toString().substring(lastIndexPeriod);
              String more = file.toString().substring(0,lastIndexPeriod - 1);
              try{
                  String namePic = saveFileToDevice(imgFile,ext);
                  System.out.print(namePic);
                      //action add btn
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //ajout data
                if (tftitle.getText().isEmpty() || namePic.isEmpty()) {
                    //toast if empty
                    ToastBar.showErrorMessage("Veuillez remplir tous les champs", FontImage.MATERIAL_ERROR);
                } else {
                    //create new publication
                    Emploi pub = new Emploi(tftitle.getText(),tfDesc.getText().toString(),cbCat.getSelectedItem().toString(),today,namePic);
                    if (ServiceEmploi.getInstance().addPublication(pub)) {
                        //success toast
                        ToastBar.showMessage("Pub ajouté", FontImage.MATERIAL_CHECK_CIRCLE);
                        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));

                        new OffresEmplois().show();
                    } else {
                        //error toast
                        ToastBar.showMessage("Une erreur est survenue lors de l'ajout ", FontImage.MATERIAL_ERROR);
                    }
                }
            }
        });
              }catch(IOException exx){
                  System.out.println("exx : " +exx);
              }
           revalidate();
            }
            } 
        });
    }
            }
    });
     
    }
       
    protected String saveFileToDevice(String file, String extension) throws IOException {
            String path = Static.News_Emploi_Pic;
            int index = file.lastIndexOf("/");
            file = file.substring(index + 1);
            return file;
    }
}
