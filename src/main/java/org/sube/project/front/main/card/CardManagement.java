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
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Path;
import org.sube.project.util.Utilities;
import org.sube.project.util.json.JSONManager;

import javax.swing.*;
import java.awt.*;
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

        TransportSystem transportSystem = new TransportSystem();
        transportSystem.loadFromJSON();

        String[] rechargeValues = {"2000", "3000", "5000"};

        if (registeredCard(user.getDocumentNumber())) {
            Card card = Utilities.getManualCard(user.getDocumentNumber());
            if (card != null) {
                cardNumberLabel.setText("Numero: " + card.getId());
                balanceInfoLabel.setText("Saldo actual: " + card.getBalance());
                registrarTarjetaButton.setVisible(false);
                cargarSaldoButton.setEnabled(true);
                acreditarTarjetaButton.setEnabled(true);
                darDeBajaButton.setEnabled(true);
            }
        } else {
            balanceInfoLabel.setVisible(false);
            cardNumberLabel.setText("No hay tarjeta asociada.");
            registrarTarjetaButton.setVisible(true);
            cargarSaldoButton.setEnabled(false);
            acreditarTarjetaButton.setEnabled(false);
            darDeBajaButton.setEnabled(false);
        }

        Utilities.setImageIcon(ImagesUtil.EDIT_1, registrarTarjetaButton);
        Utilities.setImageIcon(ImagesUtil.ADD, cargarSaldoButton);
        Utilities.setImageIcon(ImagesUtil.ACCEPT, acreditarTarjetaButton);
        Utilities.setImageIcon(ImagesUtil.TAKE_DOWN, darDeBajaButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);

        cargarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());
                if (card != null) {
                    int selectedOption = JOptionPane.showOptionDialog(cardManagementPanel, "Elija el monto a cargar", "Cargar saldo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, rechargeValues, rechargeValues[0]);
                    transportSystem.addUncreditedAmount(card.getId(), Double.parseDouble(rechargeValues[selectedOption]));
                    transportSystem.loadFromJSON();
                } else JOptionPane.showMessageDialog(cardManagementPanel, "Tarjeta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        acreditarTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if (card != null) {
                    String cardId = card.getId();


                    boolean credited = transportSystem.creditIntoCard(cardId);
                    if (credited) {
                        JOptionPane.showMessageDialog(cardManagementPanel, "Saldo acreditado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        transportSystem.loadFromJSON();
                        balanceInfoLabel.setText("Saldo actual: " + transportSystem.getCards().get(card.getId()).getBalance());
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
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if(card!=null){
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea dar de baja la tarjeta?", "Confirmar Baja", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        RequestHandler<Request> requestHandler = new RequestHandler<>();
                        CardTakeDownRequest request = new CardTakeDownRequest(user.getDocumentNumber(), card.getId());

                        requestHandler.addRequest(request);
                        requestHandler.requestsToFile();

                        JOptionPane.showMessageDialog(null, "La solicitud de baja ha sido enviada exitosamente.", "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else
                    JOptionPane.showMessageDialog(cardManagementPanel, "Tarjeta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
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
                        transportSystem.loadFromJSON();
                        cargarSaldoButton.setEnabled(true);
                        acreditarTarjetaButton.setEnabled(true);
                        darDeBajaButton.setEnabled(true);
                        registrarTarjetaButton.setVisible(false);
                    } else {
                        throw new CardNotFoundException("Numero de tarjeta no existente");
                    }

                    Card card = Utilities.getManualCard(user.getDocumentNumber());

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
            if (array.getJSONObject(i).getString("dniOwner").equals(documentNumber)) {
                return true;
            }
        }
        return false;
    }


    private boolean validateCardID(String id) {
        JSONArray array = JSONManager.readJSONArray(Path.CARD);

        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("id").equals(id)
                    && array.getJSONObject(i).getString("dniOwner").equalsIgnoreCase("")
                    && array.getJSONObject(i).getBoolean("status")) {
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
