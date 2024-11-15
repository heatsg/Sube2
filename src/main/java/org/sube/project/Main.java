package org.sube.project;

import org.json.JSONArray;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.bus.Bus;
import org.sube.project.bus.BusManager;
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

        /*JSONArray jsonArray = new JSONArray();
        User user = new User("test", "asd", 40, "99999999", "Masculino", UserType.ADMIN, true, "password");

        jsonArray.put(user.toJSON());
        JSONManager.write(Path.USER, jsonArray);*/

//        JSONArray jsonArray = JSONManager.readJSONArray(Path.CARD);
//        Card card = new Card();
//
//        jsonArray.put(card.toJSON());
//        JSONManager.write(Path.CARD, jsonArray);
    }
}