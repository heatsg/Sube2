package org.sube.project.front.main.account;

import org.sube.project.accounts.User;
import org.sube.project.exceptions.InsuficientCharactersException;
import org.sube.project.exceptions.PasswordNotEqualsException;
import org.sube.project.exceptions.WrongPasswordException;
import org.sube.project.front.main.MainMenu;
import org.sube.project.system.TransportSystem;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Account extends JFrame {

    private JLabel gestionTitleLabel;
    private JPanel accountPanel;
    private JButton modificarNombreYApellidoButton;
    private JButton cambiarDatosPersonalesButton;
    private JButton cambiarContraseñaButton;
    private JButton backButton;
    private JPanel gestionTitlePanel;
    private JButton modificarEdadButton;
    private JButton modificarGeneroButton;
    private JTextField currentPasswordText;
    private JTextField newPasswordText;
    private JTextField confirmNewPasswordText;
    private JButton confirmarButton;
    private JButton regresarButton;
    private JPanel changePasswordPanel;
    private JPanel accountManagementPanel;
    private JButton cerrarSesionButton;
    private JButton eliminarCuentaButton;

    public Account(User user) {

        TransportSystem transportSystem = new TransportSystem();
        transportSystem.loadFromJSON();

        Object[] genderOptions = {"Masculino", "Femenino", "No binario", "Prefiero no decirlo"};

        changePasswordPanel.setVisible(false);

        Utilities.setImageIcon(ImagesUtil.EDIT, modificarNombreYApellidoButton);
        Utilities.setImageIcon(ImagesUtil.EDIT, modificarEdadButton);
        Utilities.setImageIcon(ImagesUtil.EDIT, modificarGeneroButton);
        Utilities.setImageIcon(ImagesUtil.CHANGE_PASSWORD, cambiarContraseñaButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, backButton);

        modificarNombreYApellidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentName = user.getName();
                String currentSurname = user.getSurname();

                String newName = JOptionPane.showInputDialog(accountPanel, "Modificar nombre", "Nombre", JOptionPane.PLAIN_MESSAGE, null, null, currentName).toString();
                if (newName != null && !newName.trim().isEmpty()) {
                    transportSystem.modifyUserName(newName, user);
                }

                String newSurname = JOptionPane.showInputDialog(accountPanel, "Modificar apellido", "Apellido", JOptionPane.PLAIN_MESSAGE, null, null, currentSurname).toString();
                if (newSurname != null && !newSurname.trim().isEmpty()) {
                    transportSystem.modifyUserSurname(newSurname, user);
                }

                JOptionPane.showMessageDialog(accountPanel, "Nombre y apellido actualizados exitosamente.", "Modificado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        modificarEdadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentAge = user.getAge();
                int newAge = Integer.parseInt((String) JOptionPane.showInputDialog(accountPanel, "Modificar edad", "Edad", JOptionPane.PLAIN_MESSAGE, null, null, currentAge));
                if (newAge != 0) {
                    transportSystem.modifyAge(newAge, user);
                }

                JOptionPane.showMessageDialog(null, "Edad actualizada correctamente.", "Modificado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        modificarGeneroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedOption = JOptionPane.showOptionDialog(accountPanel, "Modificar genero", "Genero", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, genderOptions, genderOptions[0]);
                transportSystem.modifyGender((String) genderOptions[selectedOption], user);
                JOptionPane.showMessageDialog(null, "Genero actualizado correctamente.", "Modificado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cambiarContraseñaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountManagementPanel.setVisible(false);
                changePasswordPanel.setVisible(true);
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(accountPanel);

                MainMenu menu = new MainMenu(user);
                menu.showUI(true, user);
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = currentPasswordText.getText();
                String newPassword = newPasswordText.getText();
                String confirmPassword = confirmNewPasswordText.getText();

                try {
                    if (currentPassword.equals(user.getPassword())) {
                        if (newPassword.length() >= 8) {
                            if (newPassword.equals(confirmPassword)) {
                                transportSystem.modifyUserPassword(newPassword, user);
                                JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente", "Modificado", JOptionPane.INFORMATION_MESSAGE);
                            } else
                                throw new PasswordNotEqualsException("Las contraseñas no coinciden");
                        } else
                            throw new InsuficientCharactersException("La contraseña no tiene los caracteres suficientes");
                    } else
                        throw new WrongPasswordException("La contraseña actual es incorrecta");

                    changePasswordPanel.setVisible(false);
                    accountManagementPanel.setVisible(true);

                } catch (WrongPasswordException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InsuficientCharactersException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (PasswordNotEqualsException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordPanel.setVisible(false);
                accountManagementPanel.setVisible(true);
            }
        });
    }

    /**
     * Metodo para mostrar la ventana a traves de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Gestionar cuenta");
        frame.setContentPane(new Account(user).accountPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    MainMenu menu = new MainMenu(user);
                    menu.showUI(true, user);
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
