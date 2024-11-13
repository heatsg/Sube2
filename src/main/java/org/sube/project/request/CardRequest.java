package org.sube.project.request;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class CardRequest extends Request{

    private String cardId;

    public CardRequest(int id, String documentNumber, String cardId) {
        super(id, documentNumber);
        setRequestType(getClass().getSimpleName());
        this.cardId = cardId;
    }

    public CardRequest(JSONObject j) {
        super(j);
        this.cardId = j.getString("cardId");
        setRequestType(j.getString("RequestType"));
    }

    public String getCardId() {
        return cardId;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("RequestType", getRequestType());
            j.put("cardId", cardId);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}
