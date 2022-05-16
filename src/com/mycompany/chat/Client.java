/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat;

/**
 *
 * @author aa
 */
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.uis.Conversation;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
public class Client implements Runnable{
    
    public int port;
    public String serverAddr;
    public Socket socket;
    public Conversation ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;

    public Client(Conversation ui) throws IOException {
        
        this.ui = ui;
        this.serverAddr = ui.serverAddr; this.port = ui.port;
        System.out.println(InetAddress.getLocalHost());
        socket = new Socket(InetAddress.getLocalHost(),2018);
        System.out.println("test test");
         Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
    }

    
    
    
      @Override
    public void run() {
         boolean keepRunning = true;
         while(keepRunning){
             try {
                 Message msg = (Message) In.readObject();
                 System.out.println("Incoming : "+msg.toString());
                 System.out.println("Incoming : "+msg.toString());
                 System.out.println("Incoming : "+msg.toString());
                  
                 
                int test=msg.getUser().getId();
                    if((test== ui.uid)){
      
                          
                          

   Label right = new Label("moi: " + msg.getContent());
   right.setUIID("CustomLabel");
                right.setTextPosition(Component.RIGHT);
             //   ui.cnt.add(right);     
             Container cnt =new Container(BoxLayout.y());
             cnt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
             cnt.add(right);
                          
                       
                            
                    ui.add(cnt);
                                   }
                    else{

                          
                         
                Label left = new Label("Etudiant: " + msg.getContent());
                left.setTextPosition(Component.LEFT);
                //ui.cnt.add(left);
                //ui.add(ui.cnt);
                Container cnt =new Container(BoxLayout.y());
                cnt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
             cnt.add(left);
                           ui.add(cnt);
     
                           
                                    }
             }
                    
              catch (IOException ex) {
                 System.out.println(ex);
             } catch (ClassNotFoundException ex) {
                  System.out.println(ex);
             }
             
         }


    }
    
    
    
    
     public void send(String msg,int classe){
        try {
            Classe c=new Classe();
            User u = new User();
            c.setId(classe);
            u.setId(ui.uid);
            Message m=new Message();
                        Date date = new Date(System.currentTimeMillis());
    m.setPostDate(date); 
            m.setClasse(c);
            m.setUser(u);
            m.setContent(msg);
            
            Out.writeObject(m);
            Out.flush();
            System.out.println("Outgoing : "+m.toString());
            
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()"+ ex.getStackTrace());
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
    
   

  
}
