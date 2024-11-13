package org.sube.project.front.main.card;

import org.json.JSONArray;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.exceptions.CardNotFoundException;
import org.sube.project.front.main.MainMenu;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.card.CardTakeDownRequest;
import org.sube.project.request.user.UserTakeDownRequest;
import org.sube.project.system.TransportSystem;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CardManagement {

    private JPanel cardManagementPanel;
    private JLabel balanceInfoLabel;
    private JLabel cardNumberLabel;
    private JButton cargarSaldoButton;
    private JButton acreditarTarjetaButton;
    private JButton darDeBajaButton;
    private JButton volverButton;
    private JPanel cardTitleManagementPanel;
    private JButton registrarTarjetaButton;

    public CardManagement(User user) {

        String[] rechargeValues = {"2000", "3000", "5000"};

        if (registeredCard(user.getDocumentNumber())) {
            Card card = getCard(user.getDocumentNumber());
            cardNumberLabel.setText("Numero: " + card.getId());
            balanceInfoLabel.setText("Saldo actual: " + card.getBalance());
            registrarTarjetaButton.setVisible(false);
        } else {
            balanceInfoLabel.setVisible(false);
            cardNumberLabel.setText("No hay tarjeta asociada.");
            registrarTarjetaButton.setVisible(true);
        }

        cargarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = getCard(user.getDocumentNumber());

                int selectedOption = JOptionPane.showOptionDialog(cardManagementPanel, "Elija el monto a cargar", "Cargar saldo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, rechargeValues, rechargeValues[0]);
                if (card != null) {
                    TransportSystem.getInstance().addUncreditedAmount(card.getId(), Double.parseDouble(rechargeValues[selectedOption]));
                    //CardManager.addBalance(card, Double.parseDouble(rechargeValues[selectedOption]));
                    CardManager.updateCardFile(card);
                }
            }
        });

        acreditarTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportSystem transportSystem = new TransportSystem();
                transportSystem.loadFromJSON();

                Card card = getCard(user.getDocumentNumber());

                if (card != null) {
                    String cardId = card.getId();

                    System.out.println("ID de tarjeta a acreditar: " + cardId);

                    boolean credited = transportSystem.creditIntoCard(cardId);
                    if (credited) {
                        JOptionPane.showMessageDialog(cardManagementPanel, "Saldo acreditado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        balanceInfoLabel.setText("Saldo actual: " + card.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(cardManagementPanel, "No hay saldo pendiente para acreditar o la tarjeta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(cardManagementPanel, "Tarjeta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        darDeBajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea dar la tarjeta?", "Confirmar Baja", JOptionPane.YES_NO_OPTION);
                Card card = getCard(user.getDocumentNumber());
                if (confirm == JOptionPane.YES_OPTION) {
                    RequestHandler<Request> requestHandler = new RequestHandler<>();
                    int requestId = (int) (Math.random() * 10000);
                    CardTakeDownRequest request= new CardTakeDownRequest(requestId, user.getDocumentNumber(), card.getId());

                    requestHandler.addRequest(request);
                    requestHandler.requestsToFile();

                    JOptionPane.showMessageDialog(null, "La solicitud de baja ha sido enviada exitosamente.", "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(cardManagementPanel);

                MainMenu menu = new MainMenu(user);
                menu.showUI(true, user);
            }
        });

        registrarTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog(cardManagementPanel, "Por favor, ingrese el numero de tarjeta", "Tarjeta", JOptionPane.PLAIN_MESSAGE, null, null, null).toString();
                    if (validateCardID(id)) {
                        CardManager.setCardDocumentNumber(id, user.getDocumentNumber());
                        registrarTarjetaButton.setVisible(false);
                    } else {
                        throw new CardNotFoundException("Numero de tarjeta no existente");
                    }

                    Card card = getCard(user.getDocumentNumber());
                    if (card != null) {
                        cardNumberLabel.setText("Numero: " + card.getId());
                        balanceInfoLabel.setVisible(true);
                        balanceInfoLabel.setText("Saldo actual: " + card.getBalance());
                    }
                } catch (CardNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    private boolean registeredCard(String documentNumber) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("dniOwner").equals(documentNumber)){
                return true;
            }
        }
        return false;
    }

    private Card getCard(String documentNumber) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("dniOwner").equals(documentNumber)){
                return new Card(array.getJSONObject(i));
            }
        }
        return null;
    }

    private boolean validateCardID(String id) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("id").equals(id) && array.getJSONObject(i).getString("dniOwner").equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }



    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setContentPane(cardManagementPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 450);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    MainMenu menu = new MainMenu(user);
                    menu.showUI(true, user);

                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
