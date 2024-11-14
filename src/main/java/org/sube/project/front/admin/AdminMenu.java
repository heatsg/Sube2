package org.sube.project.front.admin;

import org.sube.project.accounts.User;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.Sube;
import org.sube.project.front.admin.cards.CardUnsuscriber;
import org.sube.project.front.admin.cards.CardsManagement;
import org.sube.project.front.admin.users.UsersManagement;
import org.sube.project.front.admin.users.UserUnsuscriber;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminMenu {
    private JButton gestionarTarjetasButton;
    private JButton solicitudesDeBajaButton;
    private JButton volverButton;
    private JPanel adminMenuPanel;
    private JLabel nameLabel;
    private JLabel documentLabel;
    private JButton gestionarUsuariosButton;
    private JButton solicitudesDeBeneficiosButton;
    private JButton solicitudesDeBajaButton1;


    public AdminMenu(User user) {

        nameLabel.setText("<html>Nombre & Apellido: <span style='color: #00FFFF'>" + user.getName() + " " + user.getSurname() + "</span></html>");
        documentLabel.setText("<html>Documento: <span style='color: #00FFFF'>" + user.getDocumentNumber() + "</span></html>");

        solicitudesDeBajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(adminMenuPanel);
                UserUnsuscriber userManagement = null;
                try {
                    userManagement = new UserUnsuscriber(user);
                } catch (UserNotFoundException ex) {
                    ex.printStackTrace();
                }
                userManagement.showUI(true, user);
            }
        });


        gestionarTarjetasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(adminMenuPanel);

                CardsManagement cardsManagement = new CardsManagement(user);
                cardsManagement.showUI(true, user);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(adminMenuPanel);
                Sube.getInstance().showUI(true);
            }
        });

        gestionarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(adminMenuPanel);

                UsersManagement userList = new UsersManagement(user);
                userList.showUI(true, user);
            }
        });

        solicitudesDeBeneficiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        solicitudesDeBajaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(adminMenuPanel);

                CardUnsuscriber cardUnsuscriber = null;
                try {
                    cardUnsuscriber = new CardUnsuscriber(user);
                } catch (CardNotFoundException ex) {
                    ex.printStackTrace();
                }
                cardUnsuscriber.showUI(true, user);
            }
        });
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Menu Principal: Administrador");
        frame.setContentPane(adminMenuPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
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
