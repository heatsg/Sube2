package org.sube.project;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.card.Card;
import org.sube.project.card.CardType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.front.Sube;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Sube.getInstance().showUI(true);

        Card card = new Card();
        Card card1 = new Card(CardType.NORMAL_CARD, "46814665");
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(card.toJSON());
        jsonArray.put(card1.toJSON());
        JSONManager.write(Path.CARD, jsonArray);




    }
}