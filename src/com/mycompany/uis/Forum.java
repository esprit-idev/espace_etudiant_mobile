package com.mycompany.uis;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Display;

import com.codename1.ui.Form;
import com.codename1.ui.Label;

import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.entities.Club;
import com.mycompany.services.ClubService;
import com.mycompany.utils.Static;
import java.util.ArrayList;

public class Forum extends Form {

    public Forum() {
        Toolbar tb = getToolbar();
        setLayout(new FlowLayout(CENTER, CENTER));
        setTitle("Forum");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());

       

    }
}
