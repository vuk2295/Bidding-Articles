import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Graphics extends JFrame {


    private static ServerInterface look_up;
    private DataBaseConnector sqlConnector = DataBaseConnector.getInstanca();
    private JPanel panelUI;
    private JTextField usernameLogin;
    private JTextField usernameSignUP;
    private JPasswordField passwordLogin;
    private JTextArea textArea;
    private JPasswordField passwordSignUP;
    private JPasswordField passwordSignUPConfirm;
    private JButton backToHome;
    private JButton backToLogin;
    private JButton newuserB;
    private JButton loginB;
    private JButton bid;
    private JButton btnShowArticles;
    private JButton addArticle;
    private JLabel sellArticle;
    private JLabel nameOfArticle;
    private JLabel labelforarticles;
    private JLabel usernameLoginLabel;
    private JLabel passwordLoginLabel;
    private JLabel usernameSignUPnLabel;
    private JLabel passwordSignUPLabel;
    private JLabel passwordSignUPLabelConfirm;
    private JLabel lblWelcome;
    private JLabel labelForListArticles;
    private JComponent lblInformation2;
    private JComponent lblInformation3;
    private JComboBox comboBoxArticles;
    private JComboBox getComboBoxArticlesAdd;
    private JTextField nameOfArticleTF;
    private JLabel modelOfArticle;
    private JTextField modelOfArticleTF;
    private JLabel priceOfArticle;
    private JTextField priceOfArticleTF;
    private JLabel comboBoxArticleLabel;
    private JLabel descriptionOfArticle;
    private JTextArea descriptionOfArticleTF;
    private JLabel remainingTimeOfArticle;
    private JTextField remainingTimeOfArticleTF;
    private JPanel showArticlesFromBase;
    private JScrollPane scrollPane;
    private JLabel bidSelectedArticle;
    private JButton BID;
    private JButton buyNow;
    private JLabel lastBidLbl;
    private JButton addCreditCardBtn;
    private JTextField numberOfCreditCardTF;
    private JLabel numberOfCreditCardLbl;
    private JTextField moneyOnCreditCardTF;
    private JLabel moneyOnCreditCardLbl;
    private JButton addMoneyBtn;
    private JLabel yourMoneyOnCreditCardLbl;
    private JLabel ulogovanikorisnik;
    private JLabel afterBuyArticleLbl;
    private JButton inputMoneyBtn;
    private JButton btnboughtArticles;


    public Graphics() {
        super("CLIENT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(460, 450);
        ClientUI();
        setVisible(true);
        setResizable(false);

    }


    public static void main(String[] args) {

        DataBaseConnector sqlConnector = DataBaseConnector.getInstanca();
        Graphics cg = new Graphics();
        cg.setVisible(true);
        cg.scrollPane.setVisible(false);


        // SIGN UP
        cg.newuserB.addActionListener(e -> {
            try {
                look_up = (ServerInterface) Naming.lookup("//localhost:5099/MyServer");
            } catch (NotBoundException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

            try {
                // Pozivanje metode registerUser sa ServerInterface na PodServeru (Server)
                look_up.registerUser(cg.usernameSignUP.getText(), cg.passwordSignUP.getPassword(), cg.passwordSignUPConfirm.getPassword());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        cg.loginB.addActionListener(e -> {

            try {
                look_up = (ServerInterface) Naming.lookup("//localhost:5099/MyServer");
            } catch (NotBoundException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

            try {
                boolean response = look_up.loginUser(new ServerOperation(), cg.usernameLogin.getText(), cg.passwordLogin.getPassword()); // Zasto sam morao da stavim new ServerOperation??????

                if (response) {

                    cg.addCreditCardBtn.setVisible(true);
                    cg.passwordLogin.setVisible(false);
                    cg.passwordLoginLabel.setVisible(false);
                    cg.usernameLogin.setVisible(false);
                    cg.usernameLoginLabel.setVisible(false);
                    cg.backToLogin.setVisible(false);
                    cg.lblInformation2.setVisible(false);
                    cg.loginB.setVisible(false);
                    cg.lblWelcome.setVisible(false);
                    cg.btnShowArticles.setVisible(true);
                    cg.labelforarticles.setVisible(true);
                    cg.comboBoxArticles.setVisible(true);
                    cg.labelForListArticles.setVisible(true);
                    cg.scrollPane.setVisible(true);
                    cg.lblInformation3.setVisible(true);
                    cg.showArticlesFromBase.setVisible(true);
                    cg.backToHome.setVisible(true);
                    cg.btnboughtArticles.setVisible(true);
                    cg.btnboughtArticles.setText("Your bought articles");

                    cg.btnboughtArticles.addActionListener(e11 -> {
                        cg.btnboughtArticles.setVisible(false);
                        cg.addCreditCardBtn.setVisible(false);
                        cg.buyNow.setVisible(false);
                        cg.backToHome.setVisible(true);
                        cg.backToHome.setBounds(10, 385, 260, 30);
                        cg.backToHome.addActionListener(e21 -> {
                            cg.showArticlesFromBase.removeAll();
                            cg.btnboughtArticles.setVisible(true);

                        });

                        cg.lblInformation3.setVisible(false);
                        cg.btnShowArticles.setVisible(false);
                        cg.comboBoxArticleLabel.setVisible(false);
                        cg.comboBoxArticles.setVisible(false);
                        cg.labelforarticles.setText("YOUR BOUGHT ARTICLES");
                        cg.scrollPane.setVisible(true);


                        java.util.List<Articles> listOfBoughtArticles = new ArrayList<>();
                        try {
                            listOfBoughtArticles = look_up.getListOfBoughtArticles(cg.ulogovanikorisnik.getText());
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        cg.showArticlesFromBase.removeAll();
                        cg.showArticlesFromBase.revalidate();
                        cg.showArticlesFromBase.repaint();
                        cg.showArticlesFromBase.setLayout(new BoxLayout(cg.showArticlesFromBase, 1));

                        for (Articles article : listOfBoughtArticles) {
                            String category = article.getCategory();
                            String name = article.getName();
                            String model = article.getModel();
                            Double price = article.getPrice();
                            String description = article.getDescription();


                            JLabel categorylbl = new JLabel("Category: " + category);

                            cg.showArticlesFromBase.add(categorylbl);

                            JLabel namelbl = new JLabel("Name: " + name);

                            cg.showArticlesFromBase.add(namelbl);

                            JLabel modelbl = new JLabel("Model: " + model);

                            cg.showArticlesFromBase.add(modelbl);

                            JLabel pricelbl = new JLabel("Price: " + price);

                            cg.showArticlesFromBase.add(pricelbl);

                            JLabel descriptionlbl = new JLabel("Description: " + description);

                            cg.showArticlesFromBase.add(descriptionlbl);
                        }
                    });
                    cg.ulogovanikorisnik.setText(cg.usernameLogin.getText());

                    JPanel panelUI1 = new JPanel();
                    panelUI1.setBackground(Color.WHITE);
                    panelUI1.setBounds(360, 110, 350, 350);
                    panelUI1.setVisible(false);
                    panelUI1.setLayout(null);
                    cg.panelUI.add(panelUI1);
                    cg.setSize(800, 460);

                    cg.addCreditCardBtn.addActionListener(e3 -> {
                        cg.creditCard(false);
                        cg.inputMoneyBtn.setEnabled(true);
                        cg.moneyOnCreditCardTF.setEditable(true);
                        cg.btnboughtArticles.setVisible(false);


                        Long[] array = new Long[3];
                        try {
                            array = look_up.getNumberCard(cg.ulogovanikorisnik.getText());
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }

                        if (array[0] == 1) {
                            String ulogovani = cg.usernameLogin.getText();
                            Long moneyCard = array[1];
                            Long numberCard = array[2];
                            cg.yourMoneyOnCreditCardLbl.setText("Ulogovani korisnik: " + ulogovani + " status novca: " + moneyCard + " broj kartice: " + numberCard);
                            if (!(numberCard == null)) {
                                cg.moneyOnCreditCardLbl.setVisible(true);
                                cg.moneyOnCreditCardTF.setVisible(true);
                                cg.inputMoneyBtn.setVisible(true);
                                cg.numberOfCreditCardLbl.setVisible(false);
                                cg.numberOfCreditCardTF.setVisible(false);
                                cg.addMoneyBtn.setVisible(false);
                                cg.inputMoneyBtn.addActionListener(e2 -> {
                                    Long moneyInput = Long.parseLong(cg.moneyOnCreditCardTF.getText().trim());
                                    if (moneyInput != 0) {
                                        try {
                                            look_up.addMoney(cg.ulogovanikorisnik.getText(), moneyCard, numberCard, moneyInput);
                                        } catch (RemoteException ex) {
                                            ex.printStackTrace();
                                        }
                                        cg.moneyOnCreditCardTF.setText("");
                                        cg.inputMoneyBtn.setEnabled(false);
                                        cg.moneyOnCreditCardTF.setEditable(false);
                                    } else {
                                        JFrame parent2 = new JFrame();
                                        JOptionPane.showMessageDialog(parent2, "You did not input a money ( 0 ) !");

                                    }
                                });
                            }
                        } else {
                            cg.creditCard(false);
                            cg.addMoneyBtn.addActionListener(e2 -> {
                                Long[] array2 = new Long[3];
                                try {
                                    array2 = look_up.addNumber(cg.ulogovanikorisnik.getText(), Long.parseLong(cg.numberOfCreditCardTF.getText()));
                                } catch (RemoteException ex) {
                                    ex.printStackTrace();
                                }
                                infoMessage("Number of credit card is correct", "Credit Card INFO");
                                String ulogovani = cg.usernameLogin.getText();
                                Long moneyCard = array2[1];
                                Long numberCard = array2[2];
                                cg.yourMoneyOnCreditCardLbl.setText("Ulogovani korisnik: " + ulogovani + " status novca: " + moneyCard + " broj kartice: " + numberCard);
                            });
                        }
                    });
                    cg.btnShowArticles.addActionListener(e1 -> {

                        try {
                            String phones = "Phones";
                            String tablets = "Tablets";
                            String value = (String) cg.comboBoxArticles.getSelectedItem();

                            java.util.List<Articles> listOfArticles = look_up.getListOfArticles(value);

                            cg.showArticlesFromBase.removeAll();
                            cg.showArticlesFromBase.revalidate();
                            cg.showArticlesFromBase.repaint();
                            cg.showArticlesFromBase.setLayout(new BoxLayout(cg.showArticlesFromBase, 1));

                            var ref = new Object() {
                                boolean test = false;
                            };

                            for (Articles article : listOfArticles) {

                                Long id = article.getId();
                                String category = article.getCategory();
                                String name = article.getName();
                                String model = article.getModel();
                                Double price = article.getPrice();
                                String description = article.getDescription();
                                String time = article.getTime();
                                String dateOfCreate = article.getDateOfCreate();


                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date dateOfCreateDate = sdf.parse(dateOfCreate);
                                Date currentDate = new Date();

                                long diff = currentDate.getTime() - dateOfCreateDate.getTime();
                                long diffInSeconds = diff / 1000;
                                long timeLicitationInSeconds = Long.parseLong(time) * 86400;
                                long mainDiffInSeconds = timeLicitationInSeconds - diffInSeconds;
                                System.out.println(name + " " + diffInSeconds);
                                System.out.println(timeLicitationInSeconds);
                                System.out.println(mainDiffInSeconds);
                                int day = (int) TimeUnit.SECONDS.toDays(mainDiffInSeconds);
                                final long[] remainHours = {mainDiffInSeconds / 3600};
                                long m = TimeUnit.SECONDS.toMinutes(mainDiffInSeconds) - (TimeUnit.SECONDS.toHours(mainDiffInSeconds) * 60);
                                long s = TimeUnit.SECONDS.toSeconds(mainDiffInSeconds) - (TimeUnit.SECONDS.toMinutes(mainDiffInSeconds) * 60);

                                JLabel categorylbl = new JLabel("Category: " + category);

                                cg.showArticlesFromBase.add(categorylbl);

                                JLabel namelbl = new JLabel("Name: " + name);

                                cg.showArticlesFromBase.add(namelbl);

                                JLabel modelbl = new JLabel("Model: " + model);

                                cg.showArticlesFromBase.add(modelbl);

                                JLabel pricelbl = new JLabel("Price: " + price);

                                cg.showArticlesFromBase.add(pricelbl);

                                JLabel descriptionlbl = new JLabel("Description: " + description);

                                cg.showArticlesFromBase.add(descriptionlbl);

                                JLabel hourLbl = new JLabel("Hours: " + remainHours[0]);
                                JLabel minutesLbl = new JLabel("Minutes: " + "00");
                                JLabel secondsLbl = new JLabel("Seconds: " + "00");
                                cg.showArticlesFromBase.add(hourLbl);
                                cg.showArticlesFromBase.add(minutesLbl);
                                cg.showArticlesFromBase.add(secondsLbl);

                                final boolean[] flag = {true};
                                /******/
                                final long[] min = {m};
                                final long[] sec = {s};
                                JButton bidbtn = new JButton("BID");
                                bidbtn.setName(id + "");
                                Timer timer = null;
                                Timer finalTimer = timer;
                                bidbtn.setEnabled(false);
                                timer = new Timer(1000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        if (mainDiffInSeconds > 0) {
                                            bidbtn.setEnabled(true);
                                            if (sec[0] <= 0 && min[0] <= 0 && remainHours[0] <= 0) {
                                                bidbtn.setText("Time is up for BID");
                                                bidbtn.setEnabled(false);

                                                try {
                                                    look_up.buyArticleAfterTimeIsUp(cg.ulogovanikorisnik.getText(), id);
                                                } catch (RemoteException ex) {
                                                    ex.printStackTrace();
                                                }

                                                if (finalTimer != null)
                                                    finalTimer.stop();
                                            }

                                            if (sec[0] == 0 && min[0] > 0) {
                                                sec[0] = 60;
                                                min[0]--;
                                            }

                                            if (sec[0] == 0 && min[0] == 0 && remainHours[0] > 0) {
                                                min[0] = 59;
                                                sec[0] = 60;
                                                remainHours[0]--;
                                            }
                                            if (min[0] == 0 && sec[0] == 0) {
                                                min[0] = 0;
                                                sec[0] = 0;
                                            }
                                            if (sec[0] != 0) {
                                                sec[0]--;
                                            }


                                            if (sec[0] < 10) {
                                                secondsLbl.setText("Seconds: 0" + sec[0]);
                                                flag[0] = false;
                                            } else {
                                                secondsLbl.setText("Seconds: " + sec[0]);
                                                flag[0] = false;
                                            }
                                            if (min[0] < 10) {
                                                minutesLbl.setText("MInutes: 0" + min[0]);
                                                flag[0] = false;
                                            } else {
                                                minutesLbl.setText("MInutes: " + min[0]);
                                                flag[0] = false;
                                            }
                                            if (remainHours[0] < 10) {
                                                hourLbl.setText("Hours: 0" + remainHours[0]);
                                                flag[0] = false;
                                            } else {
                                                hourLbl.setText("Hours: " + remainHours[0]);
                                                flag[0] = false;
                                            }
                                            if (flag[0]) {
                                                minutesLbl.setText("Minutes: " + min[0]);
                                                secondsLbl.setText("Seconds: " + sec[0]);
                                                hourLbl.setText("Hours: " + remainHours[0]);
                                            }


                                        } else {
                                            bidbtn.setText("Time is up for BID");
                                            bidbtn.setEnabled(false);

                                        }
                                    }
                                });
                                timer.start();

                                /******/

                                bidbtn.addActionListener(e2 -> {
                                    ref.test = true;
                                    cg.btnboughtArticles.setVisible(false);
                                    System.out.println("ID; " + id + " - " + bidbtn.getName());
                                    cg.addCreditCardBtn.setVisible(false);
                                    cg.scrollPane.setVisible(false);
                                    cg.comboBoxArticleLabel.setVisible(false);
                                    cg.comboBoxArticles.setVisible(false);
                                    cg.lblInformation3.setVisible(false);
                                    cg.btnShowArticles.setVisible(false);
                                    cg.labelForListArticles.setVisible(false);
                                    cg.backToHome.setVisible(true);
                                    cg.bidSelectedArticle.setVisible(true);
                                    cg.BID.setVisible(true);
                                    cg.buyNow.setVisible(true);
                                    cg.lastBidLbl.setVisible(true);

                                    Articles articleById = null;
                                    try {
                                        articleById = look_up.getArticleById(Long.parseLong(bidbtn.getName()));
                                    } catch (RemoteException ex) {
                                        ex.printStackTrace();
                                    }

                                    String sql = "";
                                    Long price2 = 0L;

                                    String category2 = articleById.getCategory();
                                    String name2 = articleById.getName();
                                    String model2 = articleById.getModel();
                                    price2 = (long) articleById.getPrice();
                                    String description2 = articleById.getDescription();
                                    String time2 = articleById.getTime();
                                    if (article != null) {

                                        sql = ("<br/> Category: " + category +
                                                "<br/> Name: " + name +
                                                "<br/> Model: " + model +
                                                "<br/> Price: " + price +
                                                "<br/> Description: " + description +
                                                "<br/> Time: " + time + "<br/>");

                                        cg.bidSelectedArticle.setText("<html>BID THIS ARTICLE<br/>" + sql + "</html>");
                                    }

                                    //get last bid...
                                    Long lastBid = 0L;
                                    try {
                                        lastBid = look_up.getPriceFromBidArticle(Long.parseLong(bidbtn.getName()));
                                    } catch (RemoteException ex) {
                                        ex.printStackTrace();
                                    }
                                    System.out.println(lastBid);
                                    if (lastBid != 0) {
                                        cg.lastBidLbl.setText("Last BID: " + lastBid);
                                    } else {
                                        cg.lastBidLbl.setText("No BID yet!");
                                    }

                                    Long finalPrice = price2;

                                    cg.BID.addActionListener(e3 -> {
                                        cg.backToHome.addActionListener(e21 -> {
                                            cg.showArticlesFromBase.removeAll();

                                        });
                                        if (ref.test) {
                                            Long priceLastBid = 0L;

                                            try {
                                                priceLastBid = look_up.getPriceFromBidArticle(Long.parseLong(bidbtn.getName()));
                                            } catch (RemoteException ex) {
                                                ex.printStackTrace();
                                            }

                                            JFrame frame = new JFrame();
                                            String result = JOptionPane.showInputDialog(frame, "Enter your bid:");
                                            System.out.println(finalPrice);
                                            System.out.println(result);
                                            try {
                                                if (finalPrice != 0 && Long.parseLong(result) >= finalPrice) {
                                                    JFrame parent = new JFrame();
                                                    JOptionPane.showMessageDialog(parent, "\n" +
                                                            "You can not bid above an existing price.!");
                                                    ref.test = true;

                                                } else if (priceLastBid != 0 && Long.parseLong(result) <= priceLastBid) {
                                                    JFrame parent2 = new JFrame();
                                                    JOptionPane.showMessageDialog(parent2, "You can not bid under last bid price!");
                                                    ref.test = true;

                                                } else {

                                                    try {
                                                        look_up.addBid(cg.ulogovanikorisnik.getText(), Long.parseLong(bidbtn.getName()), Long.parseLong(result), priceLastBid);
                                                        cg.lastBidLbl.setText("Last BID: " + result);
                                                        ref.test = false;
                                                    } catch (RemoteException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                }

                                            } catch (NumberFormatException nfe) {
                                                JFrame parent2 = new JFrame();
                                                JOptionPane.showMessageDialog(parent2, "Enter correct price");
                                                ref.test = true;
                                            }
                                        }
                                    });
                                    cg.buyNow.addActionListener(e4 -> {

                                        cg.buyNow.setVisible(false);
                                        System.out.println("Buy now button is working!");
                                        cg.lastBidLbl.setVisible(true);
                                        cg.BID.setVisible(false);
                                        cg.afterBuyArticleLbl.setVisible(true);

                                        try {
                                            int res = look_up.buyArticle(cg.ulogovanikorisnik.getText(), Long.parseLong(bidbtn.getName()));
                                            if (res == 0) {
                                                cg.afterBuyArticleLbl.setText("you dont have enough money to buy this article");
                                            } else {
                                                cg.afterBuyArticleLbl.setText("You are bought this article");
                                            }
                                            cg.showArticlesFromBase.removeAll();
                                            cg.showArticlesFromBase.revalidate();
                                            cg.showArticlesFromBase.repaint();
                                            cg.showArticlesFromBase.setLayout(new BoxLayout(cg.showArticlesFromBase, 1));


                                        } catch (RemoteException ex) {
                                            ex.printStackTrace();
                                        }
                                    });

                                });
                                cg.showArticlesFromBase.add(bidbtn);

                            }

                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                    });

                    cg.lblInformation3.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                            super.mouseClicked(mouseEvent);
                            cg.addCreditCardBtn.setVisible(false);
                            cg.btnboughtArticles.setVisible(false);
                            cg.bid.setVisible(false);
                            cg.backToHome.setVisible(true);
                            cg.backToHome.setBounds(530, 380, 170, 20);
                            cg.backToHome.addActionListener(e -> {
                                cg.btnboughtArticles.setVisible(true);
                            });
                            cg.scrollPane.setVisible(false);
                            cg.lblWelcome.setText("Add your article");
                            cg.passwordLogin.setVisible(false);
                            cg.passwordLoginLabel.setVisible(false);
                            cg.usernameLogin.setVisible(false);
                            cg.usernameLoginLabel.setVisible(false);
                            cg.backToLogin.setVisible(false);
                            cg.lblInformation2.setVisible(false);
                            cg.loginB.setVisible(false);
                            cg.lblWelcome.setVisible(false);
                            cg.btnShowArticles.setVisible(false);
                            cg.labelforarticles.setVisible(false);
                            cg.comboBoxArticles.setVisible(false);
                            cg.labelForListArticles.setVisible(false);
                            cg.lblInformation3.setVisible(false);
                            cg.comboBoxArticles.setVisible(true);
                            cg.sellArticle.setVisible(true);
                            cg.nameOfArticle.setVisible(true);
                            cg.nameOfArticleTF.setVisible(true);
                            cg.priceOfArticle.setVisible(true);
                            cg.priceOfArticleTF.setVisible(true);
                            cg.modelOfArticle.setVisible(true);
                            cg.modelOfArticleTF.setVisible(true);
                            cg.comboBoxArticleLabel.setVisible(true);
                            cg.descriptionOfArticle.setVisible(true);
                            cg.descriptionOfArticleTF.setVisible(true);
                            cg.remainingTimeOfArticle.setVisible(true);
                            cg.remainingTimeOfArticleTF.setVisible(true);
                            cg.showArticlesFromBase.setVisible(false);
                            cg.addArticle.setVisible(true);
                        }
                    });
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        cg.addArticle.addActionListener(e -> {
            Long id;
            String name;
            int status;
            String model;
            Double price;
            String description;
            String time;
            String category;
            try {
                category = (String) cg.comboBoxArticles.getSelectedItem();
                status = 1;
                name = cg.nameOfArticleTF.getText();
                model = cg.modelOfArticleTF.getText();
                price = Double.parseDouble(cg.priceOfArticleTF.getText());
                description = cg.descriptionOfArticleTF.getText();
                time = cg.remainingTimeOfArticleTF.getText();


                if (look_up.createArticle(category, status, name, model, price, description, time) != 0) {
                    JOptionPane.showMessageDialog(cg.getContentPane(), "Articles is added");
                } else
                    JOptionPane.showMessageDialog(cg.getContentPane(), "Articles is not added");


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(cg.getContentPane(), "Articles is not added");
            }


        });
    }

    public static void infoMessage(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void ClientUI() {


        panelUI = new JPanel();
        panelUI.setBackground(Color.BLACK);
        panelUI.setBounds(0, 0, 346, 490);
        panelUI.setVisible(true);
        panelUI.setLayout(null);

        BID = new JButton("BID");
        BID.setBounds(180, 300, 80, 20);
        BID.setVisible(false);
        panelUI.add(BID);

        buyNow = new JButton("BUY NOW");
        buyNow.setBounds(350, 300, 100, 20);
        buyNow.setVisible(false);
        panelUI.add(buyNow);

        lastBidLbl = new JLabel();
        lastBidLbl.setForeground(Color.WHITE);
        lastBidLbl.setBounds(260, 200, 350, 350);
        lastBidLbl.setVisible(true);
        panelUI.add(lastBidLbl);

        afterBuyArticleLbl = new JLabel();
        afterBuyArticleLbl.setBounds(350, 300, 400, 20);
        afterBuyArticleLbl.setVisible(false);
        panelUI.add(afterBuyArticleLbl);


        bidSelectedArticle = new JLabel();
        bidSelectedArticle.setForeground(Color.WHITE);
        bidSelectedArticle.setBounds(260, 20, 350, 350);
        bidSelectedArticle.setVisible(false);
        panelUI.add(bidSelectedArticle);

        usernameLoginLabel = new JLabel("USERNAME");
        usernameLoginLabel.setForeground(Color.WHITE);
        usernameLoginLabel.setBounds(10, 58, 114, 14);
        panelUI.add(usernameLoginLabel);

        usernameLogin = new JTextField();
        usernameLogin.setBounds(10, 90, 283, 36);
        panelUI.add(usernameLogin);

        passwordLoginLabel = new JLabel("PASSWORD");
        passwordLoginLabel.setForeground(Color.WHITE);
        passwordLoginLabel.setBounds(10, 150, 96, 14);
        panelUI.add(passwordLoginLabel);

        passwordLogin = new JPasswordField();
        passwordLogin.setBounds(10, 185, 283, 36);
        panelUI.add(passwordLogin);


        numberOfCreditCardLbl = new JLabel("Enter your number of credit card:");
        numberOfCreditCardLbl.setForeground(Color.WHITE);
        numberOfCreditCardLbl.setBounds(10, 90, 400, 36);
        numberOfCreditCardLbl.setVisible(false);
        panelUI.add(numberOfCreditCardLbl);

        numberOfCreditCardTF = new JTextField();
        numberOfCreditCardTF.setBounds(10, 130, 283, 36);
        numberOfCreditCardTF.setVisible(false);
        panelUI.add(numberOfCreditCardTF);


        moneyOnCreditCardLbl = new JLabel("Input money: ");
        moneyOnCreditCardLbl.setForeground(Color.WHITE);
        moneyOnCreditCardLbl.setBounds(350, 90, 283, 36);
        moneyOnCreditCardLbl.setVisible(false);
        panelUI.add(moneyOnCreditCardLbl);

        moneyOnCreditCardTF = new JTextField();
        moneyOnCreditCardTF.setBounds(350, 130, 283, 36);
        moneyOnCreditCardTF.setVisible(false);
        panelUI.add(moneyOnCreditCardTF);

        addMoneyBtn = new JButton("Add credit card");
        addMoneyBtn.setBounds(10, 280, 283, 36);
        addMoneyBtn.setVisible(false);
        panelUI.add(addMoneyBtn);

        inputMoneyBtn = new JButton("Add money");
        inputMoneyBtn.setBounds(350, 280, 283, 36);
        inputMoneyBtn.setVisible(false);
        panelUI.add(inputMoneyBtn);


        yourMoneyOnCreditCardLbl = new JLabel();
        yourMoneyOnCreditCardLbl.setForeground(Color.WHITE);
        yourMoneyOnCreditCardLbl.setVisible(false);
        yourMoneyOnCreditCardLbl.setBounds(10, 20, 380, 35);
        panelUI.add(yourMoneyOnCreditCardLbl);

        ulogovanikorisnik = new JLabel();
        ulogovanikorisnik.setForeground(Color.WHITE);
        ulogovanikorisnik.setVisible(false);
        ulogovanikorisnik.setBounds(10, 20, 240, 35);
        panelUI.add(ulogovanikorisnik);

        labelforarticles = new JLabel("SELECT THE DESIRED ARTICLE ");
        labelforarticles.setHorizontalAlignment(JLabel.CENTER);
        labelforarticles.setFont(new Font("Serif", Font.CENTER_BASELINE, 35));
        labelforarticles.setForeground(Color.WHITE);
        labelforarticles.setSize(700, 100);
        labelforarticles.setVisible(false);
        panelUI.add(labelforarticles);


        labelForListArticles = new JLabel("LIST OF ARTICLES");
        labelForListArticles.setForeground(Color.WHITE);
        labelForListArticles.setBounds(360, 80, 120, 20);
        labelForListArticles.setVisible(false);
        panelUI.add(labelForListArticles);


        btnShowArticles = new JButton("Show");
        btnShowArticles.setBounds(200, 100, 75, 20);
        btnShowArticles.setVisible(false);
        panelUI.add(btnShowArticles);

        bid = new JButton(" BID ");
        bid.setBounds(520, 250, 50, 20);
        bid.setVisible(false);
        panelUI.add(bid);


        showArticlesFromBase = new JPanel();
        showArticlesFromBase.setBounds(360, 110, 350, 350);

        scrollPane = new JScrollPane(showArticlesFromBase, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(360, 100, 350, 300);
        scrollPane.setVisible(true);
        panelUI.add(scrollPane);

        comboBoxArticles = new JComboBox();
        comboBoxArticles.addItem("Phones");
        comboBoxArticles.addItem("Tablets");
        comboBoxArticles.setSelectedItem(null);
        comboBoxArticles.setBounds(50, 100, 90, 20);
        comboBoxArticles.setVisible(false);
        panelUI.add(comboBoxArticles);

        getComboBoxArticlesAdd = new JComboBox();
        getComboBoxArticlesAdd.setBounds(50, 100, 90, 20);
        getComboBoxArticlesAdd.setVisible(false);
        panelUI.add(getComboBoxArticlesAdd);

        addArticle = new JButton("Add article");
        addArticle.setBounds(400, 380, 100, 20);
        addArticle.setVisible(false);
        panelUI.add(addArticle);


        sellArticle = new JLabel("ADD YOUR ARTICLE");
        sellArticle.setForeground(Color.WHITE);
        sellArticle.setBounds(300, 10, 320, 20);
        sellArticle.setFont(new Font("Serif", Font.CENTER_BASELINE, 20));
        sellArticle.setVisible(false);
        panelUI.add(sellArticle);

        comboBoxArticleLabel = new JLabel("SELECT CATEGORY");
        comboBoxArticleLabel.setForeground(Color.WHITE);
        comboBoxArticleLabel.setBounds(40, 80, 114, 14);
        comboBoxArticleLabel.setVisible(false);
        panelUI.add(comboBoxArticleLabel);


        nameOfArticle = new JLabel("NAME OF ARTICLE");
        nameOfArticle.setForeground(Color.WHITE);
        nameOfArticle.setBounds(10, 180, 114, 14);
        nameOfArticle.setVisible(false);
        panelUI.add(nameOfArticle);


        nameOfArticleTF = new JTextField();
        nameOfArticleTF.setBounds(10, 210, 283, 36);
        nameOfArticleTF.setVisible(false);
        panelUI.add(nameOfArticleTF);


        modelOfArticle = new JLabel("MODEL OF ARTICLE");
        modelOfArticle.setForeground(Color.WHITE);
        modelOfArticle.setBounds(10, 250, 120, 14);
        modelOfArticle.setVisible(false);
        panelUI.add(modelOfArticle);


        modelOfArticleTF = new JTextField();
        modelOfArticleTF.setBounds(10, 280, 283, 36);
        modelOfArticleTF.setVisible(false);
        panelUI.add(modelOfArticleTF);


        priceOfArticle = new JLabel("PRICE OF MODEL");
        priceOfArticle.setForeground(Color.WHITE);
        priceOfArticle.setBounds(10, 320, 150, 14);
        priceOfArticle.setVisible(false);
        panelUI.add(priceOfArticle);

        priceOfArticleTF = new JTextField();
        priceOfArticleTF.setBounds(10, 350, 283, 36);
        priceOfArticleTF.setVisible(false);
        panelUI.add(priceOfArticleTF);


        descriptionOfArticle = new JLabel("DESCRIPTION OF MODEL");
        descriptionOfArticle.setForeground(Color.WHITE);
        descriptionOfArticle.setBounds(400, 80, 150, 14);
        descriptionOfArticle.setVisible(false);
        panelUI.add(descriptionOfArticle);

        descriptionOfArticleTF = new JTextArea();
        descriptionOfArticleTF.setBounds(400, 100, 283, 200);
        descriptionOfArticleTF.setLineWrap(true);
        descriptionOfArticleTF.setWrapStyleWord(true);
        descriptionOfArticleTF.setVisible(false);
        panelUI.add(descriptionOfArticleTF);

        remainingTimeOfArticle = new JLabel("REMAINING TIME");
        remainingTimeOfArticle.setForeground(Color.WHITE);
        remainingTimeOfArticle.setBounds(400, 320, 150, 14);
        remainingTimeOfArticle.setVisible(false);
        panelUI.add(remainingTimeOfArticle);

        remainingTimeOfArticleTF = new JTextField();
        remainingTimeOfArticleTF.setBounds(400, 340, 283, 36);
        remainingTimeOfArticleTF.setVisible(false);
        panelUI.add(remainingTimeOfArticleTF);

        usernameSignUPnLabel = new JLabel("USERNAME");
        usernameSignUPnLabel.setForeground(Color.WHITE);
        usernameSignUPnLabel.setBounds(10, 58, 114, 14);
        usernameSignUPnLabel.setVisible(false);
        panelUI.add(usernameSignUPnLabel);

        usernameSignUP = new JTextField();
        usernameSignUP.setBounds(10, 90, 283, 36);
        usernameSignUP.setVisible(false);
        panelUI.add(usernameSignUP);

        passwordSignUPLabel = new JLabel("PASSWORD");
        passwordSignUPLabel.setForeground(Color.WHITE);
        passwordSignUPLabel.setBounds(10, 150, 96, 14);
        passwordSignUPLabel.setVisible(false);
        panelUI.add(passwordSignUPLabel);


        passwordSignUP = new JPasswordField();
        passwordSignUP.setBounds(10, 185, 283, 36);
        passwordSignUP.setVisible(false);
        panelUI.add(passwordSignUP);

        passwordSignUPLabelConfirm = new JLabel("CONFIRM PASSWORD");
        passwordSignUPLabelConfirm.setForeground(Color.WHITE);
        passwordSignUPLabelConfirm.setBounds(10, 245, 150, 14);
        passwordSignUPLabelConfirm.setVisible(false);
        panelUI.add(passwordSignUPLabelConfirm);

        passwordSignUPConfirm = new JPasswordField();
        passwordSignUPConfirm.setBounds(10, 275, 283, 36);
        passwordSignUPConfirm.setVisible(false);
        panelUI.add(passwordSignUPConfirm);

        lblWelcome = new JLabel("Login");
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Serif", Font.PLAIN, 30));
        lblWelcome.setBounds(35, 10, 341, 35);
        panelUI.add(lblWelcome);

        lblInformation2 = new JLabel("If you dont have account please click here.");
        ((JLabel) lblInformation2).setHorizontalAlignment(SwingConstants.CENTER);
        lblInformation2.setForeground(new Color(240, 248, 255));
        lblInformation2.setFont(new Font("Serif", Font.PLAIN, 20));
        lblInformation2.setBounds(0, 320, 381, 27);
        panelUI.add(lblInformation2);

        lblInformation3 = new JLabel("If you want to sell article click here.");
        ((JLabel) lblInformation3).setHorizontalAlignment(SwingConstants.CENTER);
        lblInformation3.setForeground(new Color(240, 248, 255));
        lblInformation3.setFont(new Font("Serif", Font.PLAIN, 20));
        lblInformation3.setBounds(0, 300, 341, 27);
        lblInformation3.setVisible(false);
        panelUI.add(lblInformation3);

        lblInformation2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                super.mouseClicked(mouseEvent);
                lblWelcome.setText("Register");
                setLoginRegisterPanel(true);


            }
        });

        loginB = new JButton("Login");
        loginB.setBounds(10, 250, 110, 25);
        panelUI.add(loginB);

        addCreditCardBtn = new JButton("Add your credit card to buy article");
        addCreditCardBtn.setBounds(10, 345, 260, 30);
        addCreditCardBtn.setVisible(false);
        panelUI.add(addCreditCardBtn);


        backToHome = new JButton("Back to home");
        backToHome.setBounds(520, 380, 150, 20);
        backToHome.setVisible(false);
        panelUI.add(backToHome);

        backToHome.addActionListener(e -> {
            afterBuyArticleLbl.setVisible(false);
            lblWelcome.setText("Login");
            labelforarticles.setText("SELECT THE DESIRED ARTICLE");
            setPanelAfterLogin(false);
            addCreditCardBtn.setVisible(true);
            numberOfCreditCardTF.setEditable(true);
            addMoneyBtn.setEnabled(true);
            bidSelectedArticle.setVisible(false);
            btnboughtArticles.setVisible(true);


        });

        btnboughtArticles = new JButton();
        btnboughtArticles.setBounds(10, 385, 260, 30);
        btnboughtArticles.setVisible(false);
        panelUI.add(btnboughtArticles);


        backToLogin = new JButton("Back to Login");
        backToLogin.setBounds(152, 340, 140, 25);
        backToLogin.setVisible(false);
        panelUI.add(backToLogin);

        backToLogin.addActionListener(e -> {
            lblWelcome.setText("Login");

            setLoginRegisterPanel(false);

        });

        newuserB = new JButton("Sign up");
        newuserB.setBounds(10, 340, 110, 25);
        newuserB.setVisible(false);
        panelUI.add(newuserB);
        getContentPane().add(panelUI);
    }


    private void setLoginRegisterPanel(boolean parametar) {


        usernameSignUPnLabel.setVisible(parametar);
        usernameSignUP.setVisible(parametar);
        passwordSignUP.setVisible(parametar);
        passwordSignUPLabel.setVisible(parametar);
        passwordSignUPConfirm.setVisible(parametar);
        passwordSignUPLabelConfirm.setVisible(parametar);
        newuserB.setVisible(parametar);
        backToLogin.setVisible(parametar);
        backToHome.setVisible(parametar);


        usernameLogin.setVisible(!parametar);
        usernameLoginLabel.setVisible(!parametar);
        passwordLogin.setVisible(!parametar);
        passwordLoginLabel.setVisible(!parametar);
        loginB.setVisible(!parametar);
        lblInformation2.setVisible(!parametar);

    }

    private void setPanelAfterLogin(boolean parametar) {
        yourMoneyOnCreditCardLbl.setVisible(parametar);
        lastBidLbl.setVisible(parametar);
        BID.setVisible(parametar);
        buyNow.setVisible(parametar);
        scrollPane.setVisible(!parametar);
        passwordLogin.setVisible(parametar);
        passwordLoginLabel.setVisible(parametar);
        usernameLogin.setVisible(parametar);
        usernameLoginLabel.setVisible(parametar);
        backToLogin.setVisible(parametar);
        backToHome.setVisible(parametar);
        lblInformation2.setVisible(parametar);
        loginB.setVisible(parametar);
        lblWelcome.setVisible(parametar);
        btnShowArticles.setVisible(!parametar);
        labelforarticles.setVisible(!parametar);
        inputMoneyBtn.setVisible(parametar);
        comboBoxArticles.setVisible(!parametar);
        labelForListArticles.setVisible(!parametar);
        lblInformation3.setVisible(!parametar);
        getComboBoxArticlesAdd.setVisible(parametar);
        sellArticle.setVisible(parametar);
        nameOfArticle.setVisible(parametar);
        nameOfArticleTF.setVisible(parametar);
        priceOfArticle.setVisible(parametar);
        priceOfArticleTF.setVisible(parametar);
        modelOfArticle.setVisible(parametar);
        modelOfArticleTF.setVisible(parametar);
        comboBoxArticleLabel.setVisible(parametar);
        descriptionOfArticle.setVisible(parametar);
        descriptionOfArticleTF.setVisible(parametar);
        remainingTimeOfArticle.setVisible(parametar);
        remainingTimeOfArticleTF.setVisible(parametar);
        showArticlesFromBase.setVisible(!parametar);
        addArticle.setVisible(parametar);


    }

    private void creditCard(boolean parametar) {
        yourMoneyOnCreditCardLbl.setVisible(!parametar);

        addCreditCardBtn.setVisible(parametar);
        backToHome.setVisible(!parametar);
        backToHome.addActionListener(e -> {

            btnShowArticles.setVisible(parametar);
            addMoneyBtn.setVisible(parametar);
            labelforarticles.setVisible(parametar);
            comboBoxArticles.setVisible(parametar);
            labelForListArticles.setVisible(parametar);
            lblInformation3.setVisible(parametar);
            showArticlesFromBase.setVisible(parametar);
            scrollPane.setVisible(parametar);
            numberOfCreditCardLbl.setVisible(parametar);
            numberOfCreditCardTF.setVisible(parametar);
            moneyOnCreditCardTF.setVisible(parametar);
            moneyOnCreditCardLbl.setVisible(parametar);

        });
        btnShowArticles.setVisible(parametar);
        labelforarticles.setVisible(parametar);
        comboBoxArticles.setVisible(parametar);
        labelForListArticles.setVisible(parametar);
        lblInformation3.setVisible(parametar);
        showArticlesFromBase.setVisible(parametar);
        scrollPane.setVisible(parametar);
        addMoneyBtn.setVisible(!parametar);
        numberOfCreditCardTF.setVisible(!parametar);
        numberOfCreditCardLbl.setVisible(!parametar);
        yourMoneyOnCreditCardLbl.setVisible(!parametar);
    }


    // GETTERS
    public JPanel getPanelUI() {
        return panelUI;
    }

    public JTextField getUsernameLogin() {
        return usernameLogin;
    }

    public JPasswordField getPasswordLogin() {
        return passwordLogin;
    }

    public JTextField getUsernameSignUP() {
        return usernameSignUP;
    }

    public JPasswordField getPasswordSignUP() {
        return passwordSignUP;
    }

    public JButton getLoginB() {
        return loginB;
    }

    public JLabel getUsernameLoginLabel() {
        return usernameLoginLabel;
    }

    public JLabel getPasswordLoginLabel() {
        return passwordLoginLabel;
    }

    public JLabel getUsernameSignUPnLabel() {
        return usernameSignUPnLabel;
    }

    public JLabel getPasswordSignUPLabel() {
        return passwordSignUPLabel;
    }

    public JButton getNewuserB() {
        return newuserB;
    }

    public JPasswordField getPasswordSignUPConfirm() {
        return passwordSignUPConfirm;
    }

    public JLabel getPasswordSignUPLabelConfirm() {
        return passwordSignUPLabelConfirm;
    }


}