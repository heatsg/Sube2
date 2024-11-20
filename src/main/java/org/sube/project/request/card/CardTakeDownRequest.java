package org.sube.project.request.card;

import org.json.JSONObject;

public class CardTakeDownRequest extends CardRequest {
    public CardTakeDownRequest(String documentNumber, String cardId) {
        super(documentNumber,cardId);
    }
    public CardTakeDownRequest(JSONObject j) {
        super(j);
    }
}
