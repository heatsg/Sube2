package org.sube.project.card;

import org.sube.project.card.transaction.Transaction;

public class CardManager {

    public void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        //card.getTransactionHistory().add();
    }



}
