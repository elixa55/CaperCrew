package game;

import capercrew.Music;
import static capercrew.ViewController.nf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.scene.control.Alert;

/**
 *
 * @Fodor Edit
 */
public class Heist {

    private static String[] trials = {
        "accuracy",
        "weapon",
        "reflex",
        "strategy",
        "charme",
        "pc",
        "speed",
        "slink"
    };

    private Map<String, Integer> trial;
    private double takings;
    private static int instance = 0;
    private String textMaxTakings;
    private String textTrials;

    public Heist() {
        instance++;
        if (instance > 20) {
            message("GAME OVER! EL MATADOR TE ESTAN BUSCANDO...");
            Music music = new Music();
            music.playSound("src/matador_end.wav");
            System.exit(1);
        } else {
            List<String> permutationList = permutation();
            Map<String, Integer> skillMap = new HashMap<>();
            Random random = new Random();
            int generator = random.nextInt((1000 - 100) + 1) + 100;
            this.takings = (double) generator;
            int eventual1 = random.nextInt(100) + 1;
            int eventual2 = random.nextInt(100) + 1;
            if (eventual1 < 25) {
                permutationList.add("drive");
            }
            if (eventual2 < 25) {
                permutationList.add("vehicle");
            }
            for (String s : permutationList) {
                int temp = random.nextInt((125 - 25) + 1) + 25;
                skillMap.put(s, temp);
            }
            this.trial = skillMap;
        }
    }

    public static List<String> permutation() {
        List<String> listTrials = Arrays.asList(trials);
        List<String> list = new ArrayList<>();
        Collections.shuffle(listTrials);
        int size = (int) (Math.random() * (8 - 4 + 1) + 4);
        for (int i = 0; i < size; i++) {
            list.add(listTrials.get(i));
        }
        return list;
    }

    public static String[] getTrials() {
        return trials;
    }

    public static void setTrials(String[] trials) {
        Heist.trials = trials;
    }

    public Map<String, Integer> getTrial() {
        return trial;
    }

    public void setTrial(Map<String, Integer> trial) {
        this.trial = trial;
    }

    public static int getInstance() {
        return instance;
    }

    public double getTakings() {
        return takings;
    }

    public void setTakings(double takings) {
        this.takings = takings;
    }

    public void takingsSet(double rate) {
        this.takings *= rate;
    }

    public String getTextMaxTakings() {
        String text1 = "" + this.getInstance() + "th HEIST" + "\n" + "The most obtainable\n" + "takings: " + nf.format((int) this.getTakings()) + "\n\n";
        return text1;
    }

    public String getTextTrials() {
        String text2 = "";
        for (Map.Entry<String, Integer> i : this.trial.entrySet()) {
            text2 += i.getKey() + "\t\t" + i.getValue() + "\n";
        }
        return text2;
    }

    private void message(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("INFO");
        alert.setContentText(text);
        alert.showAndWait();
    }

}
