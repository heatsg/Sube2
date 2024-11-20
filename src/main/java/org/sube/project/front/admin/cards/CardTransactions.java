package org.sube.project.front.admin.cards;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sube.project.accounts.User;
import org.sube.project.busLines.Lines;
import org.sube.project.card.Card;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        tableModel.addColumn("Documento Asociado");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Fecha y hora");
        tableModel.addColumn("Monto");
        tableModel.addColumn("Linea");

        Utilities.activateTable(table1, scrollPane, tableModel);
        transactionsTitleLabel.setText("TRANSACCIONES DE " + selectedCard.getDniOwner());
        loadTransactionsOnTable(selectedCard.getId());

        Utilities.setImageIcon(ImagesUtil.UPDATE, actualizarButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verDetallesButton);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadTransactionsOnTable(selectedCard.getId());
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    String transactionID = table1.getValueAt(selectedRow, 0).toString();
                    String associatedDocument = table1.getValueAt(selectedRow, 1).toString();
                    String transactionType = table1.getValueAt(selectedRow, 2).toString();
                    String dateTime = table1.getValueAt(selectedRow, 3).toString();
                    String amount = table1.getValueAt(selectedRow, 4).toString();

                    if(table1.getValueAt(selectedRow,5).toString().isEmpty()){

                        JOptionPane.showMessageDialog(null,
                                "Datos de transaccion:" +
                                        "\n" + "\n" +
                                        "ID: " + transactionID +
                                        "\n" +
                                        "DNI Asociado: " + associatedDocument +
                                        "\n" +
                                        "Tipo: " + transactionType +
                                        "\n" +
                                        "Fecha & Hora: " + dateTime +
                                        "\n" +
                                        "Monto: " + amount, "Detalles", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String line = table1.getValueAt(selectedRow,5).toString();
                        JOptionPane.showMessageDialog(null,
                                "Datos de transaccion:" +
                                        "\n" + "\n" +
                                        "ID: " + transactionID +
                                        "\n" +
                                        "DNI Asociado: " + associatedDocument +
                                        "\n" +
                                        "Tipo: " + transactionType +
                                        "\n" +
                                        "Fecha & Hora: " + dateTime +
                                        "\n" +
                                        "Monto: " + amount +
                                        "\n" +
                                        "Linea: " + line, "Detalles", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una transaccion", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                    String transactionId = transaction.getString("id");
                    String associatedDocument = transaction.getString("dniAffiliated");
                    String transactionType = transaction.getString("transactionType");
                    String dateTime = transaction.getString("dateTime");
                    double amount = transaction.getDouble("amount");
                    String line;
                    if (transaction.getString("transactionType").equals("Recarga")){
                        line = "";
                    }else {
                        line = Lines.valueOf(transaction.getString("line")).toString();
                    }

                    tableModel.addRow(new Object[]{transactionId, associatedDocument, transactionType, dateTime, amount, line});
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
