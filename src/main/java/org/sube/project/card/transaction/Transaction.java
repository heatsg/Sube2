package org.sube.project.card.transaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.ID_TYPE;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONSube;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction implements JSONCompatible {
    private static int idCounter = JSONSube.assignID(ID_TYPE.TRANSACTION);
    private int id;
    private LocalDateTime dateTime;
    private TransactionType transactionType;
    private double amount;

    public Transaction() {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(TransactionType transactionType, double amount) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public Transaction(TransactionType transactionType, double amount, String dateTime) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public Transaction(JSONObject j) {
        try {
            this.id = j.getInt("id");
            this.dateTime = LocalDateTime.parse(j.getString("dateTime"));
            this.transactionType = TransactionType.valueOf(j.getString("transactionType"));
            this.amount = j.getDouble("amount");
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", id);
            j.put("dateTime", dateTime);
            j.put("transactionType", transactionType);
            j.put("amount", amount);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
        return j;
    }

    public int getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public void viewTransaction() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Double.compare(that.amount, amount) == 0 && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, amount);
    }

    @Override
    public String toString() {
        return "Transacción (ID: " + id +
                " | Fecha y Hora: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")) +
                " | Tipo: " + transactionType +
                " | Monto: " + amount +
                ')';
    }
}
