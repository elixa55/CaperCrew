package capercrew;

import game.Data;
import game.Heist;
import game.MasterMind;
import game.Partner;
import game.Role;
import game.Teammate;
import game.Selection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;

/**
 *
 * @Fodor Edit
 */
public class ViewController implements Initializable {

    public static List<Teammate> staticPoolList = new ArrayList<>(10);
    public static List<Data> staticDataList = new ArrayList<>(10);
    public static List<Teammate> staticActualList = new ArrayList<>();
    public static List<Data> staticActualDataList = new ArrayList<>();
    public static Heist staticHeist = null; // mindig a statikus Heist változóba töltődik az aktuális példány
    public static MasterMind master = MasterMind.instance();
    public static ObservableList<Data> dataPool = FXCollections.observableArrayList();
    public static ObservableList<Data> dataActual;
    public static List<Selection> selectItem = new ArrayList<>();
    public static Map<String, Integer> actualRoleNumbers = new HashMap<>();
    public static Map<Teammate, Integer> crewInJail = new HashMap<>();
    public static List<Teammate> releasedFromJail = new ArrayList<>();
    public static NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    final public static Map<String, Integer> maxRoleNumbers = new HashMap<>();

    static {  // statikus blokk a választható szerepkörök maximalizálása miatt
        maxRoleNumbers.put("PARTNER_IN_CRIME", 1);
        maxRoleNumbers.put("DRIVER", 2);
        maxRoleNumbers.put("GUNMAN", 4);
        maxRoleNumbers.put("COORDINATOR", 1);
        maxRoleNumbers.put("HACKER", 1);
        maxRoleNumbers.put("DISTRACTION", 2);
        maxRoleNumbers.put("GADGET_GUY", 2);
        maxRoleNumbers.put("BURGLAR", 2);
        maxRoleNumbers.put("CON_MAN", 2);
        actualRoleNumbers.put("PARTNER_IN_CRIME", 0);
        actualRoleNumbers.put("DRIVER", 0);
        actualRoleNumbers.put("GUNMAN", 0);
        actualRoleNumbers.put("COORDINATOR", 0);
        actualRoleNumbers.put("HACKER", 0);
        actualRoleNumbers.put("DISTRACTION", 0);
        actualRoleNumbers.put("GADGET_GUY", 0);
        actualRoleNumbers.put("BURGLAR", 0);
        actualRoleNumbers.put("CON_MAN", 0);
    }
    ;

    
    @FXML
    private ComboBox<String> cb1;
    @FXML
    private ComboBox<String> cb2;
    @FXML
    private Label label;
    @FXML
    private TextArea heistArea1;
    @FXML
    private TextArea heistArea2;
    @FXML
    private Button generateTotal;
    @FXML
    private Button reset;
    @FXML
    private Button start;
    @FXML
    private Button generateHalf;
    @FXML
    private Pane PoolPane;
    @FXML
    private Pane ChoosePane;
    @FXML
    private Pane HeistPane;
    @FXML
    private TextField account;
    @FXML
    private TextField obbligation;
    @FXML
    private TextField totalDebit;
    @FXML
    private TextField counter;

    @FXML
    private TableView table;
    @FXML
    private TableColumn<Data, Number> id;
    @FXML
    private TableColumn<Data, String> name;
    @FXML
    private TableColumn<Data, Number> drive;
    @FXML
    private TableColumn<Data, Number> vehicle;
    @FXML
    private TableColumn<Data, Number> accuracy;
    @FXML
    private TableColumn<Data, Number> weapon;
    @FXML
    private TableColumn<Data, Number> reflex;
    @FXML
    private TableColumn<Data, Number> strategy;
    @FXML
    private TableColumn<Data, Number> charme;
    @FXML
    private TableColumn<Data, Number> pc;
    @FXML
    private TableColumn<Data, Number> speed;
    @FXML
    private TableColumn<Data, Number> slink;

    @FXML
    private TableView tableActual;
    @FXML
    private TableColumn<Data, Number> idActual;
    @FXML
    private TableColumn<Data, String> nameActual;
    @FXML
    private TableColumn<Data, Number> driveActual;
    @FXML
    private TableColumn<Data, Number> vehicleActual;
    @FXML
    private TableColumn<Data, Number> accuracyActual;
    @FXML
    private TableColumn<Data, Number> weaponActual;
    @FXML
    private TableColumn<Data, Number> reflexActual;
    @FXML
    private TableColumn<Data, Number> strategyActual;
    @FXML
    private TableColumn<Data, Number> charmeActual;
    @FXML
    private TableColumn<Data, Number> pcActual;
    @FXML
    private TableColumn<Data, Number> speedActual;
    @FXML
    private TableColumn<Data, Number> slinkActual;
    @FXML
    private TableColumn<Data, String> role1Column;
    @FXML
    private TableColumn<Data, String> role2Column;

    private void initRoleComboboxes() {
        /*selection of the type of roles*/
        cb1.setItems(Role.ROLES);
        cb2.getItems().addAll(Role.ROLES);
    }

