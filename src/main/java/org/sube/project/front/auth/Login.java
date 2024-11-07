package org.sube.project.front.auth;

import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.IncorrectCredentialsException;
import org.sube.project.front.Sube;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class Login {
    private JLabel cuentaLabel;
    private JLabel documentNumberLabel;
    private JLabel passwordLabel;
    private JButton iniciarSesionButton;
    private JPanel loginPanel;
    private JTextField documentText;
    private JPasswordField passwordText;
    private JLabel logoAccountLabel;
    private JLabel incorrectLabel;
    private JPanel loginTitlePanel;

    public Login() {
        String imagePath = "images/account.png";
        String imageDocumentPath = "images/document.png";
        String imagePasswordPath = "images/password.png";

        ImageIcon imageIcon = new ImageIcon(imagePath);
        ImageIcon documentImage = new ImageIcon(imageDocumentPath);
        ImageIcon passwordImage = new ImageIcon(imagePasswordPath);

        documentNumberLabel.setIcon(documentImage);
        passwordLabel.setIcon(passwordImage);

        if (imageIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen. Verifica la ruta del archivo.");
        } else {
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoAccountLabel.setIcon(new ImageIcon(scaledImage));
        }

        loginTitlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.weighty = 1.0;

        loginTitlePanel.add(logoAccountLabel, gbc);

        gbc.gridx = 1;
        loginTitlePanel.add(cuentaLabel, gbc);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storedDocument = documentText.getText();
                String storedPassword = Arrays.toString(passwordText.getPassword());

                try {
                    boolean signed = UserAuthentication.login(storedDocument, storedPassword);
                    if (signed) {
                        // NO HECHO TODAVIA
                        incorrectLabel.setText("");
                    } else {
                        incorrectLabel.setText("Documento o contraseña incorrectos.");
                    }

                } catch (IncorrectCredentialsException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void showUI() {
        JFrame frame = new JFrame("Iniciar sesión");
        frame.setContentPane(loginPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    Sube sube = new Sube();
                    sube.showUI(true);
                }
            }
        });
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
