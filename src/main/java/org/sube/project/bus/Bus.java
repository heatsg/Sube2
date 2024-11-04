package org.sube.project.bus;

import org.json.JSONException;
import org.json.JSONObject;

public class Bus {
    public Lines line;
    public int capacity;
    public boolean disabled;

    public Bus(Lines line, int capacity, boolean disabled) {
        this.line = line;
        this.capacity = capacity;
        this.disabled = disabled;
    }

    public Lines getLine() {
        return line;
    }

    public void setLine(Lines line) {
        this.line = line;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public JSONObject toJson() {
        try {
            JSONObject object = new JSONObject();
            object.put("line", line);
            object.put("capacity", capacity);
            object.put("disabled", disabled);
            return object;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Bus convertToBus(JSONObject object) {
        Lines line = Lines.valueOf(String.valueOf(object.getInt("line")));
        int capacity = object.getInt("capacity");
        boolean disabled = object.getBoolean("disabled");

        return new Bus(line, capacity, disabled);
    }
}
