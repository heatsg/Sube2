package org.sube.project.card.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {
    private final int id;
    private TransactionType transactionType;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(int id, TransactionType transactionType, double amount, String dateTime) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.dateTime = LocalDateTime.parse(dateTime);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        System.out.println("Transaccion (ID: " + this.getId() + " | Fecha & Hora: " + dateTime.format(formatter) + " | Tipo: " + this.getTransactionType() + " | Monto: " + this.getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, amount);
    }
}
