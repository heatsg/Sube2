package org.sube.project.card;

import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

public class CardManager {

    public void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        card.getTransactionHistory().add(new Transaction(TransactionType.RECHARGE, amount));
    }



}
