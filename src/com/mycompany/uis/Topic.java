/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uis;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.ThreadType;
import com.mycompany.services.ThreadService;
import com.mycompany.services.ThreadTypeService;
import java.util.ArrayList;

/**
 *
 * @author 21656
 */
public class Topic extends Form{
    Form current;
    
    public Topic() {
        current = this;
        Toolbar tb = getToolbar();
        setLayout(BoxLayout.y());
        setTitle("Topics");
        Label titre = new Label("List of topics");
        titre.setUIID("ThreadLabel");
        Form previous = Display.getInstance().getCurrent();
        add(titre);
        Button btnAddThread= new Button("+");
        ArrayList<com.mycompany.entities.ThreadType> Topics = new ArrayList(ThreadTypeService.getInstance().getAllThreadType());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        for(ThreadType t:Topics) {
            
            MultiButton mb = new MultiButton(t.getContent());
            
            Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
                Button b = new Button("Delete");
                Button update = new Button("Update");
                update.setUIID("RoundBtn");
                b.setUIID("IndianredRoundBtn");
                C2.add(mb);
                C2.add(update);
                C2.add(b);
                
                update.addActionListener(e->{
                new TopicUpdate(t,current).show();
                });
                b.addActionListener(e-> {
                    ThreadTypeService.getInstance().delete(t);
                    new Topic().show();
                        });
            
            
           
            
            
        
        
        
        }
        add(C2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
        
        btnAddThread.addActionListener(e-> new AddTopic(current).show());
        btnAddThread.setUIID("addBtn");
        addAll(btnAddThread);
        
    }
    
}
