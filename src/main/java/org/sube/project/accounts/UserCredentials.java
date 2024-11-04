package org.sube.project.accounts;

public class UserCredentials {
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

    /**
     * Método para verificar si la contraseña ingresada es correcta.
     *
     * @param inputPassword La contraseña proporcionada para verificar.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}