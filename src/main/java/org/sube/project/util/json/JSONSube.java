package org.sube.project.util.json;

import org.json.JSONArray;
import org.sube.project.exceptions.MalformedIdException;
import org.sube.project.exceptions.OverflowedIdException;
import org.sube.project.util.ID_TYPE;
import org.sube.project.util.PATH;

import java.io.File;

public class JSONSube {
    private static final byte TAMANO_ID = 16 -4; // -4 por el prefijo 6061
    private static final byte[] idCounter = assignCardCounterID(); // inicializador estático

    public static int assignIDCounter(ID_TYPE idType) {
        return assignIDCounter(idType.toString());
    }

    public static int assignIDCounter(String idType) {
        if (!(new File(PATH.ID_COUNTERS.toString())).isFile()) return 0; // Si no encuentra el archivo retorna el valor inicial (0)
        return JSONManager.readJSONObject(PATH.ID_COUNTERS).getInt(idType);
    }

    public static byte[] assignCardCounterID() {
        byte[] array = new byte[TAMANO_ID];

        if (!(new File(PATH.ID_COUNTERS.toString())).isFile()) return array; // Si no encuentra el archivo retorna el valor inicial (0000 0000 0000)

        JSONArray json = JSONManager.readJSONArray(PATH.ID_COUNTERS);
        for (int i = 0; i < TAMANO_ID; i++) {
            array[i] = (byte) json.getInt(i);
        }
        return array;
    }

    /**
     * converts a byte array into a string
     * @param bytes
     * @return
     */
    public static String convertIdFormat(byte[] bytes){
        StringBuilder st = new StringBuilder();
        for (byte Byte : bytes) {
            st.append(Byte);
        }
        return st.toString();
    }

    public static String generateCardID() {
        try {
            return "6061" + convertIdFormat(generateByteArray());
        } catch (MalformedIdException | OverflowedIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Genera un número de dígitos TAMANO_ID a partir del idCounter
    private static byte[] generateByteArray() throws MalformedIdException, OverflowedIdException {
        byte i = TAMANO_ID - 1; // Busca el array en reversa para obtener el último dígito

        // Cuenta de forma análoga, si el dígito es 9 (o mayor) lo devuelve a 0 y desplaza el index
        while (i >= 0 && idCounter[i] >= 9) {
            if (idCounter[i] > 9) throw new MalformedIdException("Contador de ID de SUBE malformada en el índice " + i + ".");
            idCounter[i] = 0;
            i--;
        }
        if (i < 0) throw new OverflowedIdException("Tamaño de ID de SUBE sin espacio.");

        // Añade uno al contador
        idCounter[i]++;
        return idCounter;
    }
}
