import java.sql.*;

public class H2testConnection {
    public static final String DB_URL = "jdbc:h2:/c:/Users/Dilitand/test";
    static String DBURL2 = "jdbc:h2:~/test";
    public static final String DB_Driver = "org.h2.Driver";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(DB_Driver);
        Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT TOP 100 * FROM ACCOUNT");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
            System.out.print(resultSetMetaData.getColumnLabel(i) + "\t");
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
        }

        connection.close();
    }
}
