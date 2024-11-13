package org.sube.project.front.admin.users;

import org.json.JSONArray;
import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class UserList {
    private JPanel userTotalPanel;
    private JScrollPane scrollPane;

    private JTable table1;
    private JButton actualizarButton;
    private JButton modificarButton;
    private JButton deshabilitarButton;
    private JButton verDetallesButton;
    private JButton inhabilitadosButton;
    private JTextField searchText;
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

    DefaultTableModel tableModel = new DefaultTableModel();

    public UserList(User user) {
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

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() != Utilities.getAllUsers().size()) {
                    loadUsersOnTable();
                }
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
                User user = Utilities.getUserTableByDocument(table1, tableModel, 3);
                JSONManager.updateUserStatus(user.getDocumentNumber(), false, Path.USER);
                JOptionPane.showMessageDialog(null, "Usuario dado de baja correctamente.", "Cambio de estado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userTable = Utilities.getUserTableByDocument(table1, tableModel, 3);
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
            }
        });
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
        JFrame frame = new JFrame("Lista de Usuarios");
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
