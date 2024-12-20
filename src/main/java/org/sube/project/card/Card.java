package org.sube.project.card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONSube;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Card implements JSONCompatible {
    private final String id;
    private String dniOwner;
    private double balance;
    private final Set<Transaction> transactionHistory;
    private boolean status;
    private CardType cardType;

    public Card() {
        this.id = JSONSube.generateRandomCardID();
        this.dniOwner = "";
        this.balance = 1000;
        this.transactionHistory = new LinkedHashSet<>();
        this.status = true;
        this.cardType = CardType.NORMAL_CARD;
    }

    public Card(CardType cardType, String dniOwner) {
        this.id = JSONSube.generateRandomCardID();
        this.dniOwner = dniOwner;
        this.balance = 1000;
        this.transactionHistory = new LinkedHashSet<>();
        this.status = true;
        this.cardType = cardType;
    }

    public Card(JSONObject j) {
        this.id = j.getString("id");
        this.cardType = CardType.valueOf(j.getString("cardType"));
        this.dniOwner = j.getString("dniOwner");
        this.balance = j.getDouble("balance");
        this.transactionHistory = new LinkedHashSet<>();

        JSONArray jarr = j.getJSONArray("transactionHistory");

        if (!jarr.isEmpty()) {
            for (int i = 0; i < jarr.length(); i++) {
                if (jarr.getJSONObject(i).getString("transactionType").equalsIgnoreCase(TransactionType.PAYMENT.toString())) {
                    transactionHistory.add(new TransactionPayment(jarr.getJSONObject(i)));
                } else if (jarr.getJSONObject(i).getString("transactionType").equalsIgnoreCase(TransactionType.RECHARGE.toString())) {
                    transactionHistory.add(new TransactionRecharge(jarr.getJSONObject(i)));
                }
            }
        }

        this.status = j.getBoolean("status");
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();

        try {
            j.put("id", id);
            j.put("cardType", cardType);
            j.put("dniOwner", dniOwner);
            j.put("balance", balance);
            j.put("transactionHistory", new JSONArray(transactionHistory));
            j.put("status", status);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }

        return j;
    }

    public static String getLocalDateTimeNow() {
        return java.time.LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDniOwner() {
        return dniOwner;
    }

    public void setDniOwner(String dniOwner) {
        this.dniOwner = dniOwner;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Card card = (Card) object;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("|%-17s |%-8s |%-7s |%-6s |%-10s |", id, dniOwner, balance, status, cardType.toString());
    }
}
