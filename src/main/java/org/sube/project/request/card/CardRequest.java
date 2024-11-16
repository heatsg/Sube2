package org.sube.project.request.card;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.request.Request;

public abstract class CardRequest extends Request {

    private String cardId;

    public CardRequest(String documentNumber, String cardId) {
        super(documentNumber);
        setRequestType(getClass().getSimpleName());
        this.cardId = cardId;
    }

    public CardRequest(JSONObject j) {
        super(j);
        this.cardId = j.getString("cardId");
        setRequestType(j.getString("requestType"));
    }

    public String getCardId() {
        return cardId;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("requestType", getRequestType());
            j.put("cardId", cardId);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}
