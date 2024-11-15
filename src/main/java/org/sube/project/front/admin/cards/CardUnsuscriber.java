package org.sube.project.front.admin.cards;

import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.exceptions.UserNotFoundException;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.Requestable;
import org.sube.project.request.card.CardRequest;
import org.sube.project.request.card.CardTakeDownRequest;
import org.sube.project.request.user.UserTakeDownRequest;
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

public class CardUnsuscriber {
    private JPanel cardUnsuscriberPanel;
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

    DefaultTableModel tableModel = new DefaultTableModel();

    public CardUnsuscriber(User user) throws CardNotFoundException {
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo de Tarjeta");
        tableModel.addColumn("DNI Asociado");
        tableModel.addColumn("Estado");
        tableModel.addColumn("Balance");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadRequestsIntoTable();

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                try {
                    loadRequestsIntoTable();
                } catch (CardNotFoundException ex) {
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
                    String cardID = tableModel.getValueAt(selectedRow, 0).toString();
                    approveRequest(cardID);
                    try {
                        loadRequestsIntoTable();
                    } catch (CardNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una solicitud para aceptar.", "Error", JOptionPane.WARNING_MESSAGE);
                }

                actualizarButton.doClick();
            }
        });

        denegarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String cardID = tableModel.getValueAt(selectedRow, 0).toString();
                    denyRequest(cardID);
                    try {
                        loadRequestsIntoTable();
                    } catch (CardNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una solicitud para denegar.", "Error", JOptionPane.WARNING_MESSAGE);
                }

                actualizarButton.doClick();
            }
        });

        inhabilitadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(cardUnsuscriberPanel);

                UnsuscribedCards unsuscribedCards = new UnsuscribedCards(user);
                unsuscribedCards.showUI(true, user);
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card tableCard = Utilities.getCardTableByID(table1, tableModel, 2);
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "ID: " + tableCard.getId() +
                                "\n" +
                                "Tipo de Tarjeta: " + tableCard.getCardType().toString() +
                                "\n" +
                                "DNI Asociado: " + tableCard.getDniOwner() +
                                "\n" +
                                "Estado: " + tableCard.getStatus() +
                                "\n" +
                                "Balance: " + tableCard.getBalance(), "Informacion de tarjeta", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUnsuscriberRequestsOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUnsuscriberRequestsOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUnsuscriberRequestsOnTable();
            }
        });
    }

    private void filterUnsuscriberRequestsOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundCards = false;

        for (Requestable request : Request.loadRequestsFromFile()) {
            if (request instanceof CardTakeDownRequest cardRequest && cardRequest.getStatus()) {
                Card card = null;
                try {
                    card = CardManager.getCardByID(cardRequest.getCardId());
                } catch (CardNotFoundException e) {
                    System.out.println(e.getMessage());
                }

                if (card.getId().toLowerCase().contains(searchText)) {
                    tableModel.addRow(new Object[]{
                            card.getId(),
                            card.getCardType().toString(),
                            card.getDniOwner(),
                            card.getStatus(),
                            card.getBalance(),
                    });
                    foundCards = true;
                }
            }
        }

        if (!foundCards) {
            searchNotResultsLabel.setText("Busqueda sin resultados");
        } else {
            searchNotResultsLabel.setText("");
        }
    }

    private void loadRequestsIntoTable() throws CardNotFoundException {
        tableModel.setRowCount(0);
        List<Requestable> requests = Request.loadRequestsFromFile();

        for (Requestable request : requests) {
            if (request instanceof CardTakeDownRequest cardRequest && ((CardTakeDownRequest) request).getStatus()) {
                try {
                    Card card = CardManager.getCardByID(cardRequest.getCardId());
                    tableModel.addRow(new Object[]{
                            card.getId(),
                            card.getCardType().toString(),
                            card.getDniOwner(),
                            card.getStatus(),
                            card.getBalance(),
                    });
                } catch (CardNotFoundException e) {
                    System.out.println("Error al encontrar la tarjeta: " + e.getMessage());
                }
            }
        }
    }


    private void approveRequest(String cardID) {
        RequestHandler<CardRequest> requestHandler = new RequestHandler<>();
        for (Requestable request : CardRequest.loadRequestsFromFile()) {
            if (request instanceof CardTakeDownRequest && ((CardTakeDownRequest) request).getCardId().equals(cardID)) {
                requestHandler.takeDownRequest((CardRequest) request);
                CardManager.setCardDocumentNumber(cardID, "");
                Request.updateRequestStatus(((CardTakeDownRequest) request).getId(), false);
                JOptionPane.showMessageDialog(null, "La solicitud ha sido aprobada.", "Solicitud Aprobada", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        requestHandler.requestsToFile();
    }

    private void denyRequest(String cardID) {
        RequestHandler<CardRequest> requestHandler = new RequestHandler<>();
        for (Requestable request : CardRequest.loadRequestsFromFile()) {
            if (request instanceof CardTakeDownRequest && ((CardTakeDownRequest) request).getCardId().equals(cardID)) {
                Request.updateRequestStatus(((CardTakeDownRequest) request).getId(), false);
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
        JFrame frame = new JFrame("Gestionar usuarios");
        frame.setContentPane(cardUnsuscriberPanel);
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
