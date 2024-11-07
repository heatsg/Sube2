package org.sube.project.card.transaction.types;

import org.json.JSONObject;
import org.sube.project.bus.Lines;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

import javax.sound.sampled.Line;

public class TransactionPayment extends Transaction {




    public TransactionPayment() {
        setTransactionType(TransactionType.PAYMENT);
    }

    public TransactionPayment(JSONObject j) {
        super(j);
        setTransactionType(TransactionType.PAYMENT);
    }

    public TransactionPayment(double amount, String dateTime) {
        super(amount, dateTime);
        setTransactionType(TransactionType.PAYMENT);

    }

    public TransactionPayment(double amount) {
        super(amount);
        setTransactionType(TransactionType.PAYMENT);

    }

    @Override
    public JSONObject toJSON() {
        JSONObject j  = super.toJSON();
        j.put("transactionType", TransactionType.PAYMENT.toString());
        return j;
    }

    @Override
    public String toString() {
        return String.format("| %-3s | %-5s | %-10s | %-20s|",getId(),getAmount(),getTransactionType().toString(),getDateTime().toString());
    }
}
