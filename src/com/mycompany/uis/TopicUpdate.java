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

/**
 *
 * @author 21656
 */
public class TopicUpdate extends Form {

    public TopicUpdate(ThreadType t, Form current,Resources res) {
        setTitle("Update Thread");
      setLayout(BoxLayout.y());
      TextField tfName= new TextField(t.getContent(),"Question");
      Button btnValider = new Button("Update");
      btnValider.setUIID("RoundFilledBtn");
      
      
      
     
      btnValider.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((tfName.getText().length()==0)){
                  Dialog.show("Alert","Please fill all the fields",new Command("OK"));
              }
              else{
                  try {
              
              if (ThreadTypeService.getInstance().update(t, tfName.getText())){
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
      getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e->new Topic(res).show());
    
    }
    
}
