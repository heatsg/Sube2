package org.sube.project.accounts;

import org.json.JSONException;
import org.json.JSONObject;
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
    private final UserType userType;
    private final boolean status;

    public User(String name, String surname, int age, String documentNumber, char gender, Card card, UserType userType, boolean status) {
        this.id = JSONSube.assignID(ID_TYPE.TRANSACTION);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.card = card;
        this.userType = userType;
        this.status = status;
    }

    public JSONObject userToJSON(){
        JSONObject j=new JSONObject();
        try {
            j.put("id",id);
            j.put("name",name);
            j.put("surname",surname);
            j.put("age",age);
            j.put("documentNumber",documentNumber);
            j.put("gender",gender);
            j.put("Card",card.cardToJSON());
            j.put("UserType",userType);
            j.put("status",status);
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
        }

        return j;
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

    public UserType getUserType() {
        return userType;
    }

    public boolean getStatus() {
        return status;
    }
}
