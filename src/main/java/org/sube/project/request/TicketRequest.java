package org.sube.project.request;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.card.CardType;

public class TicketRequest extends CardRequest{
    private CardType requestType;

    public TicketRequest(int id, String documentNumber, String cardId,CardType requestType){
        super(id, documentNumber,cardId);
        this.requestType=requestType;
    }

    public TicketRequest(JSONObject j){
        super(j);
        this.requestType=CardType.valueOf(j.getString("requestType"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("requestType",requestType);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}
