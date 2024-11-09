package org.sube.project.card;

import org.json.JSONArray;
import org.sube.project.accounts.UserType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CardManager {

    private static final int BASE_TICKET = 1180;

    public static void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        card.getTransactionHistory().add(new TransactionRecharge(amount));
    }

    public static boolean payTicket(Card card) {
        if (card == null || card.getCardType() == null) return false;

        card.setBalance(card.getBalance() - card.getCardType().getFinalPrice(BASE_TICKET));
        return true;
    }

    public static void setCardDocumentNumber(String id, String documentNumber) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        Map<String, Card> cards = new HashMap<>();

        for (int i = 0; i < array.length(); i++) {
            cards.put(array.getJSONObject(i).getString("id"), new Card(array.getJSONObject(i)));
        }

        Card card = cards.get(id);
        card.setDniOwner(documentNumber);
        cards.put(id, card);
        JSONManager.collectionToFile(cards.values(), Path.CARD, true);
    }

    public static void updateCardFile(Card card) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        Map<String, Card> cards = new HashMap<>();

        for (int i = 0; i < array.length(); i++) {
            cards.put(array.getJSONObject(i).getString("id"), new Card(array.getJSONObject(i)));
        }

        cards.put(card.getId(), card);
        JSONManager.collectionToFile(cards.values(), Path.CARD, true);
    }
}
