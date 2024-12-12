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

    public static List<DogClass> getRandomDogs(int count) throws SQLException {
        String query = "SELECT * FROM dog ORDER BY RAND() LIMIT ?";
        List<DogClass> dogs = new ArrayList<>();
        String[] conditions = { "Super", "Healthy", "Tired", "Injured" }; 
        Random random = new Random(); 

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dogCondition = conditions[random.nextInt(conditions.length)];
                String skill = resultSet.getString("skill");
                int baseSpeed = resultSet.getInt("baseSpeed");
                String imgPath = resultSet.getString("imgPath");
                int price = resultSet.getInt("price");

                dogs.add(new DogClass(id, name, dogCondition, skill, baseSpeed, imgPath, price));                                                                                     
            }
        }
        return dogs;
    }

    public static Obstacle getRandomArena() throws SQLException {
        String query = "SELECT * FROM arena ORDER BY RAND() LIMIT 1";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double reduce = rs.getDouble("reduce");
                int length = rs.getInt("length");
                return new Obstacle(name, reduce, length);
            } else {
                throw new SQLException("No arena found in the database.");
            }
        }
    }
}
