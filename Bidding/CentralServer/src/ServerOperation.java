import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class ServerOperation extends UnicastRemoteObject implements ServerInterface {
    private static ServerInterface look_up;
    private DataBaseConnector sqlConnector = DataBaseConnector.getInstanca();

    protected ServerOperation() throws RemoteException {
        try {
            look_up = (ServerInterface) Naming.lookup("//localhost:5099/MyServer");
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public String registerUser(String username, char[] password, char[] passwordconfirm) throws RemoteException {
        return look_up.registerUser(username, password, passwordconfirm);
    }


    @Override
    public boolean loginUser(ServerInterface clientInt, String username, char[] password) throws RemoteException {
        return look_up.loginUser(clientInt, username, password);
    }

    @Override
    public void addServer(ServerInterface clientInt) throws RemoteException {

    }

    @Override
    public void message(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public List<Articles> getListOfArticles(String value) throws RemoteException{
        return look_up.getListOfArticles(value);
    }

    @Override
    public int createArticle(String category, int status, String name, String model, Double price, String description, String time) throws RemoteException {
        return look_up.createArticle(category, status, name, model, price, description, time);

    }

    @Override
    public int addMoney(String username, Long moneyCard, Long numberCard, Long moneyInput) throws RemoteException {
        return look_up.addMoney(username,moneyCard,numberCard,moneyInput);
    }

    @Override
    public Long[] addNumber(String username, Long numberCard) throws RemoteException {
        return look_up.addNumber(username, numberCard);
    }

    @Override
    public Long[] getNumberCard(String username) throws RemoteException {
        return look_up.getNumberCard(username);
    }

    @Override
    public List<Articles> getListOfBoughtArticles(String username) throws RemoteException {
        return look_up.getListOfBoughtArticles(username);
    }

    @Override
    public int buyArticleAfterTimeIsUp(String username, Long articleId) throws RemoteException {
        return look_up.buyArticleAfterTimeIsUp(username, articleId);
    }

    @Override
    public Articles getArticleById(Long id) throws RemoteException {
        return look_up.getArticleById(id);
    }

    @Override
    public Long getPriceFromBidArticle(Long articleId) throws RemoteException {
        return look_up.getPriceFromBidArticle(articleId);
    }

    @Override
    public int addBid(String username, Long articleId, Long price, Long priceLastBid) throws RemoteException {
        return look_up.addBid(username, articleId, price, priceLastBid);
    }

    @Override
    public int buyArticle(String username, Long articleId) throws RemoteException {
        return look_up.buyArticle(username, articleId);
    }
}
