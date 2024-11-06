package org.sube.project.card.transaction;

public enum TransactionType {
    RECHARGE("recarga"),
    PAYMENT("pago");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
