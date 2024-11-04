package org.sube.project.accounts.authentication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserCredentials;
import org.sube.project.accounts.UserType;
import org.sube.project.card.Card;
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
    public static boolean login(String documentNumber, String password) {
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

        System.out.println("Ingrese genero:");
        String storedGender = scanner.next();

        System.out.println("Ingrese contraseña:");
        String password = scanner.next();

        Card card = new Card();
        UserType userType = UserType.NORMAL_USER;
        boolean status = true;
        UserCredentials userCredentials = new UserCredentials(storedDocument, password);

        return new User(storedName, storedSurname, storedAge, storedDocument, storedGender, card, userType, status, userCredentials);
    }

    /**
     * Metodo para registrar un nuevo usuario con sus respectivas caracteristicas
     */
    public static User getUserData() {
        User newUser = enteredData();
        return newUser;
    }


}