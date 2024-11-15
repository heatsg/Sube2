package org.sube.project.front.admin.cards;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
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

public class CardTransactions {
    private JTable table1;
    private JButton verDetallesButton;
    private JButton volverButton;
    private JButton habilitarButton;
    private JButton actualizarButton;
    private JPanel cardTransactionsPanel;
    private JScrollPane scrollPane;
    private JLabel updatedTableLabel;
    private JLabel transactionsTitleLabel;

    DefaultTableModel tableModel = new DefaultTableModel();

    public CardTransactions(User user, Card selectedCard) {
        tableModel.addColumn("ID de Transaccion");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Fecha y hora");
        tableModel.addColumn("Monto");

        Utilities.activateTable(table1, scrollPane, tableModel);
        transactionsTitleLabel.setText("TRANSACCIONES DE " + selectedCard.getDniOwner());
        loadTransactionsOnTable(selectedCard.getId());

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadTransactionsOnTable(selectedCard.getId());
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(cardTransactionsPanel);

                CardsManagement cardsManagement = new CardsManagement(user);
                cardsManagement.showUI(true, user);
            }
        });
    }

    private void loadTransactionsOnTable(String cardId) {
        JSONArray cardsArray = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject card = cardsArray.getJSONObject(i);

            if (card.getString("id").equals(cardId)) {
                JSONArray transactionHistory = card.getJSONArray("transactionHistory");

                for (int j = 0; j < transactionHistory.length(); j++) {
                    JSONObject transaction = transactionHistory.getJSONObject(j);

                    int transactionId = transaction.getInt("id");
                    String transactionType = transaction.getString("transactionType");
                    String dateTime = transaction.getString("dateTime");
                    double amount = transaction.getDouble("amount");

                    tableModel.addRow(new Object[]{transactionId, transactionType, dateTime, amount});
                }
                return;
            }
        }
    }


    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Transacciones");
        frame.setContentPane(cardTransactionsPanel);
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
