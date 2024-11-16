package org.sube.project.request.user;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.request.Request;

public class UserTakeDownRequest extends Request {
    public UserTakeDownRequest(String documentNumber) {
        super(documentNumber);
        setRequestType(getClass().getSimpleName());
    }

    public UserTakeDownRequest(JSONObject j) {
        super(j);
        setRequestType(j.getString("requestType"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = super.toJSON();
        try {
            j.put("requestType", getRequestType());
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }
}
