package com.mycompany.uis;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;

public class OffresEmplois extends Form{
	public OffresEmplois() {
		Toolbar tb=getToolbar();
		setLayout(new FlowLayout(CENTER,CENTER));
		setTitle("Offres d'emploi");
		Form previous = Display.getInstance().getCurrent();
		tb.setBackCommand("", e -> previous.showBack());
	}
}