    /*convert Teammate -> Data, a <Teammate> típusú konténert táblázatban megjeleníthető formátumra*/
    @FXML
    private List<Data> teammateToData(List<Teammate> a) {
        List<Data> b = new ArrayList<>();
        int j, l = 0;
        int[] array = new int[10];
        for (Partner p : a) {
            j = 0;
            for (Integer k : p.getSkills().values()) {
                array[j] = k;
                j++;
            }
            Data d = new Data(l, p.getName(), array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9]);
            b.add(d);
            l++;
        }
        return b;
    }

    /* staticPoolList konténterbe helyezi a rablásokhoz kiválasztható 10 lehetséges játékost képességeikkel
    csak a játék elején elérhető ez a gomb, ekkor tetszőleges számban frissíthető ingyen*/
    @FXML
    private void generatePool(ActionEvent event) {
        if (master.getAccount() == 0 && master.getDebit() == 0) {
            obbligation.setText(nf.format((int) MasterMind.getTotalObbligation()));
        }
        List<Teammate> actualList = new ArrayList<>(10);
        List<Data> actualDataList = new ArrayList<>(10);
        actualList.add(new Teammate());
        for (int i = 1; i < 10; i++) {
            Teammate team = new Teammate(new Partner(), Role.EMPTY, Role.EMPTY);
            actualList.add(team);
            for (Teammate t : actualList) {
                if (t.getName().equals(team.getName())) {
                    team = new Teammate();
                }
            }
        }
        dataPool = FXCollections.observableArrayList(); // observable list tölthető be a táblázatba
        actualDataList = teammateToData(actualList);
        dataPool.addAll(actualDataList);    // amihez csak Data típusú elemek adhatók
        table.setEditable(true);
        table.setItems(dataPool);   // Pool táblázatot feltölti ezen lista elemeivel
        setPoolTable();
        staticPoolList = actualList;
        staticDataList = actualDataList;
        account.getText();
    }

    /* a játék során bármikor újra frissíthető a játékos lista fele (5 db),
    a kiválasztás véletlenszerű, az aktuális számlaegyenleg 5%-a ellenében*/
    @FXML
    private void generateHalfPool(ActionEvent event) {
        master.refreshPool(); // levonja az 5%-ot
        master.setDebit();  // hozzáigazítja a tartozás összegét is
        account.setText(String.valueOf((int) master.getAccount())); // megjeleníti a felső szövegmezőben az egyenlegeket
        obbligation.setText(String.valueOf((int) master.getDebit()));
        List<Teammate> actualList = new ArrayList<>(10);
        List<Data> actualDataList = new ArrayList<>(10);
        TreeSet<Integer> selected = new TreeSet<>();
        Random random = new Random();
        int i = 0;
        while (i < 5) {
            int j = random.nextInt(9);
            if (!selected.contains(j)) {
                selected.add(j);
                i++;
            }
        }
        for (int j = 1; j <= 5; j++) {
            Teammate team = new Teammate(new Partner(), Role.EMPTY, Role.EMPTY);
            actualList.add(team);
            for (Teammate t : staticPoolList) {
                if (t.getName().equals(team.getName())) {
                    team = new Teammate();
                }
            }
        }
        int j = 0;
        for (Integer number : selected) {
            staticPoolList.remove(j);
            staticPoolList.add(number, actualList.get(j));
            j++;
        }
        dataPool.clear();   // a régi táblázat teljes törlése
        staticDataList.clear();
        staticDataList = teammateToData(staticPoolList);
        dataPool.addAll(staticDataList);
        table.setEditable(true);
        table.setItems(dataPool);   // az új táblázat feltöltése (5 új elem, 5 régi)
        setPoolTable();
        account.getText();
    }

    /*a Start gomb megnyomásával kezdhető a játék,
    azaz klikkeléssel kiválasztható(k) az aktuális rabláshoz a játékos(ok) szerepkör(eik) megadásával
    elvárt sorrend tehát: rablás generálás, pool generálás, majd Start gomb megnyomása*/
    @FXML
    private void startGame(ActionEvent e) {
        generateTotal.setVisible(false);    // már nem generálható teljes játékoslista többé a játék folyamán
        start.setVisible(false);
        start.disarm();
        obbligation.setText(nf.format(MasterMind.getTotalObbligation()));
        getValueFromPool(); // the mates from the pool can be selectable to the staticActualList container
        Music music = new Music();
        music.playSound("src/matador_start.wav");
    }

    /*a teljes játékoslista táblázat mezőinek aktualizálása*/
    private void setPoolTable() {
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        drive.setCellValueFactory(cellData -> cellData.getValue().driveProperty());
        vehicle.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());
        accuracy.setCellValueFactory(cellData -> cellData.getValue().accuracyProperty());
        weapon.setCellValueFactory(cellData -> cellData.getValue().weaponProperty());
        reflex.setCellValueFactory(cellData -> cellData.getValue().reflexProperty());
        strategy.setCellValueFactory(cellData -> cellData.getValue().strategyProperty());
        charme.setCellValueFactory(cellData -> cellData.getValue().charmeProperty());
        pc.setCellValueFactory(cellData -> cellData.getValue().pcProperty());
        speed.setCellValueFactory(cellData -> cellData.getValue().speedProperty());
        slink.setCellValueFactory(cellData -> cellData.getValue().slinkProperty());
        table.setItems(dataPool);
    }

    /*az aktuális rabláshoz kiválasztott játékoslista táblázat mezőinek aktualizálása*/
    private void setActualTable() {
        nameActual.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        driveActual.setCellValueFactory(cellData -> cellData.getValue().driveProperty());
        vehicleActual.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());
        accuracyActual.setCellValueFactory(cellData -> cellData.getValue().accuracyProperty());
        weaponActual.setCellValueFactory(cellData -> cellData.getValue().weaponProperty());
        reflexActual.setCellValueFactory(cellData -> cellData.getValue().reflexProperty());
        strategyActual.setCellValueFactory(cellData -> cellData.getValue().strategyProperty());
        charmeActual.setCellValueFactory(cellData -> cellData.getValue().charmeProperty());
        pcActual.setCellValueFactory(cellData -> cellData.getValue().pcProperty());
        speedActual.setCellValueFactory(cellData -> cellData.getValue().speedProperty());
        slinkActual.setCellValueFactory(cellData -> cellData.getValue().slinkProperty());
        role1Column.setCellValueFactory(cellData -> cellData.getValue().role1Property());
        role2Column.setCellValueFactory(cellData -> cellData.getValue().role2Property());
        tableActual.setItems(dataActual);
    }

    /*csapattag beválasztása klikkeléssel*/
    @FXML
    private void getValueFromPool() {  //a pool listából kiválasztott csapatlistát készít
        table.setOnMouseClicked(e -> {
            boolean noEmpty = false;  // igaz ha role1 nem üres string
            boolean check = false;  // igaz, ha van ismétlődés, azonos nevű játékos
            boolean roleSelected = false; // a kulcsa a fgv-nek, akkor igaz, ha valamiért nem lehet beválasztani a választott elemet
            boolean roleCheck1 = false; // igaz, ha ki lehetett választani az adott szerepet
            boolean roleCheck2 = false;
            boolean overPassedRole1 = false; //igaz, ha a szerepkör már elérte a maximumát -> nem lesz választható
            boolean overPassedRole2 = false;
            table.setStyle("-fx-selection-bar: red;");
            int selectedId = table.getSelectionModel().getSelectedIndex();  // beazonosítás ID alapján
            String role1 = cb1.getValue(), role2 = cb2.getValue();
            Role r1 = null;
            Role r2 = null;
            if (role1 == null || role1.equals("") || role1.equals(String.valueOf(r1.EMPTY))) { // az 1. szerepkört mindenképpen ki kell választani
                message("Choose at least the first role.");
                roleSelected = true;
            } else {    // nincs választott 2. szerepkör
                if (role2 == null || role2.equals("") || role2.equals(String.valueOf(r2.EMPTY))) {
                    noEmpty = true;
                    for (Map.Entry<String, Integer> max : maxRoleNumbers.entrySet()) {
                        for (Map.Entry<String, Integer> actual : actualRoleNumbers.entrySet()) {
                            if (role1.equals(max.getKey()) && role1.equals(actual.getKey())) {
                                if (max.getValue() > actual.getValue()) {
                                    actualRoleNumbers.merge(role1, 1, Integer::sum);
                                    r1 = Role.valueOf(role1);
                                    roleCheck1 = true;
                                    roleCheck2 = true;
                                    r2 = Role.EMPTY;
                                } else if (max.getValue() == actual.getValue()) {
                                    overPassedRole1 = true;
                                    roleCheck1 = false;
                                    roleCheck2 = false;
                                    roleSelected = true;
                                } else {
                                    roleCheck1 = false;
                                    roleCheck2 = false;
                                    roleSelected = true;
                                }
                            }
                        }
                    }
                } else {    // 1. szerepkör adott, 2. szerepkör null vagy van értéke
                    noEmpty = true;
                    for (Map.Entry<String, Integer> max : maxRoleNumbers.entrySet()) { // 1. szerep még max. alatt van?
                        for (Map.Entry<String, Integer> actual : actualRoleNumbers.entrySet()) {
                            if (role1.equals(max.getKey()) && role1.equals(actual.getKey())) {
                                if (max.getValue() > actual.getValue()) {
                                    actualRoleNumbers.merge(role1, 1, Integer::sum);
                                    r1 = Role.valueOf(role1);
                                    roleCheck1 = true;
                                } else if (max.getValue() == actual.getValue()) {
                                    overPassedRole1 = true;
                                    roleCheck1 = false;
                                    roleSelected = true;
                                } else {
                                    roleSelected = true;
                                    roleCheck1 = false;
                                }
                            }
                        }
                    }
                    for (Map.Entry<String, Integer> max : maxRoleNumbers.entrySet()) { // 2. szerep még max. alatt van?
                        for (Map.Entry<String, Integer> actual : actualRoleNumbers.entrySet()) {
                            if (role2.equals(max.getKey()) && role2.equals(actual.getKey())) {
                                if (max.getValue() > actual.getValue()) {
                                    actualRoleNumbers.merge(role2, 1, Integer::sum);
                                    r2 = Role.valueOf(role2);
                                    roleCheck2 = true;
                                } else if (max.getValue() == actual.getValue()) {
                                    overPassedRole2 = true;
                                    roleCheck2 = false;
                                    roleSelected = true;
                                } else {
                                    roleCheck2 = false;
                                    roleSelected = true;
                                }
                            }
                        }
                    }
                }
                if (!roleSelected) {   // ha semmi kizáró oka nem volt a kiválasztásnak, legalábbis idáig :-)
                    if (noEmpty && roleCheck1 && roleCheck2) {
                        selectItem.add(new Selection(selectedId, r1, r2));
                        Data dActual = new Data();  // akkor Data formátumú osztállyá alakítja
                        dActual = new Data(
                                staticDataList.get(selectedId).getId(),
                                staticDataList.get(selectedId).getName(),
                                staticDataList.get(selectedId).getWeapon(),
                                staticDataList.get(selectedId).getAccuracy(),
                                staticDataList.get(selectedId).getVehicle(),
                                staticDataList.get(selectedId).getPc(),
                                staticDataList.get(selectedId).getDrive(),
                                staticDataList.get(selectedId).getSlink(),
                                staticDataList.get(selectedId).getCharme(),
                                staticDataList.get(selectedId).getStrategy(),
                                staticDataList.get(selectedId).getReflex(),
                                staticDataList.get(selectedId).getSpeed(), role1, role2);
                        for (Data data : staticActualDataList) {    // megnézi szerepel e már benne azonos nevű
                            if (data.getName().equals(dActual.getName())) {
                                check = true;
                            }
                        }

                        if (!check) {   // ha nincs ismétlődés, tényleg beválasztható, és listához hozzáadható
                            staticActualDataList.add(dActual);
                            int size = staticActualDataList.size();
                            String text = "The " + staticPoolList.get(selectedId).getName() + " is engaged for the next heist.\n"
                                    + "You've already selected " + size + " person(s) for the crew.";
                            message(text);
                        } else if (check) {     // ha van ismétlődés, akkor nem tekinti szerepkörnövelőnek a választást
                            message("This teammate has already been selected,\nchoose an other.");
                            for (Map.Entry<String, Integer> entry : actualRoleNumbers.entrySet()) {
                                if (role1.equals(entry.getKey())) {
                                    actualRoleNumbers.merge(entry.getKey(), -1, Integer::sum);
                                }
                                if (role2.equals(entry.getKey())) {
                                    actualRoleNumbers.merge(entry.getKey(), -1, Integer::sum);
                                }
                            }
                        }
                    }
                } else {  // nem választható a szerep, nem adta hozzá actualList-hez, kilép
                    message("Role(s) is/are not selectable again for this heist.");
                    if (overPassedRole1 && overPassedRole2) { // a szerepkör növekedést visszaállítja 
                    } else if (overPassedRole1) {
                        for (Map.Entry<String, Integer> entry : actualRoleNumbers.entrySet()) {
                            if (role2.equals(entry.getKey())) {
                                actualRoleNumbers.merge(entry.getKey(), -1, Integer::sum);
                            }
                        }
                    } else {
                        for (Map.Entry<String, Integer> entry : actualRoleNumbers.entrySet()) {
                            if (role1.equals(entry.getKey())) {
                                actualRoleNumbers.merge(entry.getKey(), -1, Integer::sum);
                            }
                        }
                    }

                }

            }
        });
    }

    /*új rablást generál, véletlenszerűen generált próbkat hoz létre, és az aktuális max. elérhető zsákmány összegét*/
    @FXML
    public void generateHeist(ActionEvent event) {
        String text1 = "", text2 = "";
        Heist heist = new Heist();
        heistArea1.setText(heist.getTextMaxTakings());
        heistArea2.setText(heist.getTextTrials());
        counter.setText(String.valueOf(heist.getInstance()));
        heistArea1.setVisible(true);
        heistArea2.setVisible(true);
        counter.setVisible(true);
        staticHeist = heist; // az aktuális próba statikussá tétele, hogy mindenki tudja használni
    }

    /*egy lezárult rablás után, alaphelyzetbe állít, 
    azaz új rabláshoz megjeleníti a választható játékoslistát*/
    @FXML
    private void resetAction(ActionEvent event) {
        if (master.getAccount() == 0 && master.getDebit() == 0) {
            obbligation.setText(nf.format((int) MasterMind.getTotalObbligation()));
        }
        List<Teammate> actualList = new ArrayList<>(10);
        List<Data> actualDataList = new ArrayList<>(10);
        PoolPane.setVisible(true);   // új rabláskori alaphelyzetbe: Pool táblázat megjelenítése
        if (master.getAccount() > 0) {   // a fél Pool újragenerálása csak akkor elérhető, ha a számlaegyenleg nem zero
            generateHalf.setVisible(true);
        }
        ChoosePane.setVisible(false);
        generateTotal.setVisible(false);  // teljes játékoslistát már nem tud generálni
        setPoolTable();
        cb1.setValue("");
        cb2.setValue("");
        account.getText();
        obbligation.getText();
        if (staticPoolList.size() < 10) {   // halottak, vagy börtönviseltek helyett újakat hoz létre, 10-re egészíti ki
            int difference = 10 - staticPoolList.size();
            for (int j = 1; j <= difference; j++) {
                Teammate team = new Teammate(new Partner(), Role.EMPTY, Role.EMPTY);
                actualList.add(team);
                for (Teammate t : staticPoolList) {
                    if (t.getName().equals(team.getName())) {
                        team = new Teammate();
                    }
                }
            }
            staticPoolList.addAll(actualList);
            dataPool.clear();
            staticDataList.clear();
            staticDataList = teammateToData(staticPoolList);
            dataPool.addAll(staticDataList);
            table.setItems(dataPool);
            setPoolTable();
            Random random = new Random();
            int number = 0;
            int possibility = random.nextInt(10) + 1;
            if (!crewInJail.isEmpty()) {    // börtönbeRaboskodók listáját elemzi, ha adott rab értéke 0, akkor visszatérhet
                for (Map.Entry<Teammate, Integer> i : crewInJail.entrySet()) {
                    if (i.getValue() == 0) {
                        releasedFromJail.add(number, i.getKey()); // ha visszatérhet törli a listából, és a szabadultak listájába teszi át
                        crewInJail.remove(i);
                        number++;
                    }
                }
                int i = staticPoolList.size() - 1;
                for (Teammate team : releasedFromJail) {
                    Boolean check = true;
                    String name = team.getName();
                    message("Maybe " + name + " could be released from the jail.");
                    for (Teammate t : staticPoolList) {
                        if (t.getName().equals(team.getName())) {
                            check = false;
                        }
                    }
                    if (possibility <= 8) { // 80%-os eséllyel szabadulhat ki
                        if (select(name) && check) {
                            staticPoolList.remove(i);
                            staticPoolList.add(i, team);
                            i--;
                        }
                    }
                }
                releasedFromJail.clear();   // nullázza a listát, hiszen több lehetősége nincs visszatérni
            }
            dataPool.clear();
            staticDataList.clear();
            staticDataList = teammateToData(staticPoolList);
            dataPool.addAll(staticDataList);
            table.setItems(dataPool);
            setPoolTable();
            account.getText();
        }
        /* automatikusan generál új rablás próbákat, 
        a játékosnak csak az elsőt kellett, 
        vagy ha újat szeretne, de az a 20-ba beleszámít*/
        Heist heist = new Heist();
        heistArea1.setText(heist.getTextMaxTakings());
        heistArea2.setText(heist.getTextTrials());
        counter.setText(String.valueOf(heist.getInstance()));
        staticHeist = heist;
        staticActualList.clear();
        staticActualDataList.clear();
    }

    /*külön ablak a próba eredményeinek megjelenítésére*/
    @FXML
    private void trialResult(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RESULT OF HEIST");
        alert.setContentText(text);
        alert.showAndWait();
    }

    /*játékosok kiválasztása az aktuális próbához */
    @FXML
    private void acceptTeam(ActionEvent e) {
        for (Selection s : selectItem) {
            staticActualList.add(new Teammate(staticPoolList.get(s.getId()), s.getRole1(), s.getRole2()));
        }
        //kiiratás, fejlesztéshez
        int k = 0;
        System.out.println("STATIKUS ACTUALIS LIST");
        for (Teammate team : staticActualList) {
            System.out.println(k + ". elem " + team);
            k++;
        }
        k = 0;
        System.out.println("STATIKUS POOLLIST PROBA");
        for (Teammate team : staticPoolList) {
            System.out.println(k + ". elem" + team);
            k++;
        }

        k = 0;
        System.out.println("STATIKUS DATALIST PROBA");
        for (Data team : staticDataList) {
            System.out.println(k + ". elem" + team);
            k++;
        }
        k = 0;
        System.out.println("AKTUALIS DATALIST PROBA");
        for (Data team : staticActualDataList) {
            System.out.println(k + ". elem" + team);
            k++;
        }

        Heist heist = staticHeist;  // az aktuális statikus változó értéke beletöltődik az aktuális példányba
        if (heist == null) {
            message("You haven't generated a new heist yet.");
        } else if (staticActualDataList.isEmpty()) {
            message("You haven't selected any element.");
        } else {
            message("Your actual Heist Team is composed.");
            dataActual = FXCollections.observableArrayList(); //kiválasztott csapat observablelist
            Set<Integer> set = new TreeSet<>();
            PoolPane.setVisible(false);   //megjelenik az alsó Pane, ahol az aktuális csapatlista van
            ChoosePane.setVisible(true);
            generateTotal.setVisible(false);
            heistArea1.setVisible(true);
            heistArea2.setVisible(true);
            dataActual.addAll(staticActualDataList);
            tableActual.setEditable(true);
            tableActual.setItems(dataActual);
            setActualTable();

            /*
             * **********************************************
             * szerepkörök szerinti buzizások, már megváltoztatja a
             * staticActualListet, majd azokkal számítja ki a próbák pontjait
             * **********************************************/
            
            
            
            
            
            /*1. PARTNER_IN_CRIME - ha ez a választott szerepkör, csak addig minden skill +20 pont. 
            Az aktuális rablásnak az összes próbatípus esetében a megnövelt értékkel számol,
            ha sikeres akkor még ezt is 10%-kal növeli de csak a próba alatt
             */
            Teammate partnerCrime = new Teammate();
            for (Teammate team : staticActualList) {
                for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                    if (team.getRole1().compareTo(Role.PARTNER_IN_CRIME) == 0 || team.getRole2().compareTo(Role.PARTNER_IN_CRIME) == 0) {
                        team.getSkills().merge(entry.getKey(), 20, Integer::sum);
                        partnerCrime = team;
                    }
                }
            }
            /*2. DRIVER: szerencsére nem csinál semmit
            3. GUNMAN: szerencsére nincs kikötés*/
            /*4. COORDINATOR - ha a próba sikertelen, adott skillben 
            legjártasabb társ újravégezheti a próbát - folyt 990.sor*/
            boolean coordinatorRole = false;
            for (Teammate team : staticActualList) {
                if (team.getRole1().compareTo(Role.COORDINATOR) == 0 || team.getRole2().compareTo(Role.COORDINATOR) == 0) {
                    coordinatorRole = true;
                }
            }
            /*5. HACKER: ha nincs computer próba, de van valakinek ilyen szerepe, 
            akkor minden próba skilljét decrementálja 5-tel*/
            boolean hackerRole = false;
            for (Teammate team : staticActualList) {
                if (team.getRole1().compareTo(Role.HACKER) == 0 || team.getRole2().compareTo(Role.HACKER) == 0) {
                    hackerRole = true;
                }
            }
            boolean heistComputer = true;
            for (String s : heist.getTrial().keySet()) {
                if ((s.equals("pc"))) {
                    heistComputer = false;
                }
            }
            if (hackerRole && heistComputer) {
                for (Map.Entry<String, Integer> h : heist.getTrial().entrySet()) {
                    heist.getTrial().merge(h.getKey(), -5, Integer::sum);
                }
            }

            /*6. DISTRACTION - ha nincs charme vagy accuracy próba, akkor az összes
            drive, vehicle vagy weapon próbát könnyíti 7 ponttal
            */
            boolean distractionRole = false;
            for (Teammate team : staticActualList) {
                if (team.getRole1().compareTo(Role.DISTRACTION) == 0 || team.getRole2().compareTo(Role.DISTRACTION) == 0) {
                    distractionRole = true;
                }
            }
            boolean heistCharmeAccuracy = true;
            for (String s : heist.getTrial().keySet()) {
                if ((s.equals("charme") || s.equals("accuracy"))) {
                    heistCharmeAccuracy = false;
                }
            }
            if (distractionRole && heistCharmeAccuracy) {
                for (Map.Entry<String, Integer> h : heist.getTrial().entrySet()) {
                    if (h.getKey().equals("drive") || h.getKey().equals("vehicle") || h.getKey().equals("weapon")) {
                        heist.getTrial().merge(h.getKey(), -7, Integer::sum);
                    }
                }
            }

            /*7. GADGET_GUY - ha nincs computer, speed vagy slink próba, 
            akkor egy véletlenszerűen választottba besegít skilljei 50%-val
            */
            for (Teammate team : staticActualList) { //+7 ha sikerül, tehát legyőzi a heist megfelelő skill értéket
                if (team.getRole1().compareTo(Role.GADGET_GUY) == 0 || team.getRole2().compareTo(Role.GADGET_GUY) == 0) {
                    if (!(heist.getTrial().containsKey("pc") || heist.getTrial().containsKey("speed") || heist.getTrial().containsKey("slink"))) {
                        int i = heist.getTrial().size();
                        Random random = new Random();
                        int temp = random.nextInt(i) + 1;
                        List<String> strList = new ArrayList<>(heist.getTrial().keySet());
                        String generated = strList.get(temp - 1);
                        for (String s : heist.getTrial().keySet()) {
                            if (generated.equals(s)) {
                                team.getSkills().replace(s, team.getSkills().get(generated) / 2 * 3);
                            }
                        }
                    }
                }
            }
            /*8. BURGLAR - nincs speed vagy slink próba, akkor burglarShare változó true lesz, 
            ami majd a társak összrésesedésekori számításban 25%-kal növeli azt master kárára*/
            boolean burglarShare = false;
            boolean burglarRole = false;
            for (Teammate team : staticActualList) {
                if (team.getRole1().compareTo(Role.BURGLAR) == 0 || team.getRole2().compareTo(Role.BURGLAR) == 0) {
                    burglarRole = true;
                }
            }
            boolean heistSpeedSlink = true;
            for (String s : heist.getTrial().keySet()) {
                if ((s.equals("speed") || s.equals("slink"))) {
                    heistSpeedSlink = false;
                }
            }
            if (burglarRole && heistSpeedSlink) {
                burglarShare = true;
            }

            /*9. CON_MAN - ha nincs charme próba, minden társ részesedés csökken 2%-kal*/
            boolean conManRole = false;
            for (Teammate team : staticActualList) {
                if (team.getRole1().compareTo(Role.CON_MAN) == 0 || team.getRole2().compareTo(Role.CON_MAN) == 0) {
                    conManRole = true;
                }
            }
            boolean heistCharme = true;
            for (String s : heist.getTrial().keySet()) {
                if ((s.equals("charme"))) {
                    heistCharme = false;
                }
            }
            if (conManRole && heistCharme) {
                for (Teammate team : staticActualList) {
                    double temp1 = team.getShare1() - 0.02;
                    team.setShare1(temp1);
                    double temp2 = team.getShare2() - 0.02;
                    team.setShare2(temp2);
                }
            }

            /**
             * ************szerepkörök vége**********
             */
            
            
            
            
            /*kiszámítja a részesedéseket, és a pontokat, sikerességet*/
            double rateExperience = 0;
            boolean success = false;
            Set<String> failureTest = new HashSet<>(); // halmazba gyűjti a sikertelen próbákat
            int teamPoints = 0;
            int trialPoints = 0;
            boolean checkBaseShare = true;
            double totalShare = 0.0;
            for (Teammate team : staticActualList) {  //1. szerepkörre vizsgál
                for (int i = 0; i < team.getRole1().getSkills().length; i++) {
                    for (Map.Entry<String, Integer> entryTeam : team.getSkills().entrySet()) {
                        for (Map.Entry<String, Integer> entryTrial : heist.getTrial().entrySet()) {
                            if (entryTeam.getKey().equals(entryTrial.getKey()) && team.getRole1().getSkills()[i].equals(entryTrial.getKey())) {
                                //ahol a próba megnevezése egyezik a társ azon képességével, amire szerepköre feljogosítja
                                if (entryTeam.getValue() > entryTrial.getValue()) {  // ha a társ képességértéke meghaladja a próbájét
                                    trialPoints += entryTrial.getValue();   // beszámítjuk az összpontok közé... rablás próbapontjai
                                    totalShare += team.getShare1();
                                    totalShare += team.getMaxSkill() * 0.001;
                                    checkBaseShare = false;
                                    if (team.equals(partnerCrime)) { // ha a társ PartnerCrime, akkor +10%
                                        double pcPoints = entryTeam.getValue() * 1.1;
                                        teamPoints += (int) pcPoints;
                                    } else {
                                        teamPoints += entryTeam.getValue(); // a társak összesített pontjai
                                    }
                                } else {
                                    trialPoints += entryTrial.getValue();
                                    failureTest.add(entryTrial.getKey()); //sikertelen próbák listájéba behelyezi
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < team.getRole2().getSkills().length; i++) {  //2. szerepkörre vizsgálat
                    for (Map.Entry<String, Integer> entryTeam : team.getSkills().entrySet()) {
                        for (Map.Entry<String, Integer> entryTrial : heist.getTrial().entrySet()) {
                            if (entryTeam.getKey().equals(entryTrial.getKey()) && team.getRole2().getSkills()[i].equals(entryTrial.getKey())) {
                                if (entryTeam.getValue() > entryTrial.getValue()) {
                                    trialPoints += entryTrial.getValue();
                                    totalShare += team.getShare1();
                                    totalShare += team.getMaxSkill() * 0.001;
                                    checkBaseShare = false;
                                    if (team.equals(partnerCrime)) { // ha a társ PartnerCrime, akkor +10%
                                        double pcPoints = entryTeam.getValue() * 1.1;
                                        teamPoints += (int) pcPoints;
                                    } else {
                                        teamPoints += entryTeam.getValue();
                                    }
                                } else {
                                    trialPoints += entryTrial.getValue();
                                    failureTest.add(entryTrial.getKey());
                                }
                            }
                        }
                    }
                }
            }
            for (Teammate team : staticActualList) {    // a próba lezártával, a PartnerCrime szerepkörű játékos pontjainak visszaállítása
                for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                    if (team.equals(partnerCrime)) {
                        team.getSkills().merge(entry.getKey(), -20, Integer::sum);
                        partnerCrime = team;
                    }
                }
            }
            
            
            /*
            ***************************************************************
            *sikertelen próbák hatásai:
            halál - egy véletlenszerűen választott társ meghal
            letartóztatás - egy véletlenszerűen választott társat letartóztatnak,
            6 rablás után 80% eséllyel visszatér, 20% eséllyel áruló lesz: 
            ami master számlájának 30%-os csökkenését vonja maga után - automatikusan
            skillek szerint (ha adott skill szerepel a sikertelen próbák listájában):
            **************************************************************/
            
            /* 1. drive: ha egy db DRIVER van a teljes zsákmány összeg elveszik, 
            ha kettő akkor csak a fele*/
            Teammate deadMan = null;
            Teammate traitor = null;
            int numberDriver = 0;
            for (Teammate team : staticActualList) {
                if (team.getRole1().equals(Role.DRIVER)) {
                    numberDriver++;
                }
                if (team.getRole2().equals(Role.DRIVER)) {
                    numberDriver++;
                }
            }
            for (String s : failureTest) {
                if (s.equals("drive")) {
                    if (numberDriver == 1) {
                        heist.takingsSet(0);
                    }
                    if (numberDriver == 2) {
                        heist.takingsSet(0.5);
                    }
                }
            }
            /*2. vehicle: új weapon próbát generál, 
            ha veszt, akkor teljes zsákmány összeg elvész*/
            int pointWeapon = 0;
            int newWeapon = (int) (Math.random() * (125 - 25 + 1) + 25);
            for (String s : failureTest) {
                if (s.equals("vehicle")) {
                    for (Teammate team : staticActualList) {
                        for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                            if (entry.getKey().equals("weapon")) {
                                pointWeapon += entry.getValue();
                            }
                        }
                    }
                    if (pointWeapon < newWeapon * staticActualList.size()) {
                        heist.takingsSet(0);
                    }
                }
            }
            /*3. accuracy: teljes zsákmányösszeg 20%-a veszik, egy társ halála*/
            for (String s : failureTest) {
                if (s.equals("accuracy")) {
                    heist.takingsSet(0.8);
                    int index = 0;
                    Random random = new Random();
                    int possibility = random.nextInt(10) + 1;
                    Collections.shuffle(staticActualList);
                    if (possibility <= 5) {
                        deadMan = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(deadMan.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + deadMan.getName() + " is dead.");
                    }
                }
            }
            /*4. weapon: teljes zsákmányösszeg 10%-a veszik, 1 halál, 1 börtön 50-50% eséllyel*/
             for (String s : failureTest) {
                if (s.equals("weapon")) {
                    heist.takingsSet(0.9);
                    int index = 0;
                    Random random = new Random();
                    boolean possibility = random.nextBoolean();
                    Collections.shuffle(staticActualList);
                    if (possibility==true) {
                        deadMan = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(deadMan.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + deadMan.getName() + " is dead.");
                    }
                    possibility = random.nextBoolean();
                    if (possibility==true) {
                        traitor = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(traitor.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }

                        crewInJail.put(traitor, 6);
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + traitor.getName() + " was intercepted.\nHe's a possible damn traitor.");
                        int probability = random.nextInt(10) + 1;
                        if (probability < 3) {
                            master.accountSet(0.7);
                        }
                    }
                }
            }
            /*5. reflex: új speed próba, ha sikertelen, akkor halál, és -70% zsákmánynak elveszik*/
            int pointSpeed = 0;
            int newSpeed = (int) (Math.random() * (125 - 25 + 1) + 25);
            for (String s : failureTest) {
                if (s.equals("reflex")) {
                    for (Teammate team : staticActualList) {
                        for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                            if (entry.getKey().equals("speed")) {
                                pointSpeed += entry.getValue();
                            }
                        }
                    }
                    if (pointSpeed < newSpeed * staticActualList.size()) {
                        int index = 0;
                        Random random = new Random();
                        int possibility = random.nextInt(10) + 1;
                        Collections.shuffle(staticActualList);
                        if (possibility <= 5) {
                            deadMan = staticActualList.get(0);
                            for (Teammate t : staticPoolList) {
                                if (t.getName().equals(deadMan.getName())) {
                                    index = staticPoolList.indexOf(t);
                                }
                            }
                            staticActualList.remove(0);
                            staticPoolList.remove(index);
                            message("Bad news. " + deadMan.getName() + " is dead.");
                        }
                        heist.takingsSet(0.3);
                    }
                }
            }
            /*6. strategy: a teljes zsákmányösszeg 25%-a elvész, majd újabb strategy próba*/
            int point = 0;
            for (String s : failureTest) {
                if (s.equals("strategy")) {
                    heist.takingsSet(0.75);
                    int newStrategy = (int) (Math.random() * (125 - 25 + 1) + 25);
                    for (Teammate team : staticActualList) {
                        for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                            if (entry.getKey().equals("strategy")) {
                                point += entry.getValue();
                            }
                        }
                    }
                    if (point > newStrategy * staticActualList.size()) {
                        teamPoints += point;
                    }
                }
            }
            /*7. charme: a Master pénzének 15%-a vész el, aztán új strategy próba*/
            int pointStrategy = 0;
            int newStrategy = (int) (Math.random() * (125 - 25 + 1) + 25);
            for (String s : failureTest) {
                if (s.equals("charme")) {
                    master.accountSet(0.85);
                    for (Teammate team : staticActualList) {
                        for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                            if (entry.getKey().equals("strategy")) {
                                pointStrategy += entry.getValue();
                            }
                        }
                    }
                    if (pointStrategy > newStrategy * staticActualList.size()) {
                        teamPoints += pointStrategy;
                    }
                }
            }
            /*8. computer: a teljes zsákmányösszeg elveszik, 
            mindenkinek a tapasztalata 150%-os növekedésű??*/
            for (String s : failureTest) {
                if (s.equals("pc")) {
                    heist.takingsSet(0);
                    rateExperience = 1.5; // majd állít a tapasztalatoknál jó sokat
                }
            }
            /*9. speed: a teljes zsákmány 30%-a elvész, 1* börtön*/
            for (String s : failureTest) {
                if (s.equals("speed")) {
                    heist.takingsSet(0.7);
                    Random random = new Random();
                    int index = 0;
                    int possibility = random.nextInt(10) + 1;
                    Collections.shuffle(staticActualList);
                    if (possibility <= 5) {
                        traitor = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(traitor.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }
                        crewInJail.put(traitor, 6);
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + traitor.getName() + " was intercepted.\nHe's a possible damn traitor.");
                        possibility = random.nextInt(10) + 1;
                        if (possibility == 5 || possibility == 8) {
                            master.accountSet(0.7);
                        }
                    }
                }
            }
            /*10. slink - 2× börtön*/
            for (String s : failureTest) {
                if (s.equals("slink")) {
                    Random random = new Random();
                    int index = 0;
                    int possibility = random.nextInt(10) + 1;
                    Collections.shuffle(staticActualList);
                    if (possibility <= 5) {
                        traitor = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(traitor.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }
                        crewInJail.put(traitor, 6);
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + traitor.getName() + " was intercepted.\nHe's a possible damn traitor.");
                        possibility = random.nextInt(10) + 1;
                        if (possibility < 3) {
                            master.accountSet(0.7);
                        }
                    }
                    possibility = random.nextInt(10) + 1;
                    if (possibility <= 5) {
                        traitor = staticActualList.get(0);
                        for (Teammate t : staticPoolList) {
                            if (t.getName().equals(traitor.getName())) {
                                index = staticPoolList.indexOf(t);
                            }
                        }
                        crewInJail.put(traitor, 6);
                        staticActualList.remove(0);
                        staticPoolList.remove(index);
                        message("Bad news. " + traitor.getName() + " was intercepted.\nHe's a possible damn traitor.");
                        possibility = random.nextInt(10) + 1;
                        if (possibility < 3) {
                            master.accountSet(0.7);
                        }
                    }
                }
            }
            /*4. Coordinator szerepkör folytatása: egy sikertelen próbát újra generál, 
            ha siker, hozzáadja a csapatpontokhoz*/

            if (!failureTest.isEmpty() && coordinatorRole) {
                Teammate theBestTeammate = null;
                List<String> list = new ArrayList<>(failureTest);
                String failureTrial = list.get(0);
                int max = Integer.MIN_VALUE;
                for (Teammate team : staticActualList) {
                    for (Map.Entry<String, Integer> entry : team.getSkills().entrySet()) {
                        if (entry.getKey().equals(failureTrial)) {
                            if (max > entry.getValue()) {
                                max = entry.getValue();
                                theBestTeammate = team;
                            }
                        }
                    }
                }
                int newTrial = (int) (Math.random() * (125 - 25 + 1) + 25);

                for (Map.Entry<String, Integer> entry : theBestTeammate.getSkills().entrySet()) {
                    if (entry.getKey().equals(failureTrial)) {
                        System.out.println(entry.getKey() + "jelenlegi pontok: " + entry.getValue());
                    } else if (entry.getKey().equals(failureTrial) && newTrial < entry.getValue()) {
                        teamPoints += entry.getValue();
                    }
                  }
            }
            /*megnézem, hogy a BURGLAR változó igaz, ha igen, megnövekszik a társrészesedés negyedével*/
            if (burglarShare) {
                totalShare *= 1.25;
            }
            /*részesedés alapján eldönti, h. kivizsgálja e a próba eredményét
            mivel ha meghaladja a 100%-ot a társak összrészesedése, akkor a rablás nem kivitelezhető*/
            String text = "";
            if (totalShare >= 1) {   //ha nincs 100%, új próbát kell generálni, és ezt elbukta
                text += "The total share is over than the Mastermind's one\n"
                        + "You have to generate a new heist.";
                message(text);
            } 
            /*100% alatti részesedés*/
            else {        
                double increment = (1.0 - totalShare) * heist.getTakings();
                if (teamPoints > trialPoints) {             //sikeres próba esetén, egyenlő nem jó
                    success = true;
                    text += "" + heist.getInstance() + "th HEIST WAS SUCCESSFUL.\n"
                            + "\nYour previous balance of account:\t\t" + nf.format((int) master.getAccount())
                            + "\nYour increment of account:\t\t" + nf.format((int) increment);
                    master.setAccount(increment);
                    master.setDebit();
                } else {                       //sikertelen próba
                    text += "" + heist.getInstance() + "th HEIST WASN'T SUCCESSFUL.\n";
                }
                text += "\nPossible takings:\t\t" + nf.format((int) heist.getTakings());
                text += "\nCrew points:\t\t" + nf.format(teamPoints);
                text += "\nTrial points:\t\t" + nf.format(trialPoints);
                text += "\nMasterMind bank account:\t\t" + nf.format((int) master.getAccount());
                text += "\nTotal share:\t\t" + nf.format(totalShare);
                text += "\nRemaining debit:\t\t" + nf.format((int) master.getDebit());
            }
            trialResult(text);
            if (master.getAccount() >= MasterMind.getTotalObbligation()) {
                message("GONGRATULATION!!\n"
                        + "YOU'VE GOT THE MONEY!!\n"
                        + "YOU ARE SURVIVED!!!!");
                Music music = new Music();
                music.playSound("src/matador_start.wav");
                System.exit(1);
            } else if (heist.getInstance() == 19) {
                message("Vigyázat! Utolsó próba következik, jól gondold át, merre, hogy!");
            }
            account.setText(nf.format((int) master.getAccount()));
            obbligation.setText(nf.format((int) master.getDebit()));

            /*résztvevői tapasztalatgyűjtés minden rablás után:
            sikertelen 1-gyel, sikeres 8-cal, maradandó változások a Poolon*/
            for (Teammate team : staticActualList) { //1-et mindenképpen
                for (Map.Entry<String, Integer> i : team.getSkills().entrySet()) {
                    if (rateExperience != 0) {
                        double temp = i.getValue() * rateExperience;
                        int exp = (int) temp;
                        team.getSkills().replace(i.getKey(), exp);
                    } else {
                        team.getSkills().merge(i.getKey(), 1, Integer::sum);
                    }
                }
            }
            if (rateExperience == 0) {
                for (Teammate team : staticActualList) { //+7 ha sikerül, tehát legyőzi a heist megfelelő skill értéket
                    for (String h : heist.getTrial().keySet()) {
                        for (int i = 0; i < team.getRole1().getSkills().length; i++) {
                            for (Map.Entry<String, Integer> j : team.getSkills().entrySet()) {
                                if (h.equals(team.getRole1().getSkills()[i]) && j.getKey().equals(h) && heist.getTrial().get(team.getRole1().getSkills()[i]) < j.getValue()) {
                                    team.getSkills().merge(j.getKey(), 7, Integer::sum);
                                }
                            }
                        }
                    }
                }
                try {
                    for (Teammate team : staticActualList) { //2. szerepkör +7 pont, ha legyőzi a próba skilljét
                        for (String h : heist.getTrial().keySet()) {
                            for (int i = 0; i < team.getRole2().getSkills().length; i++) {
                                for (Map.Entry<String, Integer> j : team.getSkills().entrySet()) {
                                    if (h.equals(team.getRole2().getSkills()[i]) && j.getKey().equals(h) && heist.getTrial().get(team.getRole2().getSkills()[i]) < j.getValue()) {
                                        team.getSkills().merge(j.getKey(), 7, Integer::sum);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Nullpointer");
                }
            }
            //változások elmentése a PoolListbe
            for (Partner team : staticPoolList) {
                for (Partner actual : staticActualList) {
                    if (team.getName().equals(actual.getName())) {
                        team.setSkills(actual.getSkills());
                    }
                }
            }
            int index = 1;
            staticDataList = teammateToData(staticPoolList);
            dataPool.clear();
            dataPool.addAll(staticDataList);
            table.setEditable(true);
            table.setItems(dataPool);

        } // else ág vége: ha nem üres a staticDataList, tehát volt elemválasztás, és első rablást is generáltunk

        /*VÉGE EGY RABLÁSNAK: nullázni kell az iménti csapatlistát, és az ahhoz tartozó szerepleosztást*/
        for (Map.Entry<String, Integer> i : actualRoleNumbers.entrySet()) {//új rabláshoz nullázzuk a szerepkörszámlálót
            actualRoleNumbers.replace(i.getKey(), 0);
        }
        for (Map.Entry<Teammate, Integer> i : crewInJail.entrySet()) {//visszatérők listája rablásonként csökken
            crewInJail.merge(i.getKey(), -1, Integer::sum);
        }

        selectItem.clear();
        staticActualList.clear();
        staticActualDataList.clear();
    }

    public static void message(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("INFO");
        alert.setContentText(text);
        alert.showAndWait();
    }
    /*játék kezdetén ki kell tölteni a szövegmezőt, 
    amivel megadjuk a veszélyes Mafioso-nak visszafizetendő tartozást ($-ban)*/
    public static String dialog() {  
        TextInputDialog tid = new TextInputDialog();
        String text = "1. Give the amount of your debit!\n"
                + "2. Generate the gamers pool!\n"
                + "3. Generate the first heist!\n"
                + "THEN YOU CAN START THE GAME!!!";
        tid.setTitle("WELCOME!!! YOU'RE THE MASTERMIND");
        tid.setHeaderText(text);
        Optional<String> result = tid.showAndWait();
        if (result.get().equals("")) {
            message("You would have given the amount of debit.\n"
                    + "So start again!");
            System.exit(1);
            return null;
        }
        else if (!result.get().matches("^[0-9]+$")) {
             message("You would have given exclusively numbers.\n"
                    + "So start again!");
             System.exit(1);
            return null;
        }
        else
        return result.get();
    }
    /*akarjuk e, hogy a most szabadult társ visszatérjen, ha OK-t választunk, 
    a visszatérési érték TRUE lesz*/
    private boolean select(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Do you really want to engage " + name + "?\nHe could be a traitor.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else if (result.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initRoleComboboxes(); //meghív -> szerepkör választék megjelenítés
        cb1.setStyle("-fx-selection-bar: red;");
        cb2.setStyle("-fx-selection-bar: red;");
        MasterMind.setTotalObbligation(Double.parseDouble(dialog())); // a játék elején a megadott össztartozást beállítása
    }
}
