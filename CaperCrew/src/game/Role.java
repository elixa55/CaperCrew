package game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author f
 */
public enum Role { /* szerepkörhöz tartozó alaprészesedés, próbatípusra való jogosultság megadása*/
    PARTNER_IN_CRIME(0.1, new String[]{"strategy"}), 
    DRIVER(0.04, new String[]{"drive", "vehicle"}),
    GUNMAN(0.03, new String[]{"weapon", "accuracy", "reflex"}),
    COORDINATOR(0.05, new String[]{"strategy", "reflex"}),
    HACKER(0.05, new String[]{"pc"}),
    DISTRACTION(0.04, new String[]{"accuracy", "charme"}),
    GADGET_GUY(0.06, new String[]{"slink", "speed", "pc"}),
    BURGLAR(0.05, new String[]{"slink", "speed"}),
    CON_MAN(0.05, new String[]{"charme"}),
    EMPTY(0.0, new String[]{""});
    
    public final static ObservableList<String> ROLES = FXCollections.observableArrayList(
                Role.PARTNER_IN_CRIME.toString(),
                Role.DRIVER.toString(),
                Role.GUNMAN.toString(),
                Role.COORDINATOR.toString(),
                Role.HACKER.toString(),
                Role.DISTRACTION.toString(),
                Role.GADGET_GUY.toString(),
                Role.BURGLAR.toString(), 
                Role.CON_MAN.toString(),
                Role.EMPTY.toString());
    
    private double share;
    private String[] skills;

    Role(double share, String[] skills) {
        this.share = share;
        this.skills = skills;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

};
