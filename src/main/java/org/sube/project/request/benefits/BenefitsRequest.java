package org.sube.project.request.benefits;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.CardType;
import org.sube.project.request.card.CardRequest;

public class BenefitsRequest extends CardRequest {
    private String requestType;

    public BenefitsRequest(String documentNumber, String cardId, String requestType) {
        super(documentNumber, cardId);
        this.requestType = requestType;
    }

    public BenefitsRequest(JSONObject j) {
        super(j);
        this.requestType = j.getString("requestType");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("requestType", requestType);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}
