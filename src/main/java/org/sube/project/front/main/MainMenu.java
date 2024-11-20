package org.sube.project.front.main;

import org.sube.project.accounts.User;
import org.sube.project.busLines.LineManager;
import org.sube.project.card.Card;
import org.sube.project.card.CardManager;
import org.sube.project.front.Sube;
import org.sube.project.front.main.account.Account;
import org.sube.project.front.main.bus.Bus;
import org.sube.project.front.main.card.CardManagement;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.benefits.BenefitsRequest;
import org.sube.project.request.user.UserTakeDownRequest;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {

    private JPanel menuPanel;
    private JLabel menuAccountLabel;
    private JLabel menuTitleLabel;
    private JPanel menuTitlePanel;
    private JButton miTarjetaButton;
    private JButton cerrarSesionButton;
    private JButton gestionarSaldoButton;
    private JPanel separatorPanel;
    private JLabel nameLabel;
    private JLabel documentLabel;
    private JButton gestionarCuentaButton;
    private JButton beneficiosButton;
    private JButton darDeBajaButton;
    private JButton verMisDatosButton;
    private JButton servicioDeTransporteButton;
    private JLabel cardTitleLabel;

    public MainMenu(User user) {
        Utilities.loadImage(menuAccountLabel, ImagesUtil.ACCOUNT_PATH, 50, 50);

        menuTitlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.weighty = 1.0;

        menuTitlePanel.add(menuAccountLabel, gbc);

        gbc.gridx = 1;
        menuTitlePanel.add(menuTitleLabel, gbc);

        LineManager lineManager = new LineManager();

        nameLabel.setText("<html>Nombre & Apellido: <span style='color: #00FFFF'>" + user.getName() + " " + user.getSurname() + "</span></html>");
        documentLabel.setText("<html>Documento: <span style='color: #00FFFF'>" + user.getDocumentNumber() + "</span></html>");

        Utilities.setImageIcon(ImagesUtil.ACCOUNT_MANAGEMENT_PATH, gestionarCuentaButton);
        Utilities.setImageIcon(ImagesUtil.TAKE_DOWN, darDeBajaButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verMisDatosButton);
        Utilities.setImageIcon(ImagesUtil.DOCUMENT_PATH, miTarjetaButton);
        Utilities.setImageIcon(ImagesUtil.HEART, beneficiosButton);
        Utilities.setImageIcon(ImagesUtil.BUS_PATH, servicioDeTransporteButton);
        Utilities.setImageIcon(ImagesUtil.DELETE, cerrarSesionButton);

        Card card = Utilities.getManualCard(user.getDocumentNumber());

        if (card != null) {
            beneficiosButton.setEnabled(true);
            switch (card.getCardType()) {
                case NORMAL_CARD:
                    cardTitleLabel.setText("Tarjeta");
                    beneficiosButton.setEnabled(true);
                    break;
                case STUDENT:
                    cardTitleLabel.setText("Tarjeta (Estudiantil)");
                    beneficiosButton.setEnabled(false);
                    break;

                case TEACHER:
                    cardTitleLabel.setText("Tarjeta (Docente)");
                    beneficiosButton.setEnabled(false);
                    break;

                case DISABLED_PERSON:
                    cardTitleLabel.setText("Tarjeta (Discapacitado)");
                    beneficiosButton.setEnabled(false);
                    break;

                case RETIRED:
                    cardTitleLabel.setText("Tarjeta (Jubilado)");
                    beneficiosButton.setEnabled(false);
                    break;
            }
        } else servicioDeTransporteButton.setEnabled(false);


        gestionarCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);
                Account account = new Account(user);
                account.showUI(true, user);
            }
        });

        miTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);

                CardManagement cardManagement = new CardManagement(user);
                cardManagement.showUI(true, user);
            }
        });

        beneficiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = Utilities.getManualCard(user.getDocumentNumber());

                if (card != null) {
                    JComboBox<String> benefitsBox = CardManager.loadCardBenefits();
                    int selectedOption = JOptionPane.showOptionDialog(menuPanel, benefitsBox, "Tramita un beneficio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                    if (selectedOption != JOptionPane.CLOSED_OPTION) {
                        String selectedBenefit = (String) benefitsBox.getSelectedItem();

                        RequestHandler<BenefitsRequest> benefits = new RequestHandler<>();
                        BenefitsRequest benefitsRequest = new BenefitsRequest(user.getDocumentNumber(), card.getId(), selectedBenefit);

                        benefits.addRequest(benefitsRequest);
                        benefits.requestsToFile();

                        JOptionPane.showMessageDialog(null, "Solicitud enviada correctamente.", "Solicitud", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Tarjeta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verMisDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Datos:" +
                                "\n" + "\n" +
                                "Nombre: " + user.getName() +
                                "\n" +
                                "Apellido: " + user.getSurname() +
                                "\n" +
                                "Edad: " + user.getAge() +
                                "\n" +
                                "DNI: " + user.getDocumentNumber() +
                                "\n" +
                                "Genero: " + user.getGender(), "Informacion personal", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        darDeBajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea dar de baja su cuenta?", "Confirmar Baja", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    RequestHandler<Request> requestHandler = new RequestHandler<>();
                    UserTakeDownRequest request = new UserTakeDownRequest(user.getDocumentNumber());

                    requestHandler.addRequest(request);
                    requestHandler.requestsToFile();

                    JOptionPane.showMessageDialog(null, "La solicitud de baja ha sido enviada exitosamente.", "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(menuPanel);
                Sube.getInstance().showUI(true);
            }
        });

        servicioDeTransporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> linesBox = lineManager.loadBusLines();

                int selectedOption = JOptionPane.showOptionDialog(
                        menuPanel,
                        linesBox,
                        "Selecciona una línea de transporte",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null
                );

                if (selectedOption != JOptionPane.CLOSED_OPTION) {
                    String selectedLine = (String) linesBox.getSelectedItem();

                    if (selectedLine != null) {
                        Utilities.disposeWindow(menuPanel);

                        Bus bus = new Bus(user, selectedLine);
                        bus.showUI(true, user);
                    }
                }
            }
        });

    }

    /**
     * Método para mostrar la ventana a través de JFrame
     *
     * @param input
     */
    public void showUI(boolean input, User user) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setContentPane(menuPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 450);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "¿Seguro desea salir?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    Sube.getInstance().showUI(true);
                } else {
                    showUI(true, user);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
