package game;

import javafx.scene.control.Alert;

/**
 *
 * @Fodor Edit
 */
public class Teammate extends Partner {

    private Role role1;
    private Role role2;
    private double share1;
    private double share2;
    //default constructor
    public Teammate() {
        super();
    }
    // paraméteres konstruktorában már kibővítjük az ősosztályt a szerepkörök megadásával
    public Teammate(Partner p, Role role1, Role role2) {
        this.name = p.getName();
        this.skills = p.getSkills();
        this.role1 = role1;
        this.share1 = role1.getShare();
        if (role2 == null) {
            this.role2 = Role.EMPTY;
            this.share2 = 0.0;
        } else {
            this.role2 = role2;
            this.share2 = role2.getShare();
        }
    }

    public double getShare1() {
        return share1;
    }

    public void setShare1(double share) {
        this.share1 = share;
    }

    public void setShare2(double share) {
        this.share2 = share;
    }

    public double getShare2() {
        return share2;
    }

    public Role getRole1() {
        return role1;
    }

    public void setRole1(Role role1) {
        this.role1 = role1;
    }

    public Role getRole2() {
        return role2;
    }

    public void setRole2(Role role2) {
        this.role2 = role2;
    }

    @Override
    public String toString() {
        return "" + super.toString() + "Teammate{" + "role1=" + role1 + ", role2=" + role2 + ", share1=" + share1 + ", share2=" + share2 + '}';
    }

}
