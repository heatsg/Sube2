package org.sube.project.bus;

import org.json.JSONException;
import org.json.JSONObject;
import org.sube.project.util.json.JSONCompatible;

public class Bus implements JSONCompatible {
    public Lines line;
    public int capacity;
    public boolean disabled;

    public Bus(Lines line, int capacity, boolean disabled) {
        this.line = line;
        this.capacity = capacity;
        this.disabled = disabled;
    }

    public Bus(JSONObject j) {
        this.line = Lines.valueOf(String.valueOf(j.getInt("line")));
        this.capacity = j.getInt("capacity");
        this.disabled = j.getBoolean("disabled");
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

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();

        try {
            j.put("line", line);
            j.put("capacity", capacity);
            j.put("disabled", disabled);
        } catch (JSONException jx) {
            System.err.println(jx.getMessage());
            jx.printStackTrace();
        }

        return j;
    }

}
