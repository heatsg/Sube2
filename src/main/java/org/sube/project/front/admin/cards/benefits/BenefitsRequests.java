package org.sube.project.front.admin.cards.benefits;

import org.sube.project.accounts.User;
import org.sube.project.card.CardManager;
import org.sube.project.front.admin.AdminMenu;
import org.sube.project.front.main.MainMenu;
import org.sube.project.request.Request;
import org.sube.project.request.RequestHandler;
import org.sube.project.request.Requestable;
import org.sube.project.request.benefits.BenefitsRequest;
import org.sube.project.util.ImagesUtil;
import org.sube.project.util.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class BenefitsRequests {
    private JPanel benefitsRequestsPanel;
    private JTable table1;
    private JScrollPane scrollPane;

    private JButton actualizarButton;
    private JButton aceptarButton;
    private JButton denegarButton;

    private JLabel updatedTableLabel;
    private JButton verDetallesButton;
    private JButton volverButton;

    private JComboBox<String> filterBox;

    DefaultTableModel tableModel = new DefaultTableModel();

    public BenefitsRequests(User user) {
        tableModel.addColumn("ID");
        tableModel.addColumn("DNI Usuario");
        tableModel.addColumn("ID Tarjeta");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Tipo de Solicitud");

        Utilities.activateTable(table1, scrollPane, tableModel);
        loadRequestsIntoTable();

        Utilities.setImageIcon(ImagesUtil.UPDATE, actualizarButton);
        Utilities.setImageIcon(ImagesUtil.ACCEPT, aceptarButton);
        Utilities.setImageIcon(ImagesUtil.DELETE, denegarButton);
        Utilities.setImageIcon(ImagesUtil.GO_BACK, volverButton);
        Utilities.setImageIcon(ImagesUtil.CREDENTIALS, verDetallesButton);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadRequestsIntoTable();
                updatedTableLabel.setText("<html><span style='color: #08FF00'> Tabla actualizada </span></html>");
            }
        });

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String cardID = tableModel.getValueAt(selectedRow, 2).toString();
                    approveRequest(cardID);
                    loadRequestsIntoTable();
                    actualizarButton.doClick();
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
                    String cardID = tableModel.getValueAt(selectedRow, 2).toString();
                    denyRequest(cardID);
                    loadRequestsIntoTable();
                    actualizarButton.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una solicitud para denegar.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {

                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.disposeWindow(benefitsRequestsPanel);

                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.showUI(true, user);
            }
        });
    }


    private void loadRequestsIntoTable() {
        tableModel.setRowCount(0);
        List<Requestable> requests = Request.loadRequestsFromFile();

        for (Requestable request : requests) {
            if (request instanceof BenefitsRequest && ((BenefitsRequest) request).getStatus()) {
                tableModel.addRow(new Object[]{
                        ((BenefitsRequest) request).getId(),
                        ((BenefitsRequest) request).getDocumentNumber(),
                        ((BenefitsRequest) request).getCardId(),
                        ((BenefitsRequest) request).getDate(),
                        ((BenefitsRequest) request).getRequestType(),
                });
            }
        }
    }


    private void approveRequest(String cardID) {
        RequestHandler<BenefitsRequest> requestHandler = new RequestHandler<>();
        for (Requestable request : BenefitsRequest.loadRequestsFromFile()) {
            if (request instanceof BenefitsRequest && ((BenefitsRequest) request).getCardId().equals(cardID)) {
                requestHandler.takeDownRequest((BenefitsRequest) request);
                CardManager.updateCardType(((BenefitsRequest) request).getCardId(), ((BenefitsRequest) request).getRequestType());
                Request.updateRequestStatus(((BenefitsRequest) request).getId(), false);
                JOptionPane.showMessageDialog(null, "La solicitud ha sido aprobada.", "Solicitud Aprobada", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        requestHandler.requestsToFile();
    }

    private void denyRequest(String cardID) {
        RequestHandler<BenefitsRequest> requestHandler = new RequestHandler<>();
        for (Requestable request : BenefitsRequest.loadRequestsFromFile()) {
            if (request instanceof BenefitsRequest && ((BenefitsRequest) request).getCardId().equals(cardID)) {
                Request.updateRequestStatus(((BenefitsRequest) request).getId(), false);
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
        JFrame frame = new JFrame("Solicitudes de beneficios");
        frame.setContentPane(benefitsRequestsPanel);
        Utilities.getSubeFavicon(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
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
