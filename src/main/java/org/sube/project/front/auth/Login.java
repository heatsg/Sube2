package org.sube.project.front.auth;

import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.IncorrectCredentialsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login {
    private JLabel iniciarSesionLabel;
    private JLabel documentNumberLabel;
    private JLabel passwordLabel;
    private JButton iniciarSesionButton;
    private JPanel loginPanel;
    private JTextField documentText;
    private JPasswordField passwordText;
    private JLabel logoAccountLabel;
    private JLabel incorrectLabel;

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
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Redimensiona la imagen a 50x50 píxeles
            logoAccountLabel.setIcon(new ImageIcon(scaledImage));

        }

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
        JFrame frame = new JFrame("Iniciar sesion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.add(loginPanel);
        frame.setVisible(true);
    }
}
