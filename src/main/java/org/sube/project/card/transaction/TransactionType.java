package org.sube.project.card.transaction;

public enum TransactionType {
    RECHARGE("Recarga"),
    PAYMENT("Pago");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
