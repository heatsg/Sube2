package org.sube.project.front.admin.users;

import org.sube.project.accounts.User;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.Requestable;
import org.sube.project.request.user.UserTakeDownRequest;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static org.sube.project.accounts.authentication.UserAuthentication.getUserByDocumentNumber;

public class UserUnsuscriber {
    private JPanel userUnsuscriberPanel;
    private JTable table1;
    private JTextField searchField;
    private JScrollPane scrollPane;

    private JButton actualizarButton;
    private JButton aceptarButton;
    private JButton denegarButton;
    private JButton verDetallesButton;
    private JButton inhabilitadosButton;
    private JLabel updatedTableLabel;
    private JLabel searchNotResultsLabel;
    private JButton volverButton;

    DefaultTableModel tableModel = new DefaultTableModel();

    public UserUnsuscriber(User user) throws UserNotFoundException {
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("DNI");
        tableModel.addColumn("Estado");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadRequestsIntoTable();

        Utilities.setImageIcon(ImagesUtil.UPDATE, actualizarButton);
        Utilities.setImageIcon(ImagesUtil.ACCEPT, aceptarButton);
        Utilities.setImageIcon(ImagesUtil.DELETE, denegarButton);
        Utilities.setImageIcon(ImagesUtil.NO_VIEW, inhabilitadosButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verDetallesButton);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                try {
                    loadRequestsIntoTable();
                } catch (UserNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String dni = tableModel.getValueAt(selectedRow, 2).toString();
                    approveRequest(dni);
                    try {
                        loadRequestsIntoTable();
                    } catch (UserNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una solicitud para aceptar.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        denegarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String document = tableModel.getValueAt(selectedRow, 2).toString();
                    denyRequest(document);
                    try {
                        loadRequestsIntoTable();
                    } catch (UserNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una solicitud para denegar.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        inhabilitadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(userUnsuscriberPanel);

                UnsuscribedUsers unsuscribedUsers = new UnsuscribedUsers(user);
                unsuscribedUsers.showUI(true, user);
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if(selectedRow != -1){
                    User tableUser = Utilities.getUserTableByDocument(table1, tableModel, 2);
                    JOptionPane.showMessageDialog(null,
                            "Datos:" +
                                    "\n" + "\n" +
                                    "Nombre: " + tableUser.getName() +
                                    "\n" +
                                    "Apellido: " + tableUser.getSurname() +
                                    "\n" +
                                    "Edad: " + tableUser.getAge() +
                                    "\n" +
                                    "DNI: " + tableUser.getDocumentNumber() +
                                    "\n" +
                                    "Genero: " + tableUser.getGender() +
                                    "\n" +
                                    "Tipo de Usuario: " + tableUser.getUserType() +
                                    "\n" +
                                    "Estado: " + tableUser.getStatus(), "Informacion personal", JOptionPane.INFORMATION_MESSAGE);
                } else JOptionPane.showMessageDialog(null, "Seleccione una solicitud, por favor.", "Error", JOptionPane.WARNING_MESSAGE);

            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUserUnsuscriberOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUserUnsuscriberOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUserUnsuscriberOnTable();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(userUnsuscriberPanel);

                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.showUI(true, user);
            }
        });
    }

    private void filterUserUnsuscriberOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundUsers = false;

        for (Requestable request : Request.loadRequestsFromFile()) {
            if (request instanceof UserTakeDownRequest userRequest && userRequest.getStatus()) {
                User user = null;
                try {
                    user = getUserByDocumentNumber(userRequest.getDocumentNumber());
                } catch (UserNotFoundException e) {
                    continue;
                }

                if (user.getDocumentNumber().toLowerCase().contains(searchText)) {
                    tableModel.addRow(new Object[]{
                            user.getName(),
                            user.getSurname(),
                            user.getDocumentNumber(),
                            "Pendiente"
                    });
                    foundUsers = true;
                }
            }
        }

        if (!foundUsers) {
            searchNotResultsLabel.setText("Busqueda sin resultados");
        } else {
            searchNotResultsLabel.setText("");
        }
    }


    private void loadRequestsIntoTable() throws UserNotFoundException {
        tableModel.setRowCount(0);
        List<Requestable> requests = Request.loadRequestsFromFile();

        for (Requestable request : requests) {
            if (request instanceof UserTakeDownRequest userRequest && ((UserTakeDownRequest) request).getStatus()) {
                User user = getUserByDocumentNumber(userRequest.getDocumentNumber());
                tableModel.addRow(new Object[]{
                        user.getName(),
                        user.getSurname(),
                        user.getDocumentNumber(),
                        "Pendiente"
                });
            }
        }
    }

    private void approveRequest(String documentNumber) {
        RequestHandler<Request> requestHandler = new RequestHandler<>();
        for (Requestable request : Request.loadRequestsFromFile()) {
            if (request instanceof UserTakeDownRequest && ((UserTakeDownRequest) request).getDocumentNumber().equals(documentNumber)) {
                requestHandler.takeDownRequest((Request) request);
                JSONManager.updateUserStatus(((UserTakeDownRequest) request).getDocumentNumber(), false, Path.USER);
                requestHandler.dropUserRequests(documentNumber);
                JOptionPane.showMessageDialog(null, "La solicitud ha sido aprobada.", "Solicitud Aprobada", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

    private void denyRequest(String documentNumber) {
        RequestHandler<Request> requestHandler = new RequestHandler<>();
        for (Requestable request : Request.loadRequestsFromFile()) {
            if (request instanceof UserTakeDownRequest && ((UserTakeDownRequest) request).getDocumentNumber().equals(documentNumber)) {
                ((UserTakeDownRequest) request).setStatus(false);
                JOptionPane.showMessageDialog(null, "La solicitud ha sido denegada.", "Solicitud Denegada", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        requestHandler.requestsToFile();
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Solicitudes de baja");
        frame.setContentPane(userUnsuscriberPanel);
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
