package org.sube.project.card.transaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONSube;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Transaction implements JSONCompatible {
    private static int idCounter = JSONSube.assignTransactionIDCounter();
    private int id;
    private LocalDateTime dateTime;
    private TransactionType transactionType;
    private double amount;
    private boolean isProcessed; // Campo para indicar si la transacción ha sido procesada

    public Transaction() {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
        this.isProcessed = false;
    }

    public Transaction(double amount) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
        this.isProcessed = false;
    }

    public Transaction(double amount, String dateTime) {
        this.id = idCounter++;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.amount = amount;
        this.isProcessed = false;
    }

    public Transaction(JSONObject j) {
        try {
            this.id = j.getInt("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
            this.dateTime = LocalDateTime.parse(j.getString("dateTime"), formatter);
            this.amount = j.getDouble("amount");
            this.isProcessed = j.optBoolean("isProcessed", false); // Cargar isProcessed, predeterminado a false si no existe
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", id);
            j.put("dateTime", dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")));
            j.put("amount", amount);
            j.put("isProcessed", isProcessed); // Guardar el estado de isProcessed en JSON
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
        return j;
    }

    // Getters y Setters para el nuevo atributo isProcessed
    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
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

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss"));
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

    @Override
    public String toString() {
        return String.format("Transacción (ID: %d | Fecha y Hora: %s | Monto: %.2f | Procesada: %s)",
                id, dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")), amount, isProcessed ? "Sí" : "No");
    }
}
