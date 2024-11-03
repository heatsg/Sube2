package org.sube.project.system;


import org.sube.project.card.Card;
import org.sube.project.user.User;

import java.util.HashMap;
import java.util.Map;

public class TransportSystem {

    private Map<Integer, User> users;
    private Map<Integer, Card> cards;

    public TransportSystem() {
        this.users = new HashMap<>();
        this.cards = new HashMap<>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, Card> getCards() {
        return cards;
    }

    public User registerUser(int identifier, String name, String surname, int age, String documentNumber, char gender, int cardId) {
        Card card = new Card(cardId, 0);
        User user = new User(identifier, name, surname, age, documentNumber, gender, card);

        users.put(identifier, user);
        cards.put(cardId, card);

        return user;
    }

    public int rechargeCard(String cardId, double amount) {
        Card card = cards.get(cardId);

        if (card != null) {
            card.addBalance(amount);
            System.out.println("Recarga exitosa (" + amount + ")");
            return 1;
        } else {
            System.out.println("Tarjeta Sube no encontrada...");
            return 0;
        }
    }
}
