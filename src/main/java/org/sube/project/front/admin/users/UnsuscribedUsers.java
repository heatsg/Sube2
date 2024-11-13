package org.sube.project.front.admin.users;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.system.TransportSystem;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class UnsuscribedUsers {
    private JTextField textField1;
    private JTable table1;
    private JButton verDetallesButton;
    private JButton volverButton;
    private JButton habilitarButton;
    private JButton actualizarButton;
    private JPanel unsuscribedUsersPanel;
    private JScrollPane scrollPane;

    DefaultTableModel tableModel = new DefaultTableModel();

    public UnsuscribedUsers(User user) {
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Documento");
        tableModel.addColumn("Estado");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadUnsuscribedUsersOnTable();

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUnsuscribedUsersOnTable();
            }
        });

        habilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                suscribeUser();
                JOptionPane.showMessageDialog(null, "Usuario rehabilitado correctamente", "Cambio de estado", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(unsuscribedUsersPanel);

                UserUnsuscriber userUnsuscriber = null;
                try {
                    userUnsuscriber = new UserUnsuscriber(user);
                    userUnsuscriber.showUI(true, user);
                } catch (UserNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userTable = Utilities.getUserTableByDocument(table1, tableModel, 2);
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "Nombre: " + userTable.getName() +
                                "\n" +
                                "Apellido: " + userTable.getSurname() +
                                "\n" +
                                "Edad: " + userTable.getAge() +
                                "\n" +
                                "DNI: " + userTable.getDocumentNumber() +
                                "\n" +
                                "Genero: " + userTable.getGender() +
                                "\n" +
                                "Tipo de Usuario: " + userTable.getUserType() +
                                "\n" +
                                "Estado: " + userTable.getStatus(), "Informacion personal", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void suscribeUser() {
        String document = Utilities.getDocumentRow(table1, tableModel, 2);

        if (document != null) {
            JSONManager.updateUserStatus(document, true, Path.USER);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void loadUnsuscribedUsersOnTable() {
        JSONArray usersArray = JSONManager.readJSONArray(Path.USER);

        tableModel.setRowCount(0);
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);

            if (!user.getBoolean("status")) {
                String name = user.getString("name");
                String surname = user.getString("surname");
                String documentNumber = user.getString("documentNumber");

                tableModel.addRow(new Object[]{name, surname, documentNumber, "Inhabilitado"});
            }
        }
    }


    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Usuarios inhabilitados");
        frame.setContentPane(unsuscribedUsersPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    UserUnsuscriber userUnsuscriber = null;
                    try {
                        userUnsuscriber = new UserUnsuscriber(user);
                        userUnsuscriber.showUI(true, user);
                    } catch (UserNotFoundException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
