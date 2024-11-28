import java.sql.*;

public class DbConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/projectpbo"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void checkConnection() {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static ResultSet getAllSongs() throws SQLException {
        String query = "SELECT * FROM dog";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
