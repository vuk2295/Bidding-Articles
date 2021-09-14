import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {


    public String registerUser(String username,char[] password, char[] passwordconfirm) throws RemoteException;
    public boolean loginUser(ServerInterface clientInt,String username,char[] password) throws RemoteException;
    public void addServer(ServerInterface clientInt) throws RemoteException;
    public void message(String message) throws RemoteException;
    public List<Articles> getListOfArticles(String value) throws RemoteException;
    public int createArticle(String category,int status,String name,String model,Double price,String description,String time) throws RemoteException;
    public int addMoney(String username, Long moneyCard, Long numberCard, Long moneyInput) throws RemoteException;
    public Long[] addNumber(String username, Long numberCard) throws RemoteException;
    public Long[] getNumberCard(String username) throws  RemoteException;
    public List<Articles> getListOfBoughtArticles(String username) throws RemoteException;
    public int buyArticleAfterTimeIsUp(String username, Long articleId) throws RemoteException;
    public Articles getArticleById(Long id) throws RemoteException;
    public Long getPriceFromBidArticle(Long articleId) throws RemoteException;
    public int addBid(String username, Long articleId, Long price, Long priceLastBid) throws RemoteException;
    public int buyArticle(String username, Long articleId) throws RemoteException;

}
