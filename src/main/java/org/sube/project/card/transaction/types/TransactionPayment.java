package org.sube.project.card.transaction.types;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.bus.Bus;
import org.sube.project.bus.Lines;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;

public final class TransactionPayment extends Transaction {

    private Lines line;
    public TransactionPayment(String dniAffiliated) {
        super(dniAffiliated);
        setTransactionType(TransactionType.PAYMENT.toString());
    }

    public TransactionPayment(JSONObject j) {
        super(j);
        setTransactionType(TransactionType.PAYMENT.toString());
        setLine(Lines.valueOf(j.getString("line")));
    }

    public TransactionPayment(String dniAffiliated,double amount, String dateTime) {
        super(dniAffiliated, amount, dateTime);
        setTransactionType(TransactionType.PAYMENT.toString());
    }

    public TransactionPayment(String dniAffiliated,double amount,Lines line) {
        super(dniAffiliated, amount);
        setTransactionType(TransactionType.PAYMENT.toString());
        setLine(line);
    }

    public void setLine(Lines line) {
        this.line = line;
    }

    public Lines getLine() {
        return line;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("transactionType", TransactionType.PAYMENT.toString());
            j.put("line", line.toString());
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
