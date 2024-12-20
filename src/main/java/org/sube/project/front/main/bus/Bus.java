package org.sube.project.front.main.bus;

import org.sube.project.accounts.User;
import org.sube.project.busLines.LineManager;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.front.main.MainMenu;
import org.sube.project.system.TransportSystem;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Bus {
    private JPanel busPanel;
    private JButton pagarBoletoButton;
    private JButton acreditarTarjetaButton;
    private JButton consultarSaldoButton;
    private JLabel lineLabel;
    private JLabel busTitleLabel;
    private JButton volverButton;

    public Bus(User user, String line) {

        TransportSystem transportSystem = new TransportSystem();
        transportSystem.loadFromJSON();

        lineLabel.setText("Linea " + line);

        busPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        busPanel.add(busTitleLabel, gbc);

        gbc.gridy = 1;
        busPanel.add(lineLabel, gbc);

        gbc.gridy = 2;
        busPanel.add(pagarBoletoButton, gbc);

        gbc.gridy = 3;
        busPanel.add(acreditarTarjetaButton, gbc);

        gbc.gridy = 4;
        busPanel.add(consultarSaldoButton, gbc);

        gbc.gridy = 5;
        busPanel.add(volverButton, gbc);

        Utilities.setImageIcon(ImagesUtil.MONEY, pagarBoletoButton);
        Utilities.setImageIcon(ImagesUtil.ACCEPT, acreditarTarjetaButton);
        Utilities.setImageIcon(ImagesUtil.SEARCH, consultarSaldoButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);

        pagarBoletoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if (card != null) {

                    if (card.getBalance() < (-1180)) {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    CardManager.payTicket(card, LineManager.StringToLine(line));

                    transportSystem.getCards().put(card.getId(), card);
                    transportSystem.updateCardsJSON();

                    JOptionPane.showMessageDialog(null,
                            "Boleto pagado" +
                                    "\n" + "\n" +
                                    "Costo: " + card.getCardType().getFinalPrice(1180) +
                                    "\n" +
                                    "Nuevo saldo: " + card.getBalance() +
                                    "\n" + "\n" +
                                    "TARJETA REGISTRADA", "Transaccion Procesada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        acreditarTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if (card != null) {
                    String cardId = card.getId();

                    System.out.println("ID de tarjeta a acreditar: " + cardId);

                    boolean credited = transportSystem.creditIntoCard(cardId);
                    if (credited) {
                        JOptionPane.showMessageDialog(busPanel, "Saldo acreditado exitosamente (Balance total: " + card.getBalance() + ")", "Acreditacion", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(busPanel, "No hay saldo pendiente para acreditar o la tarjeta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(busPanel, "Tarjeta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        consultarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if (card != null) {
                    JOptionPane.showMessageDialog(null, "Saldo actual: " + card.getBalance(), "Consulta de saldo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Tarjeta no encontrada o no registrada", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(busPanel);

                MainMenu menu = new MainMenu(user);
                menu.showUI(true, user);
            }
        });
    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Colectivo");
        frame.setContentPane(busPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 450);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    MainMenu mainMenu = new MainMenu(user);
                    mainMenu.showUI(true, user);
                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
