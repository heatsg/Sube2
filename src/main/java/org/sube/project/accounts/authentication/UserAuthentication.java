package org.sube.project.accounts.authentication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.exceptions.IncorrectCredentialsException;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.util.json.JSONManager;
import org.sube.project.util.Path;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserAuthentication {

    /**
     * Verifica si el DNI y la contraseña son correctos.
     *
     * @param documentNumber El DNI del usuario.
     * @param password       La contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public static boolean login(String documentNumber, String password) throws IncorrectCredentialsException {
        JSONArray users = JSONManager.readJSONArray(Path.USER.toString());

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String storedDocumentNumber = user.getString("documentNumber");
            String storedPassword = user.getString("password");

            if (storedDocumentNumber.equals(documentNumber) && storedPassword.equals(password))
                return true;
        }
        throw new IncorrectCredentialsException("Documento o contraseña incorrectos.");
    }

    /**
     * Obtiene un usuario por su número de documento.
     *
     * @param documentNumber El DNI del usuario.
     * @return El objeto User correspondiente al documento.
     * @throws UserNotFoundException Si el documento no se encuentra.
     */
    public static User getUserByDocumentNumber(String documentNumber) throws UserNotFoundException {
        JSONArray users = JSONManager.readJSONArray(Path.USER.toString());

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            String storedDocumentNumber = userJson.getString("documentNumber");

            if (storedDocumentNumber.equals(documentNumber)) {
                String name = userJson.getString("name");
                String surname = userJson.getString("surname");
                int age = userJson.getInt("age");
                String gender = userJson.getString("gender");
                String password = userJson.getString("password");
                UserType userType = UserType.valueOf(userJson.getString("userType"));

                return new User(name, surname, age, documentNumber, gender, userType, true, password);
            }
        }

        throw new UserNotFoundException("Usuario no encontrado.");
    }

}



