package com.mycompany.uis;

import com.codename1.components.Accordion;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Club;
import com.mycompany.services.ClubService;
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.io.Util;
import com.codename1.util.StringUtil;
import org.jsoup.Jsoup;


public class ClubsList extends Form {

    public ClubsList() throws IOException {
        ArrayList<Club> clubs;
        setScrollableY(true);
        Toolbar tb = getToolbar();
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Club");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        clubs = ClubService.getInstance().getAllClubs();

        for (Club c : clubs) {
            /*SpanLabel s = new SpanLabel();
            s.setText(c.getClubName()
                    + " "
                    + c.getClubCategorie().split("=")[1].replaceAll("}", "")
                    + " "
                    + c.getClubDesc()
                    + " "
                    + c.getClubPic()
                    + " "
                    + c.getClubRespo().split("=")[1].replaceAll("}", "")
            );*/
            String clubID=c.getClubId();
            Label clubName = new Label(c.getClubName().toUpperCase());
            clubName.setAlignment(CENTER);
                
            ImageViewer clubImg = new ImageViewer(Image.createImage("/" + c.getClubPic()));

            Label clubDescTitles = new Label("Categorie: " + StringUtil.replaceAll(Util.split(c.getClubCategorie(), "=")[1], "}", "")
            );

            SpanLabel clubDesc = new SpanLabel(Jsoup.parse(c.getClubDesc()).text());
            clubDesc.getStyle().setMargin(30, 30, 30, 30);
            Label clubRespo = new Label("Contact: "
                    + StringUtil.replaceAll(Util.split(c.getClubRespo(), "=")[1], "}", ""));
            clubRespo.setAlignment(CENTER);

            clubDescTitles.setAlignment(CENTER);
            Button btnConsult = new Button("Consulter");
            btnConsult.addActionListener((ActionListener) (ActionEvent arg0) -> {
                try {
                    new ClubRubrique(clubName.getText() ,c.getClubPic(), clubDesc.getText(),clubID).show();
                } catch (IOException ex) {
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setMessage(ex.toString());
                    status.setExpires(3000);  // only show the status for 3 seconds, then have it automatically clear
                    status.show();
                }
            });

            Accordion accr = new Accordion();

            accr.addContent(
                    clubName,
                    BoxLayout.encloseY(
                            clubImg,
                            clubDescTitles,
                            clubDesc,
                            clubRespo,
                            btnConsult)
            );

            add(accr);

        }

    }

}
