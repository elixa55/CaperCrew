package game;


/**
 *
 * @Fodor Edit
 */
public class MasterMind {
    private static MasterMind instance = null;
    private static double totalObbligation;
    private double account = 0;
    private double debit;

    public static MasterMind instance() {  // Singleton
        if (instance == null) {
            instance = new MasterMind();
        }
        return instance;
    }

    private MasterMind() {
    }

    public static double getTotalObbligation() {
        return totalObbligation;
    }

    public static void setTotalObbligation(double totalObbligation) {
        MasterMind.totalObbligation = totalObbligation;
    }

    
    
    public static MasterMind getInstance() {
        return instance;
    }
     
    public double getDebit() {
        return debit;
    }

    public void setDebit() {
        this.debit = totalObbligation - this.account;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account += account;
    }

    public void refreshPool() {
        this.account *= 0.95;
    }
    
    public void accountSet(double percent) {    /* egy adott százalékérték (0-1) megadásával, 
                                                az eddigi egyenleg egy részét képezi */
        this.account *=percent;
    }
}
