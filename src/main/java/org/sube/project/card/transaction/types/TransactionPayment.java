package org.sube.project.card.transaction.types;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.bus.Lines;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

import javax.sound.sampled.Line;
import java.time.LocalDateTime;

public final class TransactionPayment extends Transaction {

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
        JSONObject j = super.toJSON();
        try {
            j.put("transactionType", TransactionType.PAYMENT.toString());
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
        return j;
    }

    @Override
    public String toString() {
        return String.format("|%-3s |%-6s |%-11s |%-29s |", getId(), getAmount(), getTransactionType().toString(), getDateTime());
    }
}
