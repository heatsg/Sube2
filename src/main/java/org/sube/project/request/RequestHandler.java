package org.sube.project.request;

import org.json.JSONArray;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestHandler<T extends Request> {

    private List<T> requestList;

    public RequestHandler() {
        this.requestList = new ArrayList<>();
    }

    public void addRequest(T request) {
        requestList.add(request);
    }

    public T changeStatus(T request) {
        request.setStatus(false);
        return request;
    }

    public void takeDownRequest(T request) {
        if (requestList.contains(request)) {
            requestList.remove(request);
            request.setStatus(false);
            requestList.add(request);
            requestList.sort(Comparator.comparing(T::getId));
        }
    }

    public void requestsToFile() {
        JSONArray jarr = JSONManager.readJSONArray(Path.REQUEST);

        for (T request : requestList) {
            jarr.put(request.toJSON());
        }

        JSONManager.write(Path.REQUEST, jarr);
    }

    private void updateRequestFile() {
        JSONArray jarr = new JSONArray();

        for (T request : requestList) {
            jarr.put(request.toJSON());
        }

        JSONManager.write(Path.REQUEST, jarr);
    }

    public void dropUserRequests(String documentNumber) {
        requestList.clear();

        for (Requestable request : Request.loadRequestsFromFile()) {
            requestList.add((T) request);
        }

        for (Request request : requestList) {
            if (request.getDocumentNumber().equals(documentNumber)) {
                request.setStatus(false);
            }
        }

        updateRequestFile();
    }

}
