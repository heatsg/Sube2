package org.sube.project.card;

import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Card {
    private int id;
    private double balance;
    private Set<Transaction> transactionHistory;

    public Card(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.transactionHistory = new HashSet<>();
    }

    public double addBalance(double amount) {
        this.balance += amount;
         // CONTINUAR ACA, NO SE COMO REGISTRAR LA TRANSACCION POR LA ID.
        return amount;
    }

    public static String getLocalDateTimeNow() {
        return java.time.LocalDateTime.now().toString();
    }

    public int getId() {
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
