package org.sube.project.front.main;

import org.sube.project.accounts.User;
import org.sube.project.front.Sube;
import org.sube.project.front.main.account.Account;
import org.sube.project.front.main.card.CardManagement;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.user.UserTakeDownRequest;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {

    private JPanel menuPanel;
    private JLabel menuAccountLabel;
    private JLabel menuTitleLabel;
    private JPanel menuTitlePanel;
    private JButton miTarjetaButton;
    private JButton cerrarSesionButton;
    private JButton gestionarSaldoButton;
    private JPanel separatorPanel;
    private JLabel nameLabel;
    private JLabel documentLabel;
    private JButton gestionarCuentaButton;
    private JButton beneficiosButton;
    private JButton darDeBajaButton;
    private JButton verMisDatosButton;

    public MainMenu(User user) {
        Utilities.loadImage(menuAccountLabel, ImagesUtil.ACCOUNT_PATH, 50, 50);

        menuTitlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.weighty = 1.0;

        menuTitlePanel.add(menuAccountLabel, gbc);

        gbc.gridx = 1;
        menuTitlePanel.add(menuTitleLabel, gbc);

        nameLabel.setText("<html>Nombre & Apellido: <span style='color: #00FFFF'>" + user.getName() + " " + user.getSurname() + "</span></html>");
        documentLabel.setText("<html>Documento: <span style='color: #00FFFF'>" + user.getDocumentNumber() + "</span></html>");

        gestionarCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);
                Account account = new Account(user);
                account.showUI(true, user);
            }
        });

        miTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);

                CardManagement cardManagement = new CardManagement(user);
                cardManagement.showUI(true, user);
            }
        });

        beneficiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        verMisDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "Nombre: " + user.getName() +
                                "\n" +
                                "Apellido: " + user.getSurname() +
                                "\n" +
                                "Edad: " + user.getAge() +
                                "\n" +
                                "DNI: " + user.getDocumentNumber() +
                                "\n" +
                                "Genero: " + user.getGender(), "Informacion personal", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        darDeBajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea dar de baja su cuenta?", "Confirmar Baja", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    RequestHandler<Request> requestHandler = new RequestHandler<>();
                    int requestId = (int) (Math.random() * 10000);
                    UserTakeDownRequest request = new UserTakeDownRequest(requestId, user.getDocumentNumber());

                    requestHandler.addRequest(request);
                    requestHandler.requestsToFile();

                    JOptionPane.showMessageDialog(null, "La solicitud de baja ha sido enviada exitosamente.", "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);
                Sube.getInstance().showUI(true);
            }
        });


    }


    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setContentPane(menuPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 450);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    Sube.getInstance().showUI(true);
                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
