package com.mycompany.uis;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

public class CentrePartage extends Form{
	public CentrePartage() {
		Container cnt = new Container(BoxLayout.y());
		Toolbar tb=getToolbar();
		setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("Centre de partage");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
		FloatingActionButton addDoc_btn = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
		addDoc_btn.addActionListener(e -> new AddDoc().show());
		Container flow_btn=addDoc_btn.bindFabToContainer(cnt, Component.RIGHT, Component.BOTTOM);
		add(flow_btn);
		//Container flow_btn=addDoc_btn.bindFabToContainer(cnt);
	//mainForm.add(fab.bindFabToContainer(MasterContainer));	
		
      
	}
}

