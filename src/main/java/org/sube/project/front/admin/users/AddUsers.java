package org.sube.project.front.admin.users;

import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.exceptions.UserAlreadyExistsException;
import org.sube.project.front.Sube;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.system.TransportSystem;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class AddUsers {

    private JButton añadirButton;
    private JButton vaciarTodosLosCamposButton;
    private JPanel addUsersPanel;
    private JLabel documentNumberLabel;
    private JTextField nameText;
    private JTextField surnameText;
    private JComboBox<String> genderBox;
    private JTextField ageText;
    private JTextField documentNumberText;
    private JPasswordField passText;
    private JLabel logoRegisterLabel;
    private JLabel registerLabel;
    private JPanel registerTitlePanel;
    private JButton volverButton;
    private JComboBox<UserType> userTypeBox;

    public AddUsers(User user) {
        Utilities.loadImage(logoRegisterLabel, ImagesUtil.ACCOUNT_PATH, 50, 50);

        registerTitlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.weighty = 1.0;

        registerTitlePanel.add(logoRegisterLabel, gbc);

        gbc.gridx = 1;
        registerTitlePanel.add(registerLabel, gbc);

        generateItemsGenderBox();

        Utilities.setImageIcon(ImagesUtil.SEND, añadirButton);
        Utilities.setImageIcon(ImagesUtil.TRASH, vaciarTodosLosCamposButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);

        userTypeBox.addItem(UserType.NORMAL_USER);
        userTypeBox.addItem(UserType.ADMIN);

        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportSystem transportSystem = new TransportSystem();

                String name = nameText.getText();
                String surname = surnameText.getText();
                int age = Integer.parseInt(ageText.getText());
                String documentNumber = documentNumberText.getText();
                String password = new String(passText.getPassword());
                UserType userType = (UserType) userTypeBox.getSelectedItem();

                try {
                    if (documentNumber.length() != 8) {
                        JOptionPane.showMessageDialog(null, "El documento debe tener 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (password.length() < 8) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese una contraseña mayor a 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (!validateGenderBox()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un género.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {
                        transportSystem.loadFromJSON();
                        User newUser = new User(name, surname, age, documentNumber, (String) genderBox.getSelectedItem(), userType, true, password);
                        transportSystem.registerUser(newUser);
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.", "Registrado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (UserAlreadyExistsException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        vaciarTodosLosCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(Objects.equals(genderBox.getSelectedItem(), "<Seleccionar>"))) {
                    genderBox.setSelectedItem("<Seleccionar>");
                    clearAllFields();
                }
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(addUsersPanel);
                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.showUI(true, user);
            }
        });
    }

    private boolean validateGenderBox() {
        if (Objects.equals(genderBox.getSelectedItem(), "<Seleccionar>")) {
            return false;
        }
        return true;
    }

    private void clearAllFields() {
        nameText.setText("");
        surnameText.setText("");
        ageText.setText("");
        documentNumberText.setText("");
        passText.setText("");
    }

    private void generateItemsGenderBox() {
        genderBox.addItem("<Seleccionar>");
        genderBox.addItem("Masculino");
        genderBox.addItem("Femenino");
        genderBox.addItem("No binario");
        genderBox.addItem("Prefiero no decirlo");
    }

    /**
     * Metodo para mostrar la ventana a traves de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Añadir usuarios");
        frame.setContentPane(new AddUsers(user).addUsersPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}