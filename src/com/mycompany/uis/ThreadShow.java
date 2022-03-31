/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.MultiButton;
import com.codename1.io.Util;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.io.Util;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.services.ThreadService;
import java.util.ArrayList;
import com.mycompany.entities.Thread;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Reponse;
import com.mycompany.services.ReponseService;
import java.util.Date;

/**
 *
 * @author 21656
 */
public class ThreadShow extends Form {
    public ThreadShow(Form previous,Thread t){
        
        int admin;
        if(SessionManager.getRoles()=="ROLE_ADMIN")
        {
            admin = 1;
        }
        else{
            admin =0;
        }
        String[] id = Util.split(t.getId(),".");
        ArrayList<Reponse> Reponses = new ArrayList(ReponseService.getInstance().getAllReponses(Integer.parseInt(id[0])));
        
        setTitle("Thread");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container C3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
            Label top = new Label("Top");
            top.setUIID("ForumLabel");
            top.setTextPosition(Component.TOP);
            top.setText(t.getQuestion());
            Label le = new Label(" ");
            le.setUIID("Separator");
            C2.add(top);
            C2.add(le);
            
        
       TextField tfName= new TextField("","Reply");
       Button btnValider = new Button("Send");
       btnValider.setUIID("RoundFilledBtn");
       btnValider.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((tfName.getText().length()==0)){
                  Dialog.show("Alert","Please fill all the fields",new Command("OK"));
              }
              else{
                  try {
                      Reponse r = new Reponse();
              r.setReply(tfName.getText());
              r.setDisplay(false);
              
              t.setPostDate((new Date()).toString());
              if (ReponseService.getInstance().addReponse(r,SessionManager.getId(),t)){
              Dialog.show("Success","Connection accepted", new Command("OK"));
              new Forum().show();
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
       
        for(Reponse r:Reponses) {
            Container repC = new Container(BoxLayout.x());
            Label rep = new Label("TOP");
            Label date = new Label();
            date.setUIID("dateLabel");
            date.setText(r.getReplyDate());
            rep.setTextPosition(Component.TOP);
            rep.setText(r.getReply());
            rep.setUIID("reponseLabel");
            repC.add(rep);
            repC.add(date);
            Label l = new Label(" ");
            l.setUIID("Separator");
            C3.add(repC);
            C3.add(l);
            if(admin == 1){
                Button del = new Button("delete");
                del.addActionListener(e-> {
                    ReponseService.getInstance().delete(r);
                     new Forum().show();       });
                del.setUIID("IndianredRoundBtn");
                C3.add(del);
            }
            
        }
        
        add(C2);
        add(tfName);
        add(btnValider);
        add(C3);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
        
    }
    
}
