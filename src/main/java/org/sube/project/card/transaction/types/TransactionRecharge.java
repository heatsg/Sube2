package org.sube.project.card.transaction.types;

import org.json.JSONObject;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

import javax.swing.*;

public class TransactionRecharge extends Transaction {

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
        JSONObject j  = super.toJSON();
        j.put("transactionType", TransactionType.RECHARGE.toString());
        return j;
    }
}
