package org.sube.project.accounts;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.json.JSONCompatible;

public class UserCredentials implements JSONCompatible {
    private String documentNumber;
    private String password;

    /**
     * Credenciales de Usuario: Esto ayuda al sistema de autenticacion a que el usuario pueda logearse.
     *
     * @param documentNumber
     * @param password
     */

    public UserCredentials(String documentNumber, String password) {
        this.documentNumber = documentNumber;
        this.password = password;
    }

    public UserCredentials(JSONObject j) {
        this.documentNumber = j.getString("documentNumber");
        this.password = j.getString("password");
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();

        try {
            j.put("documentNumber", documentNumber);
            j.put("password", password);
        } catch (JSONException jx) {
            System.err.println(jx.getMessage());
            jx.printStackTrace();
        }

        return j;
    }
}