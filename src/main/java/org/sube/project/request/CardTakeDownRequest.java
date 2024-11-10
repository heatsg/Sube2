package org.sube.project.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CardTakeDownRequest extends Request {
    private String cardId;

    public CardTakeDownRequest(int id, String documentNumber, String cardId) {
        super(id, documentNumber);
        setRequestType(getClass().getSimpleName());
        this.cardId = cardId;
    }

    public CardTakeDownRequest(JSONObject j) {
        super(j);
        this.cardId = j.getString("cardId");
        setRequestType(j.getString("RequestType"));
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
