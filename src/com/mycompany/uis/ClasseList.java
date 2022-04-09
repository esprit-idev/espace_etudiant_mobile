/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Classe;
import com.mycompany.services.ServiceClasse;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class ClasseList extends Form{
    
    
    public ClasseList(){
         Toolbar tb=getToolbar();
		setTitle("Classe");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		Container cnt = new Container(BoxLayout.y());
                Button add=new Button("Ajouter une classe");
                add.addActionListener((e)-> {
                new ClasseAdd().show();

        });
                cnt.add(add);
                
                ArrayList<Classe> list = ServiceClasse.getInstance().getAllClasse();
                      for (Classe n : list){
                          
                          Container cntb = new Container(BoxLayout.x());
                                                    Label classe = new Label ("Niveau: "+n.getNiveau()+"  Classe:"+n.getClasse());
                                  Button btnSend= new Button("Supprimer");
                                   Button btnView= new Button("show");
                                   Button btnUpdate= new Button("Metter a jour");
                                   
                                   
                                   
                                   btnUpdate.addActionListener((e)-> {
                new ClasseUpdate(n.getId(),n.getClasse(),n.getNiveau()).show();

        });

        
         btnSend.addActionListener((e)-> {
                ServiceClasse.getInstance().DeleteClasse(n.getId());
                new ClasseList().show();

        });
         btnView.addActionListener((e)-> {
                
                new ClasseEtudiant(n.getId()).show();

        });

         
         cntb.addAll(btnSend,btnUpdate);
         cnt.addAll(classe,cntb,btnView);
                          
                          
                          System.out.println(n.getClasse());
                      }
                      
                      
                      add(cnt);
    }
    
    
}
