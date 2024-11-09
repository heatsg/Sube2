package org.sube.project.card.transaction.types;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

import javax.swing.*;

public final class TransactionRecharge extends Transaction {

    public TransactionRecharge() {
        setTransactionType(TransactionType.RECHARGE);
    }

    public TransactionRecharge(JSONObject j) {
        super(j);
        setTransactionType(TransactionType.RECHARGE);
    }

    public TransactionRecharge(double amount, String dateTime) {
        super(amount, dateTime);
        setTransactionType(TransactionType.RECHARGE);
    }

    public TransactionRecharge(double amount) {
        super(amount);
        setTransactionType(TransactionType.RECHARGE);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("transactionType", TransactionType.RECHARGE.toString());
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
