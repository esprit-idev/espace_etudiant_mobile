/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import static com.codename1.ui.FontImage.MATERIAL_ARROW_BACK;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.ThreadType;
import com.mycompany.services.ThreadService;
import com.mycompany.services.ThreadTypeService;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 21656
 */
public class AddThread extends Form{
    
    public AddThread(Form previous, Resources res){
        ArrayList<ThreadType> tt = new ArrayList(ThreadTypeService.getInstance().getAllThreadType());
      setTitle("Add a new Thread");
      setLayout(BoxLayout.y());
      TextField tfName= new TextField("","Question");
      Button btnValider = new Button("Add Thread");
      btnValider.setUIID("RoundFilledBtn");
      String[] s = new String[tt.size()];
      for(int i=0;i< tt.size();i++){
          s[i] = tt.get(i).getContent();
      }
      
      
      Picker p = new Picker();
      p.setStrings(s);
      
      btnValider.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((tfName.getText().length()==0)){
                  Dialog.show("Alert","Please fill all the fields",new Command("OK"));
              }
              else{
                  try {
                      com.mycompany.entities.Thread t = new com.mycompany.entities.Thread();
              t.setQuestion(tfName.getText());
              t.setDisplay(false);
              
              t.setPostDate((new Date()).toString());
              if (ThreadService.getInstance().addThread(t,p.getSelectedString(),SessionManager.getId())){
              Dialog.show("Success","Connection accepted", new Command("OK"));
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
      
      addAll(tfName,p,btnValider);
      getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e->new Forum(res).show());
    }
  
}
