package org.sube.project.request.card;

import org.json.JSONObject;

public class CardTakeDownRequest extends CardRequest {
    private String cardId;
    public CardTakeDownRequest(int id, String documentNumber, String cardId) {
        super(id, documentNumber,cardId);
    }
    public CardTakeDownRequest(JSONObject j) {
        super(j);
    }
}
