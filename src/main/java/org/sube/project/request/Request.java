package org.sube.project.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Request implements JSONCompatible, Requestable {
    private int id;
    private LocalDateTime date;
    private String documentNumber;
    private String RequestType;
    private boolean status;

    public Request(int id, String documentNumber) {
        this.id = id;
        this.date = LocalDateTime.now();
        this.documentNumber = documentNumber;
        this.status = true;
    }

    public Request(JSONObject j) {
        this.id = j.getInt("id");
        this.date = LocalDateTime.parse(j.getString("date"));
        this.documentNumber = j.getString("documentNumber");
        this.status = j.getBoolean("status");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", this.id);
            j.put("date", this.date);
            j.put("documentNumber", this.documentNumber);
            j.put("status", this.status);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static List<Requestable> loadRequestsFromFile() {
        JSONArray jarr = new JSONArray(JSONManager.readJSONArray(Path.REQUEST));

        List<Requestable> requestList = new ArrayList<>();

        for (int i = 0; i < jarr.length(); i++) {

            if (jarr.getJSONObject(i).getString("RequestType").equals("UserTakeDownRequest")) {

                requestList.add(new UserTakeDownRequest(jarr.getJSONObject(i)));

            } else

                requestList.add(new CardTakeDownRequest(jarr.getJSONObject(i)));
        }

        return requestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return id == request.id && Objects.equals(date, request.date) && Objects.equals(documentNumber, request.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, documentNumber);
    }


}
