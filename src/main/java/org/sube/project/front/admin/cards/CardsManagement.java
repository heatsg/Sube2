package org.sube.project.front.admin.cards;

import jdk.jshell.execution.Util;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

public class CardsManagement {
    private JTable table1;
    private JButton actualizarButton;
    private JButton modificarButton;
    private JButton deshabilitarButton;
    private JTextField searchField;
    private JPanel cardsManagementPanel;
    private JScrollPane scrollPane;
    private JButton verDetallesButton;
    private JButton inhabilitadosButton;
    private JButton volverButton;
    private JLabel updatedTableLabel;
    private JButton verTransaccionesButton;
    private JLabel searchNotResultsLabel;
    private JButton añadirTarjetasButton;

    DefaultTableModel tableModel = new DefaultTableModel();

    public CardsManagement(User user) {
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo de Tarjeta");
        tableModel.addColumn("DNI Asociado");
        tableModel.addColumn("Estado");
        tableModel.addColumn("Balance");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadCardsOnTable();

        String[] cardTypes = {"NORMAL_CARD", "STUDENT", "TEACHER", "DISABLED_PERSON", "RETIRED"};

        Utilities.setImageIcon(ImagesUtil.UPDATE, actualizarButton);
        Utilities.setImageIcon(ImagesUtil.ADD, añadirTarjetasButton);
        Utilities.setImageIcon(ImagesUtil.EDIT, modificarButton);
        Utilities.setImageIcon(ImagesUtil.TRANSACTION, verTransaccionesButton);
        Utilities.setImageIcon(ImagesUtil.TAKE_DOWN, deshabilitarButton);
        Utilities.setImageIcon(ImagesUtil.NO_VIEW, inhabilitadosButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verDetallesButton);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadCardsOnTable();
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    Card card = Utilities.getCardTableByID(table1, tableModel, 0);
                    String associatedDocument = card.getDniOwner();

                    if (associatedDocument != null) {
                        int selectedOption = JOptionPane.showOptionDialog(cardsManagementPanel, "Modificar tipo de tarjeta", "Tarjeta", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, cardTypes, cardTypes[0]);
                        String newDocument = JOptionPane.showInputDialog(cardsManagementPanel, "Modificar documento asociado", "Tarjeta", JOptionPane.PLAIN_MESSAGE, null, null, associatedDocument).toString();

                        CardManager.setCardDocumentNumber(card.getId(), newDocument);
                        CardManager.updateCardType(card.getId(), cardTypes[selectedOption]);

                        actualizarButton.doClick();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        deshabilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    Card card = Utilities.getCardTableByID(table1, tableModel, 0);
                    if (card != null && card.getStatus()) {
                        CardManager.updateCardStatus(card.getId(), false);
                        JOptionPane.showMessageDialog(null, "Tarjeta dada de baja correctamente", "Cambio de estado", JOptionPane.INFORMATION_MESSAGE);
                        actualizarButton.doClick();
                    } else {
                        JOptionPane.showMessageDialog(null, "Esta tarjeta ya ha sido dada de baja", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    Card card = Utilities.getCardTableByID(table1, tableModel, 0);
                    if(!card.getDniOwner().isEmpty())
                        JOptionPane.showMessageDialog(null,
                                "Datos:" +
                                        "\n" + "\n" +
                                        "Numero: " + card.getId() +
                                        "\n" +
                                        "Tipo de Tarjeta: " + card.getCardType().toString() +
                                        "\n" +
                                        "Documento Asociado: " + card.getDniOwner() +
                                        "\n" +
                                        "Balance actual: " + card.getBalance(), "Informacion de tarjeta", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null,
                            "Datos:" +
                                    "\n" + "\n" +
                                    "Numero: " + card.getId() +
                                    "\n" +
                                    "Tipo de Tarjeta: " + card.getCardType().toString() +
                                    "\n" +
                                    "Documento Asociado: No tiene" +
                                    "\n" +
                                    "Balance actual: " + card.getBalance(), "Informacion de tarjeta", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        inhabilitadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(cardsManagementPanel);
                UnsuscribedCards unsuscribedCards = new UnsuscribedCards(user);
                unsuscribedCards.showUI(true, user);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(cardsManagementPanel);

                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.showUI(true, user);
            }
        });

        verTransaccionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    Card selectedCard = Utilities.getCardTableByID(table1, tableModel, 0);
                    if (!selectedCard.getTransactionHistory().isEmpty()) {
                        Utilities.disposeWindow(cardsManagementPanel);

                        CardTransactions cardTransactions = new CardTransactions(user, selectedCard);
                        cardTransactions.showUI(true, user);
                    } else {
                        JOptionPane.showMessageDialog(null, "La tarjeta seleccionada no tiene transacciones asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterCardsOnTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterCardsOnTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterCardsOnTable();
            }
        });


        añadirTarjetasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(JOptionPane.showInputDialog(cardsManagementPanel, "Ingrese la cantidad a crear", "Tarjeta", JOptionPane.PLAIN_MESSAGE, null, null, null).toString());

                if (amount != -1) {
                    CardManager.generateEmptyCards(amount);
                    actualizarButton.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Metodo para filtrar en la busqueda de tarjetas por ID
     */
    private void filterCardsOnTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        boolean foundCards = false;

        for (Card card : Utilities.getAllCards()) {
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

        if (!foundCards) {
            searchNotResultsLabel.setText("Busqueda sin resultados.");
        } else {
            searchNotResultsLabel.setText("");
        }
    }


    private void loadCardsOnTable() {
        for (Card card : Utilities.getAllCards()) {
            tableModel.addRow(new Object[]{
                    card.getId(),
                    card.getCardType().toString(),
                    card.getDniOwner(),
                    card.getStatus(),
                    card.getBalance(),
            });
        }
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Tarjetas");
        frame.setContentPane(cardsManagementPanel);
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
