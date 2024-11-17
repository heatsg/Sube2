package org.sube.project.card.transaction.types;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

public final class TransactionRecharge extends Transaction {


    public TransactionRecharge(String dniAffiliated) {
        super(dniAffiliated);
        setTransactionType(TransactionType.RECHARGE.toString());
    }

    public TransactionRecharge(JSONObject j) {
        super(j);
        setTransactionType(TransactionType.RECHARGE.toString());
    }

    public TransactionRecharge(String dniAffiliated,double amount, String dateTime) {
        super(dniAffiliated, amount, dateTime);
        setTransactionType(TransactionType.RECHARGE.toString());
    }

    public TransactionRecharge(String dniAffiliated,double amount) {
        super(dniAffiliated, amount);
        setTransactionType(TransactionType.RECHARGE.toString());
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
