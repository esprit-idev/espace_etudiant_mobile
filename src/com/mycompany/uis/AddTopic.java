/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import com.mycompany.entities.ThreadType;

import com.mycompany.services.ThreadTypeService;
import java.util.ArrayList;


/**
 *
 * @author 21656
 */
public class AddTopic extends Form{
    Form current;
    AddTopic(Form previous,Resources res) {
           
      setTitle("Add a new Topic");
      setLayout(BoxLayout.y());
      TextField tfName= new TextField("","Question");
      Button btnValider = new Button("Add Topic");
      btnValider.setUIID("RoundFilledBtn");
      
      
      
      
      
      btnValider.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((tfName.getText().length()==0)){
                  Dialog.show("Alert","Please fill all the fields",new Command("OK"));
              }
              else{
                  try {
                     ThreadType t = new ThreadType();
              t.setContent(tfName.getText());
              if (ThreadTypeService.getInstance().addTopic(t)){
              Dialog.show("Success","Connection accepted", new Command("OK"));
              new Topic(res).show();
              }
              else {
                  Dialog.show("Error","Server error", new Command("OK"));
              }
              }
                  catch(Exception e){
                      Dialog.show("Error","ERROR",new Command("OK"));
                  }
              
              }
          }
      });
      
      addAll(tfName,btnValider);
      getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> new Topic(res).show());
    }
    }
    

