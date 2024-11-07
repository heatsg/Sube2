package org.sube.project.card.transaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.ID_TYPE;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONSube;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Transaction implements JSONCompatible {
    private static int idCounter = JSONSube.assignID(ID_TYPE.TRANSACTION);
    private int id;
    private LocalDateTime dateTime;
    private TransactionType transactionType;
    private double amount;

    public Transaction() {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(double amount) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
    }

    public Transaction(double amount, String dateTime) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.amount = amount;
    }

    public Transaction(JSONObject j) {
        try {
            this.id = j.getInt("id");
            this.dateTime = LocalDateTime.parse(j.getString("dateTime"));
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void viewTransaction() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Transaction that = (Transaction) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

//    @Override
//    public String toString() {
//        return "Transacción (ID: " + id +
//                " | Fecha y Hora: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")) +
//                " | Monto: " + amount +
//                ')';
//    }
}
