package org.sube.project.user;

import org.sube.project.card.Card;
import org.sube.project.util.ID_TYPE;
import org.sube.project.util.JSONSube;

public class User {
    private final int id;

    private String name, surname;
    private String documentNumber;
    private int age;
    private char gender;
    private final Card card;

    public User(String name, String surname, int age, String documentNumber, char gender, Card card) {
        this.id = JSONSube.assignID(ID_TYPE.TRANSACTION);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card getCard() {
        return card;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
