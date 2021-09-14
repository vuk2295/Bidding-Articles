import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerOperation extends UnicastRemoteObject implements ServerInterface {

    private Graphics g;

    protected ServerOperation() throws RemoteException {

    }

    protected ServerOperation(Graphics g) throws RemoteException {

        this.g = g;
    }

    @Override
    public String registerUser(String username, char[] password, char[] passwordconfirm) throws RemoteException {
        return null;
    }

    @Override
    public boolean loginUser(ServerInterface clientInt, String username, char[] password) throws RemoteException {
        return false;
    }

    @Override
    public List<Articles> getListOfArticles(String value) throws RemoteException {
        return null;
    }

    @Override
    public int createArticle(String category, int status, String name, String model, Double price, String description, String time) throws RemoteException {
        return 0;
    }

    @Override
    public int addMoney(String username, Long moneyCard, Long numberCard, Long moneyInput) throws RemoteException {
        return 0;
    }

    @Override
    public Long[] addNumber(String username, Long numberCard) throws RemoteException {
        return new Long[0];
    }

    @Override
    public Long[] getNumberCard(String username) throws RemoteException {
        return null;
    }

    @Override
    public List<Articles> getListOfBoughtArticles(String username) throws RemoteException {
        return null;
    }

    @Override
    public int buyArticleAfterTimeIsUp(String username, Long articleId) throws RemoteException {
        return 0;
    }

    @Override
    public Articles getArticleById(Long id) throws RemoteException {
        return null;
    }

    @Override
    public Long getPriceFromBidArticle(Long articleId) throws RemoteException {
        return null;
    }

    @Override
    public int addBid(String username, Long articleId, Long price, Long priceLastBid) throws RemoteException {
        return 0;
    }

    @Override
    public int buyArticle(String username, Long articleId) throws RemoteException {
        return 0;
    }
}