/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Effects;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.util.Resources;
/**
 *
 * @author Eslem Nabitt
 */
public class SplashScreen extends com.codename1.ui.Form {
    
      public SplashScreen() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    public SplashScreen(Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        int size = Display.getInstance().convertToPixels(0.5f);
        Image progress = Effects.dropshadow(resourceObjectInstance.getImage("progress.png"), 10, 70, size, size);
        gui_Infinite_Progress_1.setAnimation(progress);
        gui_Infinite_Progress_1.setAngleIncrease(1);
        Image logoImage = Effects.dropshadow(resourceObjectInstance.getImage("logo.png"), 10, 70, size, size);
        gui_Label_1.setIcon(logoImage);
        UITimer.timer(2000, false, this, () -> new Login().show());
    }

//-- DON'T EDIT BELOW THIS LINE!!!
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.components.InfiniteProgress gui_Infinite_Progress_1 = new com.codename1.components.InfiniteProgress();
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BorderLayout());
        setUIID("Splash");
        setTitle("");
        setName("SplashForm");
        ((com.codename1.ui.layouts.BorderLayout)getLayout()).setCenterBehavior(com.codename1.ui.layouts.BorderLayout.CENTER_BEHAVIOR_CENTER);
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Container_2);
        gui_Container_2.setName("Container_2");
        ((com.codename1.ui.layouts.FlowLayout)gui_Container_2.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
        gui_Container_2.addComponent(gui_Infinite_Progress_1);
        gui_Infinite_Progress_1.setName("Infinite_Progress_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_2.setName("Container_2");
        ((com.codename1.ui.layouts.FlowLayout)gui_Container_2.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
        gui_Label_1.setName("Label_1");
        gui_Label_1.setIcon(resourceObjectInstance.getImage("logo.png"));
        gui_Container_1.setName("Container_1");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
}
