package org.sube.project.accounts;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.Card;
import org.sube.project.util.ID_TYPE;
import org.sube.project.util.json.JSONSube;

public class User {
    private final int id;
    private String name, surname;
    private String documentNumber;
    private int age;
    private String gender;
    private final Card card;
    private UserType userType;
    private boolean status;
    private UserCredentials userCredentials;

    public User(String name, String surname, int age, String documentNumber, String gender, Card card, UserType userType, boolean status, UserCredentials userCredentials) {
        this.id = JSONSube.assignID(ID_TYPE.TRANSACTION);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.card = card;
        this.userType = userType;
        this.status = status;
        this.userCredentials = userCredentials;
    }
    public User(JSONObject j){
        this.id = j.getInt("id");
        this.name = j.getString("name");
        this.surname = j.getString("surname");
        this.age = j.getInt("age");
        this.documentNumber = j.getString("documentnumber");
        this.gender = j.getString("gender");
        this.card = new Card(j.getJSONObject("Card"));
        this.userType = UserType.valueOf(j.getString("userType"));
        this.status = j.getBoolean("status");
        this.userCredentials = new UserCredentials(j.getJSONObject("userCredentials"));
    }

    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        try {
            j.put("id", id);
            j.put("name", name);
            j.put("surname", surname);
            j.put("age", age);
            j.put("documentNumber", documentNumber);
            j.put("gender", gender);
            j.put("Card", card.toJson());
            j.put("UserType", userType);
            j.put("status", status);
        } catch (JSONException jx) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public UserCredentials getCredentials() {
        return userCredentials;
    }
}