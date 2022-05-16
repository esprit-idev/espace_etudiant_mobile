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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.ThreadType;
import com.mycompany.entities.Thread;
import com.mycompany.services.ThreadService;
import com.mycompany.services.ThreadTypeService;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 21656
 */
public class ForumUpdate extends Form {
    public ForumUpdate(Thread t,Form previous){
        ArrayList<ThreadType> tt = new ArrayList(ThreadTypeService.getInstance().getAllThreadType());
      setTitle("Update Thread");
      setLayout(BoxLayout.y());
      TextField tfName= new TextField(t.getQuestion(),"Question");
      Button btnValider = new Button("Update");
      btnValider.setUIID("RoundFilledBtn");
      String[] s = new String[tt.size()];
      for(int i=0;i< tt.size();i++){
          s[i] = tt.get(i).getContent();
      }
      
      
     
      btnValider.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((tfName.getText().length()==0)){
                  Dialog.show("Alert","Please fill all the fields",new Command("OK"));
              }
              else{
                  try {
              
              if (ThreadService.getInstance().update(t, tfName.getText())){
              Dialog.show("Success","Connection accepted", new Command("OK"));
              new Forum(Resources.getGlobalResources()).show();
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
      getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
    }
  
    }

