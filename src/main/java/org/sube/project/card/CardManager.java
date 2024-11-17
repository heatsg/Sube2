package org.sube.project.card;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.bus.Bus;
import org.sube.project.bus.Lines;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManager {

    private static final int BASE_TICKET = 1180;

    public static void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        card.getTransactionHistory().add(new TransactionRecharge(card.getDniOwner(),amount));
    }

    public static boolean payTicket(Card card,Lines line) {
        if (card == null || card.getCardType() == null) return false;

        card.setBalance(card.getBalance() - card.getCardType().getFinalPrice(BASE_TICKET));
        card.getTransactionHistory().add(new TransactionPayment(card.getDniOwner(),card.getCardType().getFinalPrice(BASE_TICKET),line));
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

    private static String convertTypesToEnum(String type) {
        switch (type) {
            case "Normal":
                type = "NORMAL_CARD";
                break;

            case "Estudiante":
                type = "STUDENT";
                break;

            case "Docente":
                type = "TEACHER";
                break;

            case "Discapacitado":
                type = "DISABLED_PERSON";
                break;

            case "Jubilado":
                type = "RETIRED";
                break;
        }

        return type;
    }

    public static void updateCardType(String cardId, String type) {
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject card = cardsArray.getJSONObject(i);

            if (card.getString("id").equalsIgnoreCase(cardId)) {
                card.put("cardType", convertTypesToEnum(type));
                break;
            }
        }
        JSONManager.write(Path.CARD, cardsArray);
    }


    public static JComboBox<String> loadCardBenefits() {
        List<CardType> benefitsArray = new ArrayList<>(List.of(CardType.values()));
        JComboBox<String> benefitsBox = new JComboBox<>();

        for (CardType benefits : benefitsArray) {
            benefitsBox.addItem(benefits.toString());
        }

        return benefitsBox;
    }

    public static void generateEmptyCards(int amount) {
        JSONArray jarr = JSONManager.readJSONArray(Path.CARD);
        for (int i = 0; i < amount; i++) {
            Card card = new Card();
            jarr.put(card.toJSON());
        }

        JSONManager.write(Path.CARD, jarr);
    }
}
