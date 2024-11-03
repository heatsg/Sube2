package org.sube.project.card;

import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.exceptions.malformedIdException;
import org.sube.project.util.JSONSube;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Card {
    private final String id;
    private double balance;
    private final Set<Transaction> transactionHistory;

    public Card() {
        this.id = JSONSube.generateCardID();
        this.balance = 0;
        this.transactionHistory = new HashSet<>();
    }

    public Card(String id, double balance) {
        this.id = JSONSube.generateCardID();
        this.balance = balance;
        this.transactionHistory = new HashSet<>();
    }

    public double addBalance(double amount) {
        this.balance += amount;
        transactionHistory.add(new Transaction(TransactionType.RECHARGE, amount));
        // CONTINUAR ACA, NO SE COMO REGISTRAR LA TRANSACCION POR LA ID.
        return amount;
    }

    public static String getLocalDateTimeNow() {
        return java.time.LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
