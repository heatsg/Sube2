package org.sube.project;

import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

public class Main {
    public static void main(String[] args) {
        Transaction transaction = new Transaction(TransactionType.RECHARGE, 2400, Card.getLocalDateTimeNow());
        transaction.viewTransaction();
    }
}