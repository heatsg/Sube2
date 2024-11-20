package org.sube.project.busLines;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LineManager {

    public JComboBox<String> loadBusLines() {
        List<Lines> linesArray = new ArrayList<>(List.of(Lines.values()));
        JComboBox<String> linesBox = new JComboBox<>();

        for (Lines lines : linesArray) {
            linesBox.addItem(lines.toString());
        }

        return linesBox;
    }

    public static Lines StringToLine(String line){
        return switch (line) {
            case "501" -> Lines.LINE_501;
            case "511" -> Lines.LINE_511;
            case "512" -> Lines.LINE_512;
            case "522" -> Lines.LINE_522;
            case "523" -> Lines.LINE_523;
            case "525" -> Lines.LINE_525;
            case "531" -> Lines.LINE_531;
            case "532" -> Lines.LINE_532;
            case "533" -> Lines.LINE_533;
            case "541" -> Lines.LINE_541;
            case "542" -> Lines.LINE_542;
            case "543" -> Lines.LINE_543;
            case "551" -> Lines.LINE_551;
            case "552" -> Lines.LINE_552;
            case "553" -> Lines.LINE_553;
            case "554" -> Lines.LINE_554;
            case "555" -> Lines.LINE_555;
            case "562" -> Lines.LINE_562;
            case "563" -> Lines.LINE_563;
            case "571" -> Lines.LINE_571;
            case "573" -> Lines.LINE_573;
            case "581" -> Lines.LINE_581;
            case "591" -> Lines.LINE_591;
            case "593" -> Lines.LINE_593;
            default -> null;
        };
    }
}
