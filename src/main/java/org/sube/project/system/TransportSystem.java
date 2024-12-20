package org.sube.project.system;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.accounts.UserType;
import org.sube.project.card.Card;
import org.sube.project.accounts.User;
import org.sube.project.card.CardManager;
import org.sube.project.card.CardType;
import org.sube.project.exceptions.UserAlreadyExistsException;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TransportSystem {
    public static TransportSystem instance;

    private final Map<String, User> users;
    private final Map<String, Card> cards;
    private final Map<String, Double> uncreditedAmounts;

    public TransportSystem() {
        this.users = new HashMap<>();
        this.cards = new HashMap<>();
        this.uncreditedAmounts = new HashMap<>();
        instance = this;
    }

    /**
     * Metodo para obtener una instancia de la clase TransportSystem.
     *
     * @return
     */
    public static TransportSystem getInstance() {
        if (instance == null) {
            instance = new TransportSystem();
        }
        return instance;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public Map<String, Double> getUncreditedAmounts() {
        return uncreditedAmounts;
    }

    /**
     * Metodo para guardar colecciones en JSON
     */
    public void saveIntoJSON() {
        JSONManager.collectionToFile(users.values(), Path.USER, true);
        JSONManager.collectionToFile(cards.values(), Path.CARD, true);
    }

    /**
     * Metodo para actualizar el arreglo de las tarjetas en JSON
     */
    public void updateCardsJSON() {
        JSONManager.collectionToFile(cards.values(), Path.CARD, true);
    }

    /**
     * Metodo para actualizar el arreglo de usuarios en JSON
     */
    public void updateUsersJSON() {
        JSONManager.collectionToFile(users.values(), Path.USER, true);
    }


    /**
     * Metodo para guardar el dinero no acreditado en JSON
     */
    private void saveUncreditedAmountsIntoJSON() {
        JSONArray jarr = new JSONArray();

        try {
            for (String cardId : uncreditedAmounts.keySet()) {
                JSONObject j = new JSONObject();
                j.put("id", cardId);
                j.put("amount", uncreditedAmounts.get(cardId));

                jarr.put(j);
            }

            JSONManager.write(Path.UNCREDITED, jarr);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
    }

    /**
     * Metodo para cargar usuarios, tarjetas y dinero no acreditado desde JSON
     */
    public void loadFromJSON() {
        users.clear();
        JSONArray usersArray = JSONManager.readJSONArray(Path.USER);
        for (int i = 0; i < usersArray.length(); i++) {
            User user = new User(usersArray.getJSONObject(i));
            if (user.getStatus())
                users.put(user.getDocumentNumber(), user);
        }

        cards.clear();
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);
        for (int i = 0; i < cardsArray.length(); i++) {
            Card card = new Card(cardsArray.getJSONObject(i));
            if (card.getStatus())
                cards.put(card.getId(), card);
        }

        uncreditedAmounts.clear();
        JSONArray uncreditedArray = JSONManager.readJSONArray(Path.UNCREDITED);
        for (int i = 0; i < uncreditedArray.length(); i++) {
            JSONObject uncreditedAmountJSON = uncreditedArray.getJSONObject(i);
            String cardId = uncreditedAmountJSON.getString("id");
            double amount = uncreditedAmountJSON.getDouble("amount");
            uncreditedAmounts.put(cardId, amount);
        }
    }


    /**
     * Metodo para registrar un nuevo Usuario.
     *
     * @param user
     * @throws UserAlreadyExistsException
     */
    public void registerUser(User user) throws UserAlreadyExistsException {
        try {
            if (!(users.containsKey(user.getDocumentNumber()))) {
                users.put(user.getDocumentNumber(), user);
                saveIntoJSON();
            } else {
                throw new UserAlreadyExistsException("Error: El usuario ya existe.");
            }
        } catch (UserAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Metodo para registrar una nueva tarjeta.
     *
     * @param cardType
     * @param dniOwner
     */
    public void registerCard(CardType cardType, String dniOwner) {
        Card card = new Card(cardType, dniOwner);

        if (!cards.containsKey(card.getId())) {
            cards.put(card.getId(), card);
            updateCardsJSON();

            System.out.println("Tarjeta registrada correctamente con ID: " + card.getId());
        } else {
            System.out.println("La tarjeta ya existe en el sistema.");
        }
    }

    /**
     * Metodo para añadir nuevos montos no acreditados en la tarjeta.
     *
     * @param cardId
     * @param amount
     */
    public void addUncreditedAmount(String cardId, double amount) {
        double currentAmount = uncreditedAmounts.getOrDefault(cardId, 0.0);
        uncreditedAmounts.put(cardId, currentAmount + amount);
        saveUncreditedAmountsIntoJSON();
    }


    /**
     * Metodo para acreditar el dinero en la tarjeta.
     *
     * @param cardId
     * @return
     */
    public boolean creditIntoCard(String cardId) {
        if (!cards.containsKey(cardId)) {
            System.out.println("Tarjeta con ID " + cardId + " no encontrada en el registro de tarjetas.");
            return false;
        }

        if (!uncreditedAmounts.containsKey(cardId)) {
            System.out.println("No hay saldo pendiente para la tarjeta con ID: " + cardId);
            return false;
        }

        Card card = cards.get(cardId);

        CardManager.addBalance(card, uncreditedAmounts.get(cardId));
        uncreditedAmounts.remove(cardId);
        saveUncreditedAmountsIntoJSON();

        cards.put(card.getId(), card);
        updateCardsJSON();

        JOptionPane.showMessageDialog(null, "Cargas acreditadas, saldo final: " + card.getBalance(), "Acreditacion", JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

    /**
     * Metodo para dar de baja una tarjeta.
     *
     * @param id
     * @return
     */
    public boolean dropCard(String id) {
        if (cards.containsKey(id)) {
            cards.get(id).setStatus(false);
            updateCardsJSON();
            return true;
        }
        return false;
    }

    /**
     * Metodo para dar de baja un usuario.
     *
     * @param document
     * @return
     */
    public boolean dropUser(String document) {
        if (users.containsKey(document)) {
            users.get(document).setStatus(false);
            updateUsersJSON();
            return true;
        }
        return false;
    }

    /**
     *
     *
     * @param newName
     * @param user
     */
    public void modifyUserName(String newName, User user) {
        user.setName(newName);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyUserSurname(String newSurname, User user) {
        user.setSurname(newSurname);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyAge(int newAge, User user) {
        user.setAge(newAge);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyGender(String gender, User user) {
        user.setGender(gender);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyUserType(UserType type, User user) {
        user.setUserType(type);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyUserPassword(String newPassword, User user) {
        user.setPassword(newPassword);
        users.put(user.getDocumentNumber(), user);
        updateUsersJSON();
    }

    public void modifyCardType(CardType type, Card card) {
        card.setCardType(type);
        cards.put(card.getId(), card);
        updateCardsJSON();
    }
}