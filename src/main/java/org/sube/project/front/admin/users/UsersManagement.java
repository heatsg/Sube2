package org.sube.project.front.admin.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.card.Card;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class UsersManagement {
    private JPanel userTotalPanel;
    private JScrollPane scrollPane;

    private JTable table1;
    private JButton actualizarButton;
    private JButton modificarButton;
    private JButton deshabilitarButton;
    private JButton verDetallesButton;
    private JButton inhabilitadosButton;
    private JTextField searchField;
    private JButton volverButton;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField ageField;
    private JTextField userTypeField;
    private JTextField statusField;
    private JTextField passwordField;
    private JTextField documentField;
    private JComboBox<String> genderBox;
    private JButton confirmarButton;
    private JPanel modifyPanel;
    private JPanel userListPanel;
    private JLabel updatedUsersTableLabel;
    private JLabel searchNotResultsLabel;

    DefaultTableModel tableModel = new DefaultTableModel();

    public UsersManagement(User user) {
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Edad");
        tableModel.addColumn("Documento");
        tableModel.addColumn("Genero");
        tableModel.addColumn("Tipo de Usuario");
        tableModel.addColumn("Estado");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadUsersOnTable();

        genderBox.addItem("Masculino");
        genderBox.addItem("Femenino");
        genderBox.addItem("No binario");

        modifyPanel.setVisible(false);

        Utilities.setImageIcon(ImagesUtil.UPDATE, actualizarButton);
        Utilities.setImageIcon(ImagesUtil.EDIT, modificarButton);
        Utilities.setImageIcon(ImagesUtil.TAKE_DOWN, deshabilitarButton);
        Utilities.setImageIcon(ImagesUtil.NO_VIEW, inhabilitadosButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verDetallesButton);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadUsersOnTable();
                updatedUsersTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    User selectedUser = Utilities.getUserTableByDocument(table1, tableModel, 3);
                    userListPanel.setVisible(false);
                    modifyPanel.setVisible(true);

                    nameField.setText(selectedUser.getName());
                    surnameField.setText(selectedUser.getSurname());
                    ageField.setText(String.valueOf(selectedUser.getAge()));
                    documentField.setText(selectedUser.getDocumentNumber());
                    genderBox.setSelectedItem(selectedUser.getGender());
                    userTypeField.setText(String.valueOf(selectedUser.getUserType()));
                    statusField.setText(String.valueOf(selectedUser.getStatus()));
                    passwordField.setText(selectedUser.getPassword());
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Usuario a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deshabilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        User user = Utilities.getUserTableByDocument(table1, tableModel, 3);
                        JSONManager.updateUserStatus(user.getDocumentNumber(), false, Path.USER);
                        JOptionPane.showMessageDialog(null, "Usuario dado de baja correctamente.", "Cambio de estado", JOptionPane.INFORMATION_MESSAGE);
                        actualizarButton.doClick();
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User tableUser = Utilities.getUserTableByDocument(table1, tableModel, 3);
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

        inhabilitadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(userTotalPanel);

                UnsuscribedUsers users = new UnsuscribedUsers(user);
                users.showUI(true, user);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(userTotalPanel);

                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.showUI(true, user);
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = getAllModifiedFields();

                Map<String, User> users = new HashMap<>();
                JSONArray usersArray = JSONManager.readJSONArray(Path.USER);

                for (int i = 0; i < usersArray.length(); i++) {
                    users.put(usersArray.getJSONObject(i).getString("documentNumber"), new User(usersArray.getJSONObject(i)));
                }

                users.put(user.getDocumentNumber(), user);
                JSONManager.collectionToFile(users.values(), Path.USER, true);

                JOptionPane.showMessageDialog(null, "Usuario modificado correctamente.", "Modificado", JOptionPane.INFORMATION_MESSAGE);
                modifyPanel.setVisible(false);
                userListPanel.setVisible(true);

                actualizarButton.doClick();
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUsersOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUsersOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUsersOnTable();
            }
        });
    }

    /**
     * Metodo para filtrar en la busqueda de Usuarios por documento
     */
    private void filterUsersOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundUsers = false;

        for (User user : Utilities.getAllUsers()) {
            if (user.getDocumentNumber().toLowerCase().contains(searchText)) {
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
            searchNotResultsLabel.setText("Busqueda sin resultados.");
        } else {
            searchNotResultsLabel.setText("");
        }
    }


    private User getAllModifiedFields() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String document = documentField.getText();
        String gender = (String) genderBox.getSelectedItem();
        UserType userType = UserType.valueOf(userTypeField.getText());
        boolean status = Boolean.parseBoolean(statusField.getText());
        String password = passwordField.getText();

        return new User(name, surname, age, document, gender, userType, status, password);
    }

    private void loadUsersOnTable() {
        for (User user : Utilities.getAllUsers()) {
            tableModel.addRow(new Object[]{
                    user.getName(),
                    user.getSurname(),
                    user.getAge(),
                    user.getDocumentNumber(),
                    user.getGender(),
                    user.getUserType(),
                    user.getStatus()});
        }
    }


    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Usuarios");
        frame.setContentPane(userTotalPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    AdminMenu adminMenu = new AdminMenu(user);
                    adminMenu.showUI(true, user);
                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
