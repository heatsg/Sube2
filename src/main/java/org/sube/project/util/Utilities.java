package org.sube.project.util;

import org.json.JSONArray;
import org.sube.project.accounts.User;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void activateTable(JTable table, JScrollPane scrollPane, DefaultTableModel tableModel) {
        table.setModel(tableModel);

        scrollPane.setViewportView(table);
        table.setVisible(true);
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public static void activateTable(JTable table, DefaultTableModel tableModel) {
        table.setModel(tableModel);

        table.setVisible(true);
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public static String getDocumentRow(JTable table, DefaultTableModel tableModel, int column) {
        int selectedRow = table.getSelectedRow();
        String document = "";

        if (selectedRow != -1) {
            document = tableModel.getValueAt(selectedRow, column).toString();
        }

        return document;
    }

    public static User getUserTableByDocument(JTable table, DefaultTableModel tableModel, int column) {
        String document = Utilities.getDocumentRow(table, tableModel, column);
        User userTable = null;

        if (document != null) {
            try {
                userTable = UserAuthentication.getUserByDocumentNumber(document);
            } catch (UserNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return userTable;
    }

    public static java.util.List<User> getAllUsers() {
        JSONArray usersArray = JSONManager.readJSONArray(Path.USER);
        List<User> usersList = new ArrayList<>();

        for (int i = 0; i < usersArray.length(); i++) {
            usersList.add(new User(usersArray.getJSONObject(i)));
        }

        return usersList;
    }
}
