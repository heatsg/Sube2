package org.sube.project.request;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTakeDownRequest extends Request {
    public UserTakeDownRequest(int id, String documentNumber) {
        super(id, documentNumber);
        setRequestType(getClass().getSimpleName());
    }

    public UserTakeDownRequest(JSONObject j) {
        super(j);
        setRequestType(j.getString("RequestType"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("RequestType", getRequestType());
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}