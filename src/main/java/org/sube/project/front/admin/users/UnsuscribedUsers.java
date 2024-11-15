package org.sube.project.front.admin.users;

import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UnsuscribedUsers {
    private JTextField searchField;
    private JTable table1;
    private JButton verDetallesButton;
    private JButton volverButton;
    private JButton habilitarButton;
    private JButton actualizarButton;
    private JPanel unsuscribedUsersPanel;
    private JScrollPane scrollPane;
    private JLabel updatedTableLabel;
    private JLabel searchNotResultsLabel;

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
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
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
                User tableUser = Utilities.getUserTableByDocument(table1, tableModel, 2);
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "Nombre: " + tableUser.getName() +
                                "\n" +
                                "Apellido: " + tableUser.getSurname() +
                                "\n" +
                                "Edad: " + tableUser.getAge() +
                                "\n" +
                                "DNI: " + tableUser.getDocumentNumber() +
                                "\n" +
                                "Genero: " + tableUser.getGender() +
                                "\n" +
                                "Tipo de Usuario: " + tableUser.getUserType() +
                                "\n" +
                                "Estado: " + tableUser.getStatus(), "Informacion personal", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUnsuscribedUsersOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUnsuscribedUsersOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUnsuscribedUsersOnTable();
            }
        });
    }

    private void filterUnsuscribedUsersOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundUsers = false;

        for (User user : Utilities.getAllUsers()) {
            if (!user.getStatus() && user.getDocumentNumber().toLowerCase().contains(searchText)) {
                tableModel.addRow(new Object[]{
                        user.getName(),
                        user.getSurname(),
                        user.getAge(),
                        user.getDocumentNumber(),
                        user.getGender(),
                        user.getUserType().toString(),
                        user.getStatus(),
                });
                foundUsers = true;
            }
        }

        if (!foundUsers) {
            searchNotResultsLabel.setText("Busqueda sin resultados");
        } else {
            searchNotResultsLabel.setText("");
        }
    }

    /**
     * Metodo para dar de alta o rehabilitar a un usuario de la tabla
     */
    private void suscribeUser() {
        String document = Utilities.getIdentifierRow(table1, tableModel, 2);

        if (document != null) {
            JSONManager.updateUserStatus(document, true, Path.USER);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para cargar Usuarios con status = false en la tabla.
     */
    private void loadUnsuscribedUsersOnTable() {
        for (User user : Utilities.getAllUsers()) {
            if (!user.getStatus()) {
                tableModel.addRow(new Object[]{
                        user.getName(),
                        user.getSurname(),
                        user.getDocumentNumber(),
                        "Inhabilitado",
                });
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
