/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceClasse;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class ClasseEtudiant extends Form {
    public ClasseEtudiant(String id,Resources res){
        System.out.println(id);
        Toolbar tb=getToolbar();
		setTitle("Liste d'etudiants");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, Integer.parseInt("200")));
                new ClasseList(res).show();
            }
        });
		Container cnt = new Container(BoxLayout.y());
                Button btn= new Button("Ajouter un etudiant");
                btn.setUIID("BlueRoundFilledBtn");
                btn.addActionListener((e)->{
                    new Classe2etudiant(id,res).show();
                });
                
                cnt.add(btn);
                
                                 ArrayList<User> list = ServiceClasse.getInstance().viewClasse(id);
                      for (User n : list){
                         
                                 SpanLabel d = new SpanLabel("Nom:"+n.getUsername()+"      Prenom:"+n.getPrenom());
d.setUIID("CustomLabel");
         Button supp= new Button("Supprimer");
         supp.setUIID("BlackRoundFilledBtn");
                supp.addActionListener((e)->{
                    String s=n.getEmail();

                   ServiceClasse.getInstance().DeleteS(n.getEmail());
                   
                  new ClasseEtudiant(id,res).show();
                });
        

         
         cnt.addAll(d,supp);
                          
                      }
                      add(cnt);
                
        
        
    }
}
