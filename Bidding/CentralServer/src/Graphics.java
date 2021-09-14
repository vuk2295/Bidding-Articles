import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Graphics extends JFrame {

    private JPanel panelUI;
    private JButton startButton;
    private JButton stopButton;
    private JLabel serverLabel;
    private JLabel serverPortLabel;
    private JTextField serverPort;
    private JLabel serverPortLabel2;
    private JTextField serverPort2;

    Registry serverRegistry = null;

    public Graphics() {
        super("CENTRAL SERVER");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(460, 450);
        ServerUI();
        setVisible(true);

    }

    public static void main(String[] args) throws RemoteException {

        Registry registry = LocateRegistry.createRegistry(8080);
        registry.rebind("MyServer", new ServerOperation());

        Graphics serverGraphics = new Graphics();
        serverGraphics.getStartButton().addActionListener(e -> {
            serverGraphics.startCentralServer();
        });

        serverGraphics.getStopButton().addActionListener(e -> {
            serverGraphics.stopCentralServer();
        });
    }

    void startCentralServer() {

        Integer serverPort = null;
        Integer clientPort = null;
        try {
            serverPort = Integer.parseInt(getServerPort().getText());
            clientPort = Integer.parseInt(getServerPort2().getText());

            startButton.setEnabled(false);
            getServerPort().setEnabled(false);
            serverPort2.setVisible(false);
            stopButton.setEnabled(true);
            infoBox("Central Server is connected! ", "CENTRAL SERVER");

        } catch (Exception e1) {
            infoBox("Server error: Invalid ports entered! ", "WARNING");
            return;

        }
    }

    void stopCentralServer() {
        try {
            startButton.setEnabled(true);
            serverPort.setEnabled(true);
            serverPort2.setVisible(false);
            stopButton.setEnabled(false);


            System.out.println("Server is stopping....");
            serverRegistry.unbind("Server");
            UnicastRemoteObject.unexportObject(serverRegistry, true);
            infoBox("Server is stopped! ", "SERVER");

            System.out.println(serverRegistry);
        } catch (Exception e1) {
            infoBox("Server error: Invalid ports entered! ", "WARNING");
            return;

        }
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void ServerUI() {


        panelUI = new JPanel();
        panelUI.setSize(450, 450);
        panelUI.setBackground(Color.BLACK);
        panelUI.setVisible(true);
        panelUI.setLayout(null);
        add(panelUI);

        startButton = new JButton("START SERVER");
        startButton.setBounds(50, 300, 150, 35);
        startButton.setBackground(Color.BLUE);
        startButton.setForeground(Color.WHITE);
        startButton.setLayout(null);
        startButton.setVisible(true);
        panelUI.add(startButton);

        stopButton = new JButton("STOP SERVER");
        stopButton.setBounds(250, 300, 150, 35);
        stopButton.setBackground(Color.RED);
        stopButton.setForeground(Color.WHITE);
        stopButton.setEnabled(false);
        stopButton.setVisible(true);
        panelUI.add(stopButton);

        serverLabel = new JLabel("CENTRAL SERVER");  // Label for Server Title
        serverLabel.setForeground(Color.WHITE);
        serverLabel.setFont(new Font("Serif", Font.CENTER_BASELINE, 35));
        serverLabel.setBounds(55, 30, 384, 40);
        panelUI.add(serverLabel);

        serverPortLabel = new JLabel("SERVER PORT");     // Label for SERVER PORT
        serverPortLabel.setForeground(Color.WHITE);
        serverPortLabel.setBounds(85, 160, 96, 14);
        serverPortLabel.setVisible(true);
        panelUI.add(serverPortLabel);

        serverPort = new JTextField();
        serverPort.setText("8080");
        serverPort.setBounds(50, 200, 150, 25);
        serverPort.setVisible(true);
        panelUI.add(serverPort);


        serverPortLabel2 = new JLabel("CLIENT PORT");     // Label for CLIENT PORT
        serverPortLabel2.setForeground(Color.WHITE);
        serverPortLabel2.setBounds(285, 160, 96, 14);
        serverPortLabel2.setVisible(false);
        panelUI.add(serverPortLabel2);

        serverPort2 = new JTextField();
        serverPort2.setText("9090");
        serverPort2.setBounds(250, 200, 150, 25);
        serverPort2.setVisible(false);
        panelUI.add(serverPort2);


    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JTextField getServerPort() {
        return serverPort;
    }

    public JTextField getServerPort2() {
        return serverPort2;
    }

}
