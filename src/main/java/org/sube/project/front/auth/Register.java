package org.sube.project.front.auth;

import org.sube.project.front.Sube;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Register {

    private JButton registrarseButton;
    private JButton vaciarTodosLosCamposButton;
    private JPanel registerPanel;
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

    public Register() {

        // Path de Imagenes
        String imagePath = "images/account.png";
        // Path de Imagenes

        ImageIcon imageIcon = new ImageIcon(imagePath);

        if (imageIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen. Verifica la ruta del archivo.");
        } else {
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoRegisterLabel.setIcon(new ImageIcon(scaledImage));
        }

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

        // Gender Box Items //
        genderBox.addItem("<Seleccionar>");
        genderBox.addItem("Masculino");
        genderBox.addItem("Femenino");
        genderBox.addItem("No binario");
        genderBox.addItem("Prefiero no decirlo.");


        // Botones //
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateGenderBox();
            }
        });

        vaciarTodosLosCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void validateGenderBox() {
        if (Objects.equals(genderBox.getSelectedItem(), "<Seleccionar>")) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un genero.");
        }
    }

    /**
     * Metodo para mostrar la ventana a traves de JFrame
     *
     * @param input
     */
    public void showUI(boolean input) {
        JFrame frame = new JFrame("Registrate");
        frame.setContentPane(new Register().registerPanel);
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
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
