package org.sube.project.util.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.sube.project.accounts.User;
import org.sube.project.util.PATH;

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
        try (FileWriter file = new FileWriter(path);) {
            file.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void escribir(String path, JSONArray json) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject leerJSONObject(String path) {
        return new JSONObject(getTokener(path));
    }

    public static JSONArray leerJSONArray(String path) {
        JSONTokener tokener = getTokener(path);

        if (tokener == null) {
            System.err.println("Error al leer el JSONArray: JSONTokener es nulo.");
            return new JSONArray();
        }
        return new JSONArray(tokener);
    }


    public static JSONTokener getTokener(String path) {
        JSONTokener tokener = null;

        try {
            tokener = new JSONTokener(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado en la ruta " + path);
        }

        if (tokener == null) {
            System.err.println("Error: No se pudo crear el JSONTokener porque el archivo es nulo o no existe.");
        }
        return tokener;
    }

    public static void UserToFile(User user) {
        try {
            JSONArray jarr = JSONManager.leerJSONArray(PATH.USER);
            jarr.put(user.toJson());
            JSONManager.escribir(PATH.USER, jarr);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
    }



}
