package org.sube.project.card;

import org.sube.project.accounts.UserType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.types.TransactionRecharge;

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

}
