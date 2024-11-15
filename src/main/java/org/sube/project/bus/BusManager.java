package org.sube.project.bus;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BusManager {

    public JComboBox<String> loadBusLines() {
        List<Lines> linesArray = new ArrayList<>(List.of(Lines.values()));
        JComboBox<String> linesBox = new JComboBox<>();

        for (Lines lines : linesArray) {
            linesBox.addItem(lines.toString());
        }

        return linesBox;
    }
}
