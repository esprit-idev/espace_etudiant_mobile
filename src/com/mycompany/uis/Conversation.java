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
import com.mycompany.entities.Message;
import com.mycompany.services.ServiceMessage;
import java.util.ArrayList;

/**
 *
 * @author aa
 */
public class Conversation extends Form {

    public Conversation() {
        Toolbar tb = getToolbar();
        setTitle("Conversation");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());

        ArrayList<Message> list = ServiceMessage.getInstance().afficheMessage(SessionManager.getId());

        for (Message m : list) {

            if (m.getUser() == SessionManager.getId()) {

                Label right = new Label("moi: " + m.getContent());
                right.setTextPosition(Component.RIGHT);
                cnt.add(right);

            } else {
                Label left = new Label("Student: " + m.getContent());
                left.setTextPosition(Component.LEFT);
                cnt.add(left);

            }

            System.out.println(m.getPostDate());
        }

        TextField text = new TextField("", "Ecrire votre message ...");
        text.setUIID("message");

        Button btnSend = new Button("Envoyer");

        btnSend.addActionListener((e) -> {
            ServiceMessage.getInstance().sendMessage(SessionManager.getId(), text.getText());
            Label r = new Label("moi: " + text.getText());
            text.setText("");
            cnt.add(r);
            cnt.repaint();

        });

        cnt.addAll(text, btnSend);
        add(cnt);

    }

}
