package org.sube.project.front;

import org.sube.project.front.auth.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SUBE: Interfaz grafica del menu principal de la aplicacion.
 */
public class Sube {
    private JLabel subeLogoLabel;
    private JPanel subePanel;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;

    public Sube() {
        String imagePath = "images/SUBElogo.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        if (imageIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen. Verifica la ruta del archivo.");
        } else {
            subeLogoLabel.setIcon(imageIcon);
        }

        subePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        subePanel.add(subeLogoLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        subePanel.add(iniciarSesionButton, gbc);

        gbc.gridy = 2;
        subePanel.add(crearCuentaButton, gbc);


        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(subePanel);
                frame.dispose();

                Login login = new Login();
                login.showUI();
            }
        });

        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void showUI() {
        JFrame frame = new JFrame("Sistema Unico de Boleto Electronico");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.add(subePanel);
        frame.setVisible(true);
    }
}
