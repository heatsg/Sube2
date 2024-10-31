package org.sube.project.card.transaction;

public enum TransactionType {
    RECHARGE("Recarga"),
    PAYMENT("Pago");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
