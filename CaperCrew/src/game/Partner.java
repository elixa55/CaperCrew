package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @Fodor Edit
 */
public class Partner {

    protected String name;
    protected Map<String, Integer> skills;
    protected int maxSkill;
    /*a Partner ősosztály konstruktorában generálunk 1 és 100 között képesség értékeket
    osztályváltozók tehát név, 10 képességérték, ezek maximuma*/
    public Partner() {
        String temp = "";
        int generator1 = (int) (Math.random() * (90 - 65 + 1) + 65);
        int generator2 = (int) (Math.random() * (90 - 65 + 1) + 65);
        char character1 = (char) generator1;
        char character2 = (char) generator2;
        temp = String.valueOf(character1 + "." + character2 + ".");
        this.name = temp;
        int[] array = new int[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(100) + 1;
            array[i] = number;
        }
        String[] skillList = {
            "weapon", "accuracy", "vehicle", "pc", "drive", "slink", "charme", "strategy",
            "reflex", "speed"};
        skills = new HashMap<>();
        int i = 0;
        while (i < skillList.length) {
            skills.put(skillList[i], array[i]);
            i++;
        }
        maxSkill = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> j : skills.entrySet()) {
            if (j.getValue() > maxSkill) {
                maxSkill = j.getValue();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public int getMaxSkill() {
        return maxSkill;
    }

    public void setMaxSkill(int maxSkill) {
        this.maxSkill = maxSkill;
    }

    @Override
    public String toString() {
        String text = "";
        text += "name = " + name + "\t";
        int k = 0;
        for (Map.Entry<String, Integer> i : skills.entrySet()) {
            if (k < 5) {
                text += "" + i.getKey() + " = " + i.getValue() + "\t";
                k++;
            }
        }
        text += "\n";
        k = 0;
        for (Map.Entry<String, Integer> i : skills.entrySet()) {
            if (k < 5) {
                k++;
            } else {
                text += "" + i.getKey() + " = " + i.getValue() + "\t";
                k++;
            }
        }
        text += "maxSkill = " + maxSkill + "\n";
        return text;
    }

}
