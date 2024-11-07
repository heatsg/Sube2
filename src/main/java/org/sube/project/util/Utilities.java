package org.sube.project.util;

import javax.swing.*;
import java.awt.*;

public class Utilities {

    /**
     * Metodo para cargar el "favicon" de Sube para las ventanas e interfaces.
     *
     * @param frame
     */
    public static void getSubeFavicon(JFrame frame) {
        String faviconPath = "images/SUBEfavicon.png";
        ImageIcon icon = new ImageIcon(faviconPath);
        frame.setIconImage(icon.getImage());
    }

    /**
     * Metodo para cerrar ventanas, totalmente reutilizable.
     *
     * @param panel
     */
    public static void disposeWindow(JPanel panel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.dispose();
    }

    /**
     * Metodo para cargar imagenes a traves de un "path".
     *
     * @param label
     * @param imagePath
     * @param width
     * @param height
     */
    public static void loadImage(JLabel label, String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(String.valueOf(imagePath));

        if (imageIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen. Verifica la ruta del archivo.");
        } else {
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        }
    }

    /**
     * Metodo para cargar iconos a traves de un "path".
     *
     * @param label
     * @param imagePath
     */
    public static void loadIcon(JLabel label, String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label.setIcon(imageIcon);
    }
}
