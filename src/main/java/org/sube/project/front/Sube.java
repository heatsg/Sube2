package org.sube.project.front;

import org.sube.project.front.auth.Login;
import org.sube.project.front.auth.Register;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * SUBE: Interfaz grafica del menu principal de la aplicacion.
 */
public class Sube {
    private static Sube instance;

    private JLabel subeLogoLabel;
    private JPanel subePanel;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;

    public Sube() {
        ImageIcon imageIcon = new ImageIcon(ImagesUtil.SUBE_PATH);

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

        ImageIcon signInIcon = new ImageIcon(ImagesUtil.LOGIN_PATH);
        iniciarSesionButton.setIcon(signInIcon);

        ImageIcon signUpIcon = new ImageIcon(ImagesUtil.REGISTER_PATH);
        crearCuentaButton.setIcon(signUpIcon);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(subePanel);
                frame.dispose();

                Login login = new Login();
                login.showUI(true);
            }
        });

        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(subePanel);
                frame.dispose();

                Register register = new Register();
                register.showUI(true);
            }
        });
    }

    public static Sube getInstance() {
        if (instance == null) {
            instance = new Sube();
        }
        return instance;
    }

    /**
     * Metodo para mostrar la ventana a traves de JFrame
     *
     * @param input
     */
    public void showUI(boolean input) {
        JFrame frame = new JFrame("Sistema Único de Boleto Electrónico");
        frame.setContentPane(new Sube().subePanel);
        Utilities.getSubeFavicon(frame);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                } else {
                    showUI(true);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
