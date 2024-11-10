package org.sube.project.request;

import org.json.JSONArray;
import org.sube.project.util.Path;
import org.sube.project.util.json.JSONManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestHandler<T extends Request> {

    private List<T> RequestList;

    public RequestHandler() {
        this.RequestList = new ArrayList<>();
    }

    public void addRequest(T request) {
        RequestList.add(request);
    }

    public T changeStatus(T request) {
        request.setStatus(false);
        return request;
    }

    public void takeDownRequest(T request) {
        if (RequestList.contains(request)) {
            RequestList.remove(request);
            request.setStatus(false);
            RequestList.add(request);
            RequestList.sort(Comparator.comparingInt(T::getId));
        }
    }

    public void RequestsToFile() {
        JSONArray jarr = new JSONArray();

        for (T request : RequestList) {
            jarr.put(request.toJSON());
        }

        JSONManager.write(Path.REQUEST, jarr);
    }

}
