package org.example.DZ3V1;


import java.sql.*;

public class TestConnect {

    public static void main(String[] args) throws SQLException {
        // URL для подключения к базе данных H2 в памяти
        String jdbcURL = "jdbc:h2:mem:test";
        String username = "sa";
        String password = "123";

        // Подключение к базе данных
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println("Подключение установлено.");


            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE example (id INT PRIMARY KEY, name VARCHAR(255))");
                statement.execute("INSERT INTO example (id, name) VALUES (1, 'Alice')");
                statement.execute("INSERT INTO example (id, name) VALUES (2, 'Bob')");


                ResultSet resultSet = statement.executeQuery("SELECT * FROM example");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

