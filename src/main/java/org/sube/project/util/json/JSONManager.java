package org.sube.project.util.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.sube.project.util.Path;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class JSONManager {
    public static void write(Path path, JSONObject json) {
        write(path.toString(), json);
    }

    public static void write(Path path, JSONArray json) {
        write(path.toString(), json);
    }

    public static JSONObject readJSONObject(Path path) {
        return readJSONObject(path.toString());
    }

    public static JSONArray readJSONArray(Path path) {
        return readJSONArray(path.toString());
    }

    public static void write(String path, JSONObject json) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String path, JSONArray json) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJSONObject(String path) {
        return new JSONObject(getTokener(path));
    }

    public static JSONArray readJSONArray(String path) {
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

    public static <T extends JSONCompatible> void objectToFile(T obj, Path path, boolean overwrite) {
        JSONArray jarr;
        if (overwrite)
            jarr = new JSONArray();
        else
            jarr = JSONManager.readJSONArray(path);

        try {
            jarr.put(obj.toJSON());
            JSONManager.write(path, jarr);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
    }

    public static <T extends JSONCompatible> void collectionToFile(Collection<T> collection, Path path, boolean overwrite) {
        JSONArray jarr;
        if (overwrite)
            jarr = new JSONArray();
        else
            jarr = JSONManager.readJSONArray(path);
        try {
            for (T obj : collection)
                jarr.put(obj.toJSON());
            JSONManager.write(path, jarr);
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
            jx.printStackTrace();
        }
    }
}
