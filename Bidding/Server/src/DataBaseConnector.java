import java.sql.*;

public class DataBaseConnector {
    private Connection conn;
    private static DataBaseConnector instanca;

    private DataBaseConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "vulevuleva");
            System.out.println("Konekcija sa bazom uspostavljena! ");
        } catch ( Exception e ) {
            System.err.println("Doslo je do greske pri konekciji na bazu podataka " + e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static DataBaseConnector getInstanca() {
        if(instanca == null)
            instanca = new DataBaseConnector();
        return instanca;
    }

    public int BrojZapisa(ResultSet rs)
    {
        int rezultat=0;
        try {

            while(rs.next())
            {
                rezultat++;
            }

        } catch (SQLException e) {
            System.out.println("Došlo je do greške u radu sa bazom: "+e.getMessage());
            System.exit(1);
        }
        return rezultat;
    }

    public int iudQuerry(String sql) throws SQLException {
        System.out.println(sql);
        Statement statement = conn.createStatement();
        return statement.executeUpdate(sql);
    }


    public ResultSet select(String sql) throws SQLException {
        Statement statement= conn.createStatement();
        return statement.executeQuery(sql);
    }
}
