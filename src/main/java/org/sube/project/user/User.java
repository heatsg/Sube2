package org.sube.project.user;

import org.sube.project.card.Card;

public class User {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String documentNumber;
    private char gender;
    private Card card;

    public User(int id, String name, String surname, int age, String documentNumber, char gender, Card card) {
        this.id = id;
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
