package org.sube.project.front.auth;

import org.sube.project.accounts.User;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.IncorrectCredentialsException;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.Sube;
import org.sube.project.front.main.MainMenu;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        Utilities.loadImage(logoAccountLabel, ImagesUtil.ACCOUNT_PATH, 100, 100);

        Utilities.loadIcon(documentNumberLabel, ImagesUtil.DOCUMENT_PATH);
        Utilities.loadIcon(passwordLabel, ImagesUtil.PASSWORD_PATH);

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
                String storedPassword = new String(passwordText.getPassword());

                try {
                    boolean signed = UserAuthentication.login(storedDocument, storedPassword);
                    if (signed) {
                        User loggedUser = UserAuthentication.getUserByDocumentNumber(storedDocument);

                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginPanel);
                        frame.dispose();

                        MainMenu menu = new MainMenu(loggedUser);
                        menu.showUI(true, loggedUser);
                    } else {
                        JOptionPane.showMessageDialog(null, "Documento o contraseña incorrectos.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (IncorrectCredentialsException ex) {
                    System.out.println(ex.getMessage());
                } catch (UserNotFoundException ex) {

                }
            }
        });
    }

    /**
     * Metodo para mostrar la ventana a traves de JFrame
     *
     * @param input
     */
    public void showUI(boolean input) {
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
