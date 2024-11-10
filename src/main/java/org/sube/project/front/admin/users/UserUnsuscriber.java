package org.sube.project.front.admin.users;

import org.sube.project.accounts.User;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.Requestable;
import org.sube.project.request.UserTakeDownRequest;
import org.sube.project.util.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static org.sube.project.accounts.authentication.UserAuthentication.getUserByDocumentNumber;

public class UserUnsuscriber {
    private JPanel userManagementPanel;
    private JTable table1;
    private JTextField textField1;
    private JScrollPane scrollPane;

    private JButton actualizarButton;
    private JButton aceptarButton;
    private JButton denegarButton;
    private JButton verDetallesButton;
    private JButton inhabilitadosButton;

    DefaultTableModel tableModel = new DefaultTableModel();

    public UserUnsuscriber(User user) {
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("DNI");
        Utilities.activateTable(table1, scrollPane, tableModel);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        denegarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para denegar solicitud
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para ver detalles
            }
        });
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Gestionar usuarios");
        frame.setContentPane(userManagementPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
