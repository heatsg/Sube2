package org.sube.project.card.transaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.json.JSONCompatible;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public abstract class Transaction implements JSONCompatible {
    private String id;
    private String dniAffiliated;
    private LocalDateTime dateTime;
    private String transactionType;
    private double amount;

    public Transaction(String dniAffiliated) {
        this.dniAffiliated = dniAffiliated;
        this.id = generateUniqueID();
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(String dniAffiliated, double amount) {
        this.dniAffiliated = dniAffiliated;
        this.id = generateUniqueID();
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
    }

    public Transaction(String dniAffiliated, double amount, String dateTime) {
        this.dniAffiliated = dniAffiliated;
        this.id = generateUniqueID();
        this.dateTime = LocalDateTime.parse(dateTime);
        this.amount = amount;
    }

    public Transaction(JSONObject j) {
        try {
            this.dniAffiliated = j.getString("dniAffiliated");
            this.id = j.getString("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
            this.dateTime = LocalDateTime.parse(j.getString("dateTime"), formatter);
            this.amount = j.getDouble("amount");
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("dniAffiliated", dniAffiliated);
            j.put("id", id);
            j.put("dateTime", dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")));
            j.put("amount", amount);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
        return j;
    }

    public String getDniAffiliated() {
        return dniAffiliated;
    }

    public void setDniAffiliated(String dniAffiliated) {
        this.dniAffiliated = dniAffiliated;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss"));
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void viewTransaction() {
        System.out.println(this);
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Transaction that = (Transaction) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Transacción (ID: %d | Fecha y Hora: %s | Monto: %.2f)",
                id, dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")), amount);
    }
}
