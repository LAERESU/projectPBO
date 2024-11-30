import java.sql.*;
import java.util.*;

public class DbConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/projectpbo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void checkConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    // Mengambil 5 anjing secara acak dari database
    public static List<DogClass> getRandomDogs(int count) throws SQLException {
        String query = "SELECT * FROM dog ORDER BY RAND() LIMIT ?";
        List<DogClass> dogs = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dogCondition = resultSet.getString("dogCondition");
                String skill = resultSet.getString("skill");
                int baseSpeed = resultSet.getInt("baseSpeed");
                String imgPath = resultSet.getString("imgPath");
                dogs.add(new DogClass(id, name, dogCondition, skill, baseSpeed, imgPath));
            }
        }
        return dogs;
    }

    // Mengambil arena secara acak dari database
    public static List<Arena> getRandomArenas(int count) throws SQLException {
        List<Arena> arenaList = new ArrayList<>();
        String query = "SELECT * FROM arena ORDER BY RAND() LIMIT ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double reduce = rs.getDouble("reduce");
                int length = rs.getInt("length");
                arenaList.add(new Arena(name, reduce, length));
            }
        }
        return arenaList;
    }
}
