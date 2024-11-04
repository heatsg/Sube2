package org.sube.project.accounts.authentication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.util.json.JSONManager;
import org.sube.project.util.PATH;

public class UserLogin {
    /**
     * Verifica si el DNI y la contraseña son correctos.
     *
     * @param documentNumber El DNI del usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean login(String documentNumber, String password) {
        JSONArray users = JSONManager.leerJSONArray(PATH.USER);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String storedDocumentNumber = user.getString("documentNumber");
            String storedPassword = user.getString("password");

            if (storedDocumentNumber.equals(documentNumber) && storedPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
}