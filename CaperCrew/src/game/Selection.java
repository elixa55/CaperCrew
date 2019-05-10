package game;

/**
 *
 * @author f
 */
public class Selection {

    private int id;
    private Role role1;
    private Role role2;

    public Selection(int id, Role role1, Role role2) {
        this.id = id;
        this.role1 = role1;
        this.role2 = role2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Selection{" + "id=" + id + ", role1=" + role1 + ", role2=" + role2 + '}';
    }

}
