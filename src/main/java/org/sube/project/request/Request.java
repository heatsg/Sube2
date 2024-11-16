package org.sube.project.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.request.benefits.BenefitsRequest;
import org.sube.project.request.card.CardTakeDownRequest;
import org.sube.project.request.user.UserTakeDownRequest;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONCompatible;
import org.sube.project.util.json.JSONManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Request implements JSONCompatible, Requestable {
    private String id;
    private LocalDateTime date;
    private String documentNumber;
    private String RequestType;
    private boolean status;

    public Request(String documentNumber) {
        this.id = String.valueOf((int) (Math.random() * 10000));

        this.date = LocalDateTime.now();
        this.documentNumber = documentNumber;
        this.status = true;
    }

    public Request(String documentNumber, String dateTime) {
        this.id = String.valueOf(Math.random() * 10000);

        this.date = LocalDateTime.parse(dateTime);
        this.documentNumber = documentNumber;
        this.status = true;
    }

    public Request(JSONObject j) {
        this.id = j.getString("id");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        this.date = LocalDateTime.parse(j.getString("date"), formatter);
        this.documentNumber = j.getString("documentNumber");
        this.status = j.getBoolean("status");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", this.id);
            j.put("date", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")));
            j.put("documentNumber", this.documentNumber);
            j.put("status", this.status);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return j;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss"));
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
            if (jarr.getJSONObject(i).getString("requestType").equals("UserTakeDownRequest")) {
                requestList.add(new UserTakeDownRequest(jarr.getJSONObject(i)));
            } else if (jarr.getJSONObject(i).getString("requestType").equals("CardTakeDownRequest")) {
                requestList.add(new CardTakeDownRequest(jarr.getJSONObject(i)));
            } else {
                requestList.add(new BenefitsRequest(jarr.getJSONObject(i)));
            }
        }

        return requestList;
    }

    public static void updateRequestStatus(String id, boolean status) {
        JSONArray requestArray = JSONManager.readJSONArray(Path.REQUEST);

        for (int i = 0; i < requestArray.length(); i++) {
            JSONObject request = requestArray.getJSONObject(i);

            if (request.getString("id").equals(id)) {
                request.put("status", status);
                break;
            }
        }
        JSONManager.write(Path.REQUEST, requestArray);
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
