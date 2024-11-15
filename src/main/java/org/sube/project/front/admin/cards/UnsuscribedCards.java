package org.sube.project.front.admin.cards;

import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.util.Utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UnsuscribedCards {
    private JTable table1;
    private JButton actualizarButton;
    private JButton modificarButton;
    private JButton habilitarButton;
    private JTextField searchField;

    private JPanel unsuscribedCardsPanel;
    private JScrollPane scrollPane;

    DefaultTableModel tableModel = new DefaultTableModel();

    private JButton verDetallesButton;
    private JButton volverButton;
    private JLabel updatedCardsTableLabel;
    private JLabel searchNotResultsLabel;

    public UnsuscribedCards(User user) {
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo de Tarjeta");
        tableModel.addColumn("DNI Asociado");
        tableModel.addColumn("Estado");
        tableModel.addColumn("Balance");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadUnsuscribedCardsOnTable();

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadUnsuscribedCardsOnTable();
                updatedCardsTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        habilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                suscribeCard();
                JOptionPane.showMessageDialog(null, "Tarjeta rehabilitada correctamente", "Cambio de estado", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getCardTableByID(table1, tableModel, 0);
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "ID: " + card.getId() +
                                "\n" +
                                "Tipo de Tarjeta: " + card.getCardType().toString() +
                                "\n" +
                                "DNI Asociado: " + card.getDniOwner() +
                                "\n" +
                                "Estado: " + card.getStatus() +
                                "\n" +
                                "Balance: " + card.getBalance(), "Informacion de tarjeta", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(unsuscribedCardsPanel);

                CardsManagement cardsManagement = new CardsManagement(user);
                cardsManagement.showUI(true, user);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUnsuscribedCardsOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUnsuscribedCardsOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUnsuscribedCardsOnTable();
            }
        });
    }

    private void filterUnsuscribedCardsOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundCards = false;

        for (Card card : Utilities.getAllCards()) {
            if (!card.getStatus() && card.getId().toLowerCase().contains(searchText)) {
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

        if (!foundCards) {
            searchNotResultsLabel.setText("Busqueda sin resultados");
        } else {
            searchNotResultsLabel.setText("");
        }
    }


    /**
     * Metodo para dar de alta o rehabilitar una tarjeta seleccionada de la tabla.
     */
    private void suscribeCard() {
        String cardID = Utilities.getIdentifierRow(table1, tableModel, 0);

        if (cardID != null) {
            CardManager.updateCardStatus(cardID, true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una tarjeta de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para cargar todas las tarjetas hacia la tabla.
     */
    private void loadUnsuscribedCardsOnTable() {
        for (Card card : Utilities.getAllCards()) {
            if (!card.getStatus()) {
                tableModel.addRow(new Object[]{
                        card.getId(),
                        card.getCardType().toString(),
                        card.getDniOwner(),
                        card.getStatus(),
                        card.getBalance(),
                });
            }
        }
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Tarjetas");
        frame.setContentPane(unsuscribedCardsPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    CardsManagement cardsManagement = new CardsManagement(user);
                    cardsManagement.showUI(true, user);
                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
