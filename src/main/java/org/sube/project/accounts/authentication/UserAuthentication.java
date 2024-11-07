package org.sube.project.accounts.authentication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.card.Card;
import org.sube.project.exceptions.IncorrectCredentialsException;
import org.sube.project.util.json.JSONManager;
import org.sube.project.util.PATH;

import java.util.Scanner;

public class UserAuthentication {

    /**
     * Verifica si el DNI y la contraseña son correctos.
     *
     * @param documentNumber El DNI del usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public static boolean login(String documentNumber, String password) throws IncorrectCredentialsException {
        JSONArray users = JSONManager.readJSONArray(PATH.USER);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String storedDocumentNumber = user.getString("documentNumber");
            String storedPassword = user.getString("password");

            if (storedDocumentNumber.equals(documentNumber) && storedPassword.equals(password)) {
                return true;
            } else {
                throw new IncorrectCredentialsException("Documento o contraseña incorrectos.");
            }
        }
        return false;
    }

    private static User enteredData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese nombre:");
        String storedName = scanner.next();

        System.out.println("Ingrese apellido:");
        String storedSurname = scanner.next();

        System.out.println("Ingrese edad:");
        int storedAge = scanner.nextInt();

        System.out.println("Ingrese documento:");
        String storedDocument = scanner.next();

        System.out.println("Ingrese género:");
        String storedGender = scanner.next();

        System.out.println("Ingrese contraseña:");
        String password = scanner.next();

        UserType userType = UserType.NORMAL_USER;
        boolean status = true;

        return new User(storedName, storedSurname, storedAge, storedDocument, storedGender, userType, status, password);
    }

    /**
     * Metodo para registrar un nuevo usuario con sus respectivas caracteristicas
     */
    public static User getUserData() {
        User newUser = enteredData();
        return newUser;
    }


}