import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class ServerOperation extends UnicastRemoteObject implements ServerInterface {
    DataBaseConnector sqlConnector = DataBaseConnector.getInstanca();
    private Vector v = new Vector();

    protected ServerOperation() throws RemoteException {
    }

    @Override
    public void addServer(ServerInterface clientInt) throws RemoteException {

    }

    @Override
    public void message(String message) throws RemoteException {

    }

    @Override
    public boolean loginUser(ServerInterface clientInt, String username, char[] password) throws RemoteException {
        try {
            String pass = new String(password);

            String query = "SELECT * FROM user WHERE username='" + username + "' AND password='" + pass + "'";

            ResultSet rs = sqlConnector.select(query);

            if (rs.next()) {

                if (rs.getString("username").equals(username) && rs.getString("password").equals(pass)) {
                    infoBox("Successfull login", "LOGIN");
                    v.add(clientInt);

                    System.out.println(v);
                    return true;
                }
            } else {
                infoBox("Password or Username incorrect", "Message");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String registerUser(String username, char[] password, char[] passwordconfirm) throws RemoteException {
        try {
            String pass = new String(password);
            String pass2 = new String(passwordconfirm);

            String query = "SELECT * FROM user WHERE username='" + username + "'";
            ResultSet rs = sqlConnector.select(query);

            if (rs.next()) {

                infoBox("Username exsist", "Username exsist");
            } else if (!pass.equals(pass2)) {
                infoBox("Passwords did not match", "PASSWORD");


            } else {


                infoBox("Successful SIGN UP", "Successful");
                int result = sqlConnector.iudQuerry("INSERT INTO user(username,password,passwordconfirm) VALUES ('" + username + "','" + pass + "','" + pass2 + "')");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long getUserId(String username) {
        String query = "SELECT * FROM user WHERE username = '" + username + "'";
        try {
            ResultSet rss = sqlConnector.select(query);
            if (rss.next()) {
                return rss.getLong("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    @Override
    public List<Articles> getListOfArticles(String value) throws RemoteException {
        List<Articles> listOfArticles = new ArrayList<>();

        String query = "SELECT * FROM articles WHERE category ='" + value + "' AND status=1";
        try {
            ResultSet rs = sqlConnector.select(query);
            while (rs.next()) {
                listOfArticles.add(new Articles(rs.getLong("id"), rs.getInt("status"), rs.getString("category"), rs.getString("name"),
                        rs.getString("model"), rs.getDouble("price"), rs.getString("description"), rs.getString("time"), rs.getString("dateOfCreate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfArticles;
    }

    @Override
    public int createArticle(String category, int status, String name, String model, Double price, String description, String time) throws RemoteException {
        int rs = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String query = "INSERT INTO articles(category,status,name,model,price,description,time,dateOfCreate)" +
                "VALUES ('" + category + "','" + status + "','" + name + "','" + model + "','" + price + "','" + description + "','" + time + "','" + sdf.format(new Date()) + "')";
        try {
            rs = sqlConnector.iudQuerry(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public int addMoney(String username, Long moneyCard, Long numberCard, Long moneyInput) throws RemoteException {

        Long id = getUserId(username);
        try {
            if (id != 0) {
                String query22 = "SELECT * FROM money WHERE idUlogovani=" + id;
                ResultSet rsss = sqlConnector.select(query22);
                if (rsss.next()) {

                    try {
                        Long moneyOnCard = Long.parseLong(String.valueOf(rsss.getLong("moneyCard")));
                        moneyOnCard = Long.valueOf(moneyOnCard + moneyInput);
                        String querryUpdateMoney = "UPDATE money SET moneyCard=" + moneyOnCard +
                                " WHERE numberCard=" + numberCard;
                        return sqlConnector.iudQuerry(querryUpdateMoney);

                    } catch (NumberFormatException ee2) {
                        ee2.printStackTrace();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return 0;
    }

    @Override
    public Long[] addNumber(String username, Long numberCard) throws RemoteException {
        Long[] array = new Long[3];
        Long id = getUserId(username);
        try {
            if (id != 0) {
                String query222 = "INSERT INTO money (idUlogovani,numberCard,moneyCard)" +
                        " VALUES(" + id + "," + numberCard + "," + 0 + ")";
                array[0] = (long) sqlConnector.iudQuerry(query222);
            }

            String query22 = "SELECT * FROM money WHERE idUlogovani=" + id;
            ResultSet rsss = sqlConnector.select(query22);
            if (rsss.next()) {
                Long moneyCard = rsss.getLong("moneyCard");
                Long numCard = rsss.getLong("numberCard");
                array[1] = moneyCard;
                array[2] = numberCard;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return array;
    }

    @Override
    public Long[] getNumberCard(String username) throws RemoteException {
        Long[] array = new Long[3];

        Long id = getUserId(username);
        try {
            String query2 = "SELECT * FROM money WHERE idUlogovani=" + id;
            ResultSet rs = sqlConnector.select(query2);
            if (rs.next()) {
                Long moneyCard = rs.getLong("moneyCard");
                Long numberCard = rs.getLong("numberCard");
                array[0] = 1L;
                array[1] = moneyCard;
                array[2] = numberCard;
            } else {
                array[0] = 0L;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return array;
    }

    @Override
    public List<Articles> getListOfBoughtArticles(String username) throws RemoteException {
        Long id = getUserId(username);

        List<Articles> listOfArticles = new ArrayList<>();

        String query = "SELECT * FROM articles WHERE id in(SELECT idArticles FROM myarticles WHERE idUlogovani=" + id + ")";
        try {
            ResultSet rs = sqlConnector.select(query);
            while (rs.next()) {
                listOfArticles.add(new Articles(rs.getLong("id"), rs.getInt("status"), rs.getString("category"), rs.getString("name"),
                        rs.getString("model"), rs.getDouble("price"), rs.getString("description"), rs.getString("time"), rs.getString("dateOfCreate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfArticles;
    }

    @Override
    public int buyArticleAfterTimeIsUp(String username, Long articleId) throws RemoteException {
        Long id = getUserId(username);

        try {
            String check = "select * from myarticles where idUlogovani = " + id + " and idarticles = " + articleId;
            ResultSet rrr = sqlConnector.select(check);
            if (!rrr.next()) {

                String checkBid = "select * from bidarticle where idUlogovani = " + id;
                ResultSet rez = sqlConnector.select(checkBid);
                if (rez.next()) {
                    String queryInsert = "INSERT INTO myarticles (idUlogovani,idArticles)" +
                            " VALUES(" + id + "," + articleId + ")";
                    return sqlConnector.iudQuerry(queryInsert);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public Articles getArticleById(Long id) throws RemoteException {
        String query2 = "SELECT * FROM articles WHERE id ='" + id + "'";
        String sql = "";
        Long price2 = 0L;
        try {
            ResultSet rs = sqlConnector.select(query2);
            if (rs.next()) {
                return new Articles(rs.getLong("id"), rs.getInt("status"), rs.getString("category"), rs.getString("name"),
                        rs.getString("model"), rs.getDouble("price"), rs.getString("description"), rs.getString("time"), rs.getString("dateOfCreate"));
            }
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getPriceFromBidArticle(Long articleId) throws RemoteException {
        String queryLastBid = "SELECT * FROM bidarticle WHERE idArticle =" + articleId;

        try {
            ResultSet rs2 = sqlConnector.select(queryLastBid);
            if (rs2.next())
                return (long) rs2.getLong("price");
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        return 0L;
    }

    @Override
    public int addBid(String username, Long articleId, Long price, Long priceLastBid) throws RemoteException {
        Long id = getUserId(username);
        try {
            if (priceLastBid == 0) {
                String query22 = "SELECT * FROM user WHERE username = '" + username + "'";
                System.out.println(query22);
                ResultSet resultSet = sqlConnector.select(query22);
                if (resultSet.next()) {
                    String querryUpdateBase = "INSERT INTO bidarticle (idArticle,price,idUlogovani)" +
                            " VALUES(" + articleId + "," + price + "," + id + ")";

                    return sqlConnector.iudQuerry(querryUpdateBase);
                }

            } else {
                String query22 = "SELECT * FROM user WHERE username = '" + username + "'";
                System.out.println(query22);
                ResultSet resultSet = sqlConnector.select(query22);
                if (resultSet.next()) {
                    String querryUpdateBase = "UPDATE bidarticle SET price=" + price + ", idUlogovani=" + id + " WHERE idArticle=" + articleId;

                    return sqlConnector.iudQuerry(querryUpdateBase);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int buyArticle(String username, Long articleId) throws RemoteException {
        try {
            Long id = getUserId(username);
            String query22 = "SELECT * FROM articles WHERE id =" + articleId;
            ResultSet rsSelect = sqlConnector.select(query22);

            if (rsSelect.next()) {
                Long cijena = 0L;
                Long moneyCard = 0L;
                cijena = rsSelect.getLong("price");
                String query23 = "SELECT * FROM money WHERE idUlogovani=" + id;
                ResultSet izvrsi2 = sqlConnector.select(query23);
                if (izvrsi2.next()) {
                    moneyCard = izvrsi2.getLong("moneyCard");
                    System.out.println("Novac na racunu korisnika: " + moneyCard);
                    if (moneyCard >= cijena) {
                        moneyCard -= cijena;
                        String query3 = "UPDATE money SET moneyCard=" + moneyCard + " WHERE idUlogovani=" + id;
                        int ss = sqlConnector.iudQuerry(query3);
                        System.out.println(query3);

                        String queryUpdate = "UPDATE articles SET status=0 WHERE id=" + articleId;
                        sqlConnector.iudQuerry(queryUpdate);
                        String queryInsert = "INSERT INTO myarticles (idUlogovani,idArticles)" +
                                " VALUES(" + id + "," + articleId + ")";
                        return sqlConnector.iudQuerry(queryInsert);

                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
