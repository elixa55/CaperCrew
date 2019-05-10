/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author f
 */
public class Data {

    public final SimpleIntegerProperty id;
    public final SimpleStringProperty name;
    public final SimpleIntegerProperty drive;
    public final SimpleIntegerProperty vehicle;
    public final SimpleIntegerProperty accuracy;
    public final SimpleIntegerProperty weapon;
    public final SimpleIntegerProperty reflex;
    public final SimpleIntegerProperty strategy;
    public final SimpleIntegerProperty charme;
    public final SimpleIntegerProperty pc;
    public final SimpleIntegerProperty speed;
    public final SimpleIntegerProperty slink;
    public final SimpleStringProperty role1;
    public final SimpleStringProperty role2;

    public Data() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        drive = new SimpleIntegerProperty();
        vehicle = new SimpleIntegerProperty();
        accuracy = new SimpleIntegerProperty();
        weapon = new SimpleIntegerProperty();
        reflex = new SimpleIntegerProperty();
        strategy = new SimpleIntegerProperty();
        charme = new SimpleIntegerProperty();
        pc = new SimpleIntegerProperty();
        speed = new SimpleIntegerProperty();
        slink = new SimpleIntegerProperty();
        role1 = new SimpleStringProperty();
        role2 = new SimpleStringProperty();
    }

    public Data(int id, String name, int weapon, int pc, int accuracy, int charme, int slink, int reflex,
            int drive, int strategy, int speed, int vehicle) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.drive = new SimpleIntegerProperty(drive);
        this.vehicle = new SimpleIntegerProperty(vehicle);
        this.accuracy = new SimpleIntegerProperty(accuracy);
        this.weapon = new SimpleIntegerProperty(weapon);
        this.reflex = new SimpleIntegerProperty(reflex);
        this.strategy = new SimpleIntegerProperty(strategy);
        this.charme = new SimpleIntegerProperty(charme);
        this.pc = new SimpleIntegerProperty(pc);
        this.speed = new SimpleIntegerProperty(speed);
        this.slink = new SimpleIntegerProperty(slink);
        this.role1 = null;
        this.role2 = null;
    }

     public Data(int id, String name, int weapon, int accuracy, int vehicle, int pc, int drive, 
             int slink, int charme, int strategy, int reflex, int speed, String role1, String role2) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.drive = new SimpleIntegerProperty(drive);
        this.vehicle = new SimpleIntegerProperty(vehicle);
        this.accuracy = new SimpleIntegerProperty(accuracy);
        this.weapon = new SimpleIntegerProperty(weapon);
        this.reflex = new SimpleIntegerProperty(reflex);
        this.strategy = new SimpleIntegerProperty(strategy);
        this.charme = new SimpleIntegerProperty(charme);
        this.pc = new SimpleIntegerProperty(pc);
        this.speed = new SimpleIntegerProperty(speed);
        this.slink = new SimpleIntegerProperty(slink);
        this.role1 = new SimpleStringProperty(role1);
        this.role2 = new SimpleStringProperty(role2);
    }

    public final int getId() {
        return this.idProperty().get();
    }

    public final void setId(final int id) {
        this.idProperty().set(id);
    }

    public final SimpleIntegerProperty idProperty() {
        return this.id;
    }

    public final SimpleStringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    public final SimpleIntegerProperty driveProperty() {
        return this.drive;
    }

    public final int getDrive() {
        return this.driveProperty().get();
    }

    public final void setDrive(final int drive) {
        this.driveProperty().set(drive);
    }

    public final SimpleIntegerProperty vehicleProperty() {
        return this.vehicle;
    }

    public final int getVehicle() {
        return this.vehicleProperty().get();
    }

    public final void setVehicle(final int vehicle) {
        this.vehicleProperty().set(vehicle);
    }

    public final SimpleIntegerProperty accuracyProperty() {
        return this.accuracy;
    }

    public final int getAccuracy() {
        return this.accuracyProperty().get();
    }

    public final void setAccuracy(final int accuracy) {
        this.accuracyProperty().set(accuracy);
    }

    public final SimpleIntegerProperty weaponProperty() {
        return this.weapon;
    }

    public final int getWeapon() {
        return this.weaponProperty().get();
    }

    public final void setWeapon(final int weapon) {
        this.weaponProperty().set(weapon);
    }

    public final SimpleIntegerProperty reflexProperty() {
        return this.reflex;
    }

    public final int getReflex() {
        return this.reflexProperty().get();
    }

    public final void setReflex(final int reflex) {
        this.reflexProperty().set(reflex);
    }

    public final SimpleIntegerProperty strategyProperty() {
        return this.strategy;
    }

    public final int getStrategy() {
        return this.strategyProperty().get();
    }

    public final void setStrategy(final int strategy) {
        this.strategyProperty().set(strategy);
    }

    public final SimpleIntegerProperty charmeProperty() {
        return this.charme;
    }

    public final int getCharme() {
        return this.charmeProperty().get();
    }

    public final void setCharme(final int charme) {
        this.charmeProperty().set(charme);
    }

    public final SimpleIntegerProperty pcProperty() {
        return this.pc;
    }

    public final int getPc() {
        return this.pcProperty().get();
    }

    public final void setPc(final int pc) {
        this.pcProperty().set(pc);
    }

    public final SimpleIntegerProperty speedProperty() {
        return this.speed;
    }

    public final int getSpeed() {
        return this.speedProperty().get();
    }

    public final void setSpeed(final int speed) {
        this.speedProperty().set(speed);
    }

    public final SimpleIntegerProperty slinkProperty() {
        return this.slink;
    }

    public final int getSlink() {
        return this.slinkProperty().get();
    }

    public final void setslink(final int slink) {
        this.slinkProperty().set(slink);
    }

    public final SimpleStringProperty role1Property() {
        return this.role1;
    }

    public final String getRole1() {
        return this.role1Property().get();
    }

    public final void setRole1(final String role1) {
        this.role1Property().set(role1);
    }

    public final SimpleStringProperty role2Property() {
        return this.role2;
    }

    public final String getRole2() {
        return this.role2Property().get();
    }

    public final void setRole2(final String role2) {
        this.role1Property().set(role2);
    }

    @Override
    public String toString() {
        return "Data{" + "id=" + id + ", name=" + name + ", drive=" + drive + ", vehicle=" + vehicle + ", accuracy=" + accuracy + ", weapon=" + weapon + ", reflex=" + reflex + ", strategy=" + strategy + ", charme=" + charme + ", pc=" + pc + ", speed=" + speed + ", slink=" + slink + ", role1=" + role1 + ", role2=" + role2 + '}';
    }

}
