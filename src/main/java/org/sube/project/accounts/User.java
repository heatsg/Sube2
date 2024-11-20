package org.sube.project.accounts;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.util.json.JSONCompatible;

import java.util.Objects;

public class User implements JSONCompatible {
    private String name, surname;
    private String documentNumber;
    private int age;
    private String gender;
    private UserType userType;
    private String password;
    private boolean status;

    public User(String name, String surname, int age, String documentNumber, String gender, UserType userType, boolean status, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.userType = userType;
        this.password = password;
        this.status = status;
    }

    public User(JSONObject j) {
        this.name = j.getString("name");
        this.surname = j.getString("surname");
        this.age = j.getInt("age");
        this.documentNumber = j.getString("documentNumber");
        this.gender = j.getString("gender");
        this.userType = UserType.valueOf(j.getString("userType"));
        this.password = j.getString("password");
        this.status = j.getBoolean("status");
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();

        try {
            j.put("name", name);
            j.put("surname", surname);
            j.put("age", age);
            j.put("documentNumber", documentNumber);
            j.put("gender", gender);
            j.put("userType", userType);
            j.put("status", status);
            j.put("password", password);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }

        return j;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(documentNumber, user.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(documentNumber);
    }

}
