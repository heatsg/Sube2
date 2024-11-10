package org.sube.project.util.json;

import org.sube.project.util.Path;

import java.io.File;

public class JSONSube {
    private static final byte TAMANO_ID = 16 -4; // -4 por el prefijo 6061

    public static int assignTransactionIDCounter() {
        if (!(new File(Path.ID_COUNTERS.toString())).isFile()) return 0; // Si no encuentra el archivo retorna el valor inicial (0)
        return JSONManager.readJSONObject(Path.ID_COUNTERS).getInt("transaction");
    }

    public static String generateRandomCardID() {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < TAMANO_ID; i++) {
            st.append((int) (Math.random() * 8 + 1));
        }

        return "6061" + st;
    }
}
