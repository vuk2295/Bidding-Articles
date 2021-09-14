import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Graphics extends JFrame {

    public JPanel panelUI;
    public JButton connectButton;
    public JButton disconnectButton;
    public JLabel serverLabel;
    public JTextField serverIP;
    public JTextField serverPort;
    public JLabel serverIPLabel;
    public JLabel serverPortLabel;
    private static ServerInterface look_up;

    public Graphics() {
        super("SERVER");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(460, 450);
        ServerUI();
        setVisible(true);

    }
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) throws RemoteException {
        Graphics serverGraphics = new Graphics();
        serverGraphics.setVisible(true);


    }
    void connectServer(){

        serverIP.setEnabled(false);
        serverPort.setEnabled(false);
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
        infoBox("Connection successfully! ", "SERVER");

    }
    void disconnectServer(){

        serverIP.setEnabled(true);
        serverPort.setEnabled(true);
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        infoBox("Server is disconnected! ", "SERVER");
    }

    public void ServerUI() {


        panelUI = new JPanel();
        panelUI.setSize(450, 450);
        panelUI.setBackground(Color.BLACK);
        panelUI.setVisible(true);
        panelUI.setLayout(null);
        add(panelUI);


        connectButton = new JButton("CONNECT");
        connectButton.setBounds(50, 350, 150, 35);
        connectButton.setBackground(Color.BLUE);
        connectButton.setForeground(Color.WHITE);
        connectButton.setLayout(null);
        connectButton.setVisible(true);
        panelUI.add(connectButton);

        connectButton.addActionListener(e->{


            try {
                Registry registry = LocateRegistry.createRegistry(5099);
                registry.rebind("MyServer", new ServerOperation());
                connectServer();
                System.out.println("Connected");
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

        });


        disconnectButton = new JButton("DISCONNECT");
        disconnectButton.setBounds(250, 350, 150, 35);
        disconnectButton.setBackground(Color.RED);
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setLayout(null);
        disconnectButton.setVisible(true);
        disconnectButton.setEnabled(false);
        panelUI.add(disconnectButton);

        disconnectButton.addActionListener(e->{
            try {
                disconnectServer();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        });

        serverLabel = new JLabel("SERVER");
        serverLabel.setForeground(Color.WHITE);
        serverLabel.setFont(new Font("Serif", Font.CENTER_BASELINE, 35));
        serverLabel.setBounds(150, 30, 350, 40);
        panelUI.add(serverLabel);

        serverIPLabel = new JLabel("SERVER IP");
        serverIPLabel.setForeground(Color.WHITE);
        serverIPLabel.setBounds(90, 200, 114, 14);
        panelUI.add(serverIPLabel);

        serverPortLabel = new JLabel("SERVER PORT");
        serverPortLabel.setForeground(Color.WHITE);
        serverPortLabel.setBounds(280, 200, 96, 14);
        panelUI.add(serverPortLabel);

        serverIP=new JTextField();
        serverIP.setText("localhost");
        serverIP.setBounds(50,250,150,25);
        serverIP.setVisible(true);
        panelUI.add(serverIP);

        serverPort=new JTextField();
        serverPort.setText("8080");
        serverPort.setBounds(250,250,150,25);
        serverPort.setVisible(true);
        panelUI.add(serverPort);


    }
}
