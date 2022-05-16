package com.mycompany.uis;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ThreadService;
import java.io.IOException;
import java.util.ArrayList;

public class Forum extends Form {
    Form current;
    int user = 1;
    public Forum( Resources res
) {
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
            
            
            if(t.getVerified().equals("true")){
            MultiButton mb = new MultiButton(t.getQuestion()+" [VERIFIED]");
            mb.setTextLine2("#"+t.getThreadType().substring(8,t.getThreadType().length()));
            Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            C2.add(mb);
            if(SessionManager.getId() == t.getUser())
            {
                
                Button b = new Button("Delete");
                Button update = new Button("Update");
                update.setUIID("RoundBtn");
                b.setUIID("IndianredRoundBtn");
                
                C2.add(update);
                C2.add(b);
                
                update.addActionListener(e->{
                new ForumUpdate(t,current).show();
                });
                b.addActionListener(e-> {
                    ThreadService.getInstance().delete(t);
                    new Forum(res).show();
                        });
                
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
            }
            else if(SessionManager.getRoles().equals("ROLE_ADMIN") ){
                Button b = new Button("Delete");
                b.setUIID("IndianredRoundBtn");
                
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
                b.addActionListener(e-> {
                    ThreadService.getInstance().delete(t);
                    new Forum(res).show();
                        });
                C2.add(b);
            }
            else{
                
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
            }
	            
            }
            else {
            MultiButton mb = new MultiButton(t.getQuestion());
            mb.setTextLine2("#"+t.getThreadType().substring(8,t.getThreadType().length()));
            Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            C2.add(mb);
            if(SessionManager.getId() == t.getUser())
            {
                
                Button b = new Button("Delete");
                Button update = new Button("Update");
                update.setUIID("RoundBtn");
                b.setUIID("IndianredRoundBtn");
                
                C2.add(update);
                C2.add(b);
                
                update.addActionListener(e->{
                new ForumUpdate(t,current).show();
                });
                b.addActionListener(e-> {
                    ThreadService.getInstance().delete(t);
                    new Forum(res).show();
                        });
                
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
            }
            else if(SessionManager.getRoles().equals("ROLE_ADMIN") ){
                Button b = new Button("Delete");
                Button verify = new Button("Verify");
                verify.setUIID("BlueRoundFilledBtn");
                verify.addActionListener(e->{ThreadService.getInstance().verify(t);
                new Forum(res).show();
                }
                );
                b.setUIID("IndianredRoundBtn");
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
                b.addActionListener(e-> {
                    ThreadService.getInstance().delete(t);
                    new Forum(res).show();
                        });
                C2.add(verify);
                C2.add(b);
            }
            else{
                
                mb.addActionListener(e-> new ThreadShow(current,t,res).show());
            }
            }
            
            
           
            
            
        
        
        
        }
        add(C2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new TabAff(res).show());
        
        btnAddThread.addActionListener(e-> new AddThread(current,res).show());
        btnAddThread.setUIID("addBtn");
        addAll(btnAddThread);
    }
}
