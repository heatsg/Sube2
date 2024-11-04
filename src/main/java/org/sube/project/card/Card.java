package org.sube.project.card;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.util.JSONSube;

import java.util.HashSet;
import java.util.Set;

public class Card {
    private final String id;
    private double balance;
    private final Set<Transaction> transactionHistory;
    private boolean status;

    public Card() {
        this.id = JSONSube.generateCardID();
        this.balance = 0;
        this.transactionHistory = new HashSet<>();
    }

    public Card(String id, double balance, boolean status) {
        this.id = JSONSube.generateCardID();
        this.balance = balance;
        this.transactionHistory = new HashSet<>();
        this.status = status;
    }

    public double addBalance(double amount) {
        this.balance += amount;
        transactionHistory.add(new Transaction(TransactionType.RECHARGE, amount));
        // CONTINUAR ACA, NO SE COMO REGISTRAR LA TRANSACCION POR LA ID.
        return amount;
    }

    public JSONObject ToJSON(){
        JSONObject j=new JSONObject();
        try {
            j.put("id",this.id);
            j.put("balance",this.balance);
            j.put("transactionHsitory",this.transactionHistory);
            j.put("status",this.status);
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
        }

        return j;
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
