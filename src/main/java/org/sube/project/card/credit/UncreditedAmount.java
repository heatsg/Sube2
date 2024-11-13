package org.sube.project.card.credit;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.json.JSONCompatible;

import java.util.Objects;

public class UncreditedAmount implements JSONCompatible {
    private String id;
    private double amount;
    private boolean status;

    public UncreditedAmount(String id, double amount) {
        this.id = id;
        this.amount = amount;
        this.status = true;
    }

    public UncreditedAmount(JSONObject j) {
        this.id = j.getString("id");
        this.amount = j.getDouble("amount");
        this.status = j.getBoolean("status");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", id);
            j.put("amount", amount);
            j.put("status", status);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UncreditedAmount that)) return false;
        return Double.compare(amount, that.amount) == 0 && status == that.status && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, status);
    }

}
