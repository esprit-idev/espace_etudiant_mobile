/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.chat.Classe;
import com.mycompany.chat.Client;
import com.mycompany.entities.Message;
import com.mycompany.services.ServiceClasse;
import com.mycompany.services.ServiceMessage;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class Conversation extends Form {
    public int uid=SessionManager.getId();
       public Socket socket;
   public OutputStream out;
   public PrintWriter ostream;
  public   BufferedReader in;
     public String serverAddr;
    public int port;
public Client client;
public Thread clientThread;
public Container cnt ;
public Classe c;
int i;
ServiceMessage w=ServiceMessage.getInstance();
    public Conversation() {
          port=2018;
          setLayout(new BoxLayout(BoxLayout.Y_AXIS));
           ArrayList<Classe> list2 = ServiceClasse.getInstance().getClasse(uid);
                      for (Classe n : list2){
                          
                         i=n.getId();
                                   
                               System.out.println(i);
                                   
                      }
          
          
         
        
                  
        Toolbar tb = getToolbar();
        setTitle("Conversation");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        cnt = new Container(BoxLayout.y());
                int cc=i;

        TextField text = new TextField("", "Ecrire votre message ...");
        text.setUIID("message");

        Button btnSend = new Button("Envoyer");

     

        cnt.addAll(text, btnSend);

        ArrayList<Message> list = ServiceMessage.getInstance().afficheMessage(i);
       

        for (Message m : list) {

            if (m.getUser() == uid) {
                Label right = new Label("moi: " + m.getContent());
                right.setTextPosition(Component.RIGHT);
                cnt.add(right);

            } else {
                Label left = new Label("Etudiant: " + m.getContent());
                left.setTextPosition(Component.LEFT);
                cnt.add(left);

            }

            System.out.println(m.getPostDate());
        }

         try{
    
    
                client = new Client(this);
                clientThread = new Thread(client);
                clientThread.start();
                
            }
            catch(Exception ex){
                System.out.println(ex);
            }
           btnSend.addActionListener((e) -> {
           client.send(text.getText(),cc);
            ServiceMessage.getInstance().sendMessage(uid, text.getText());
          
             Label right = new Label("moi: " + text.getText());
                right.setTextPosition(Component.RIGHT);
             //   ui.cnt.add(right);     
             Container cnt1 =new Container(BoxLayout.y());
             cnt1.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
             cnt1.add(right);
                          
                       
                            
                    add(cnt1);
text.setText("");
        });
        add(cnt);

    }

}
