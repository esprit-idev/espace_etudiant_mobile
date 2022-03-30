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
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.services.ThreadService;
import java.util.ArrayList;

public class Forum extends Form {
    Form current;
    int user = 1;
    public Forum() {
        current = this;
        Toolbar tb = getToolbar();
        setLayout(BoxLayout.y());
        setTitle("Forum");
        Label titre = new Label("List of threads");
        titre.setUIID("ThreadLabel");
        Form previous = Display.getInstance().getCurrent();
        add(titre);
        Button btnAddThread= new Button("+");
        ArrayList<com.mycompany.entities.Thread> Threads = new ArrayList(ThreadService.getInstance().getAllThreads());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        for(com.mycompany.entities.Thread t:Threads) {
            
            MultiButton mb = new MultiButton(t.getQuestion());
            mb.setTextLine2("#"+t.getThreadType().substring(8,t.getThreadType().length()));
            Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            if(t.getUser()== user )
            {
                Button b = new Button("Delete");
                Button update = new Button("Update");
                update.setUIID("RoundBtn");
                b.setUIID("IndianredRoundBtn");
                C2.add(mb);
                C2.add(update);
                C2.add(b);
                
                update.addActionListener(e->{
                new ForumUpdate(t,current).show();
                });
                b.addActionListener(e-> {
                    ThreadService.getInstance().delete(t);
                    new Forum().show();
                        });
                
                mb.addActionListener(e-> new ThreadShow(current,t).show());
            }
            else{
                C2.add(mb);
                mb.addActionListener(e-> new ThreadShow(current,t).show());
            }
            
           
            
            
        
        
        
        }
        add(C2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
        
        btnAddThread.addActionListener(e-> new AddThread(current).show());
        btnAddThread.setUIID("addBtn");
        addAll(btnAddThread);
    }
}
