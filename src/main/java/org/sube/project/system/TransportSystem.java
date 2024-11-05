package org.sube.project.system;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.card.Card;
import org.sube.project.accounts.User;
import org.sube.project.card.CardManager;
import org.sube.project.exceptions.UserAlreadyExistsException;
import org.sube.project.util.PATH;
import org.sube.project.util.json.JSONManager;

import java.util.HashMap;
import java.util.Map;

public class TransportSystem {

    CardManager cardManager = new CardManager();

    private final Map<Integer, User> users;
    private final Map<String, Card> cards;
    private final Map<String, Double> uncreditedAmounts;

    public TransportSystem() {
        this.users = new HashMap<>();
        this.cards = new HashMap<>();
        this.uncreditedAmounts = new HashMap<>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public Map<String, Double> getUncreditedAmounts() {
        return uncreditedAmounts;
    }

    public void saveIntoJSON() {
        JSONManager.collectionToFile(users.values(), PATH.USER, true);
        JSONManager.collectionToFile(cards.values(), PATH.CARD, true);
    }

    private void saveUncreditedAmountsIntoJSON() {
        JSONArray jarr = new JSONArray();

        JSONObject j = new JSONObject();
        try {
            for (String cardId : uncreditedAmounts.keySet()) {
                j.put("id", cardId);
                j.put("amount", uncreditedAmounts.get(cardId));

                jarr.put(j);
            }

            JSONManager.write(PATH.UNCREDITED, jarr);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
    }

    public void loadFromJSON() {
        users.clear();
        JSONArray jarr = JSONManager.readJSONArray(PATH.USER);
        for (int i = 0; i < jarr.length(); i++) {
            User user = new User(jarr.getJSONObject(i));
            users.put(user.getId(), user);
        }

        cards.clear();
        jarr = JSONManager.readJSONArray(PATH.CARD);
        for (int i = 0; i < jarr.length(); i++) {
            Card card = new Card(jarr.getJSONObject(i));
            cards.put(card.getId(), card);
        }

        uncreditedAmounts.clear();
        jarr = JSONManager.readJSONArray(PATH.UNCREDITED);
        for (int i = 0; i < jarr.length(); i++) {
            JSONObject uncreditedAmountJSON = jarr.getJSONObject(i);
            uncreditedAmounts.put(uncreditedAmountJSON.getString("id"), uncreditedAmountJSON.getDouble("amount"));
        }
    }

    public void registerUser() throws UserAlreadyExistsException {
        User user = UserAuthentication.getUserData();

        try {
            if (!(users.containsKey(user.getId()))) {
                users.put(user.getId(), user);
                cards.put(user.getCard().getId(), user.getCard());
                saveIntoJSON();
                System.out.println("Usuario registrado correctamente.");
            } else {
                throw new UserAlreadyExistsException("Error: El usuario ya existe.");
            }
        } catch (UserAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean rechargeCard(String cardId, double amount) {
        if (cards.get(cardId) != null) {
            addUncreditedAmount(cardId, amount);
            System.out.println("Recarga exitosa (" + amount + ')');
            return true;
        } else {
            System.out.println("Tarjeta SUBE no encontrada.");
            return false;
        }
    }

    public void addUncreditedAmount(String cardId, double amount) {
        // Almacena acumulativamente en el map
        uncreditedAmounts.put(cardId, uncreditedAmounts.get(cardId) + amount);
    }

    public boolean creditIntoCard(String cardId) {
        Card card = cards.get(cardId);

        if (card == null) {
            System.out.println("Tarjeta SUBE no encontrada.");
            return false;
        }

        cardManager.addBalance(card, uncreditedAmounts.get(cardId));
        uncreditedAmounts.remove(cardId);

        System.out.println("Cargas acreditadas, saldo final: " + card.getBalance());
        return true;
    }
}
