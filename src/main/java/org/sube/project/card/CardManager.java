package org.sube.project.card;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import java.util.HashMap;
import java.util.Map;

public class CardManager {

    private static final int BASE_TICKET = 1180;

    public static void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        card.getTransactionHistory().add(new TransactionRecharge(amount));
    }

    public static boolean payTicket(Card card) {
        if (card == null || card.getCardType() == null) return false;

        card.setBalance(card.getBalance() - card.getCardType().getFinalPrice(BASE_TICKET));
        card.getTransactionHistory().add(new TransactionPayment(card.getCardType().getFinalPrice(BASE_TICKET)));
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

    public static Card getCardByID(String id) throws CardNotFoundException {
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);
            String storedCardID = cardJson.getString("id");

            if (storedCardID.equals(id)) {
                return new Card(cardJson);
            }
        }
        throw new CardNotFoundException("Tarjeta no encontrada");
    }


    public static void updateCardStatus(String cardId, boolean status) {
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject card = cardsArray.getJSONObject(i);

            if (card.getString("id").equalsIgnoreCase(cardId)) {
                card.put("status", status);
                break;
            }
        }
        JSONManager.write(Path.CARD, cardsArray);
    }

    public static void updateCardType(String cardId, String type) {
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject card = cardsArray.getJSONObject(i);

            if (card.getString("id").equalsIgnoreCase(cardId)) {
                card.put("cardType", type);
                break;
            }
        }
        JSONManager.write(Path.CARD, cardsArray);
    }
}
