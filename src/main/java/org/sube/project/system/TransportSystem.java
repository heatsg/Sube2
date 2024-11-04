package org.sube.project.system;


import org.json.JSONArray;
import org.json.JSONException;
import org.sube.project.card.Card;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.util.JSONManager;
import org.sube.project.util.PATH;

import java.util.HashMap;
import java.util.Map;

public class TransportSystem {

    private final Map<Integer, User> users;
    private final Map<String, Card> cards;
    private final Map<String, Double> uncreditedAmount;

    public TransportSystem() {
        this.users = new HashMap<>();
        this.cards = new HashMap<>();
        this.uncreditedAmount = new HashMap<>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public Map<String, Double> getUncreditedAmount() {
        return uncreditedAmount;
    }

    public User registerUser(int identifier, String name, String surname, int age, String documentNumber, char gender) {
        Card card = new Card();
        User user = new User(name, surname, age, documentNumber, gender, card, UserType.NORMAL_USER, true);

        users.put(user.getId(), user);
        cards.put(card.getId(), card);

        TransportSystem.UserToFile(user);

        return user;
    }

    public static void UserToFile(User user){
        try {
            JSONArray jarr = JSONManager.leerJSONArray(PATH.USER);
            jarr.put(user.ToJSON());
            JSONManager.escribir(PATH.USER, jarr);
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
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
        uncreditedAmount.put(cardId, uncreditedAmount.get(cardId) + amount);
    }

    public boolean creditIntoCard(String cardId) {
        Card card = cards.get(cardId);
        if (card == null) {
            System.out.println("Tarjeta SUBE no encontrada.");
            return false;
        }

        card.addBalance(uncreditedAmount.get(cardId));
        uncreditedAmount.remove(cardId);

        System.out.println("Cargas acreditadas, saldo final: " + card.getBalance());
        return true;
    }
}
