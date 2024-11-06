package org.sube.project.util;

import javax.swing.*;

public class Utilities {

    public static void getSubeFavicon(JFrame frame) {
        String faviconPath = "images/SUBEfavicon.png";
        ImageIcon icon = new ImageIcon(faviconPath);
        frame.setIconImage(icon.getImage());
    }
}
