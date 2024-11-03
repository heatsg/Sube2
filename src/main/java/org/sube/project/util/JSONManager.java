package org.sube.project.util;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONManager {
    public static void escribir(PATH path, JSONObject json) {
        escribir(path.toString(), json);
    }
    public static void escribir(PATH path, JSONArray json) {
        escribir(path.toString(), json);
    }
    public static JSONObject leerJSONObject(PATH path) {
        return leerJSONObject(path.toString());
    }
    public static JSONArray leerJSONArray(PATH path) {
        return leerJSONArray(path.toString());
    }

    public static void escribir(String path, JSONObject json) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(json.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void escribir(String path, JSONArray json) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(json.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject leerJSONObject(String path) {
        return new JSONObject(getTokener(path));
    }
    public static JSONArray leerJSONArray(String path) {
        return new JSONArray(getTokener(path));
    }

    public static JSONTokener getTokener(String path) {
        JSONTokener tokener = null;

        try {
            tokener = new JSONTokener(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }


}
