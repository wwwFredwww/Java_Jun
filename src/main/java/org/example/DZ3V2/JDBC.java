package org.example.DZ3V2;

import java.sql.*;

//public class JDBC {
//
//    static String jdbcURL = "jdbc:h2:mem:test";
//
//    public static void main(String[] args) {
//
//        Connection con = JDBC.connect2(jdbcURL);
//        JDBC.createTable2(con);
//        JDBC.insertData2(con);
//        JDBC.selectAllData(con);
//    }
//
//    public static Connection connect2(String dbURL) {
//        try {
//            Connection connection = DriverManager.getConnection(dbURL);
//            System.out.println("Соединение установлено");
//            return connection;
//        } catch (SQLException e) {
//            System.err.println("Не удалось установить соединение с db: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void createTable2(Connection connect) {
//        try (Statement statement = connect.createStatement()) {
//            statement.executeUpdate(
//                    """
//                            CREATE TABLE person (
//                                        person_id INT PRIMARY KEY AUTO_INCREMENT,
//                                        person_name VARCHAR(45),
//                                        person_age INT NOT NULL,
//                                        person_action BOOLEAN NOT NULL,
//                                        department_id BIGINT,
//                                        FOREIGN KEY (department_id) REFERENCES department(department_id))
//                            """
//            );
//            statement.executeUpdate(
//                    """
//                            CREATE TABLE department (
//                                            department_id INT PRIMARY KEY AUTO_INCREMENT,
//                                            department_name VARCHAR(45))
//                            """
//            );
//            System.out.println("Таблица успешно создана");
//        } catch (SQLException e) {
//            System.err.println("Не удалось создать таблицу: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void insertData2(Connection connect) {
//        try (PreparedStatement deptStmt = connect.prepareStatement(
//                """
//                        INSERT INTO department (department_name) VALUES (?)
//                        """, Statement.RETURN_GENERATED_KEYS)) {
//
//            Department department = new Department();
//            deptStmt.setString(1, department.getDepartmentName());
//            deptStmt.executeUpdate();
//
//            ResultSet generatedKeys = deptStmt.getGeneratedKeys();
//            long departmentId = 0;
//            if (generatedKeys.next()) {
//                departmentId = generatedKeys.getLong(1);
//            }
//
//            try (PreparedStatement personStmt = connect.prepareStatement(
//                    """
//                            INSERT INTO person (person_name, person_age, person_action, department_id) VALUES (?, ?, ?, ?)
//                            """)) {
//                Person person = new Person(departmentId);
//                personStmt.setString(1, person.getPersonName());
//                personStmt.setInt(2, person.getPersonAge());
//                personStmt.setBoolean(3, person.getPersonActive());
//                personStmt.setLong(4, person.getDepartmentId());
//                personStmt.executeUpdate();
//
//                System.out.println("В таблицу успешно добавлены записи");
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Не удалось добавить данные в таблицу: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void selectAllData(Connection connect) {
//        try (Statement statement = connect.createStatement()) {
//            ResultSet personResultSet = statement.executeQuery("SELECT * FROM person");
//
//            while (personResultSet.next()) {
//                int personId = personResultSet.getInt("person_id");
//                String personName = personResultSet.getString("person_name");
//                int personAge = personResultSet.getInt("person_age");
//                boolean personAction = personResultSet.getBoolean("person_action");
//                long departmentId = personResultSet.getLong("department_id");
//
//                System.out.println("Person ID: " + personId + ", Name: " + personName + ", Age: " + personAge + ", Active: " + personAction + ", Department ID: " + departmentId);
//            }
//
//            ResultSet departmentResultSet = statement.executeQuery("SELECT * FROM department");
//            while (departmentResultSet.next()) {
//                int departmentId = departmentResultSet.getInt("department_id");
//                String departmentName = departmentResultSet.getString("department_name");
//                System.out.println("Department ID: " + departmentId + ", Name: " + departmentName);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Не удалось получить данные из таблицы: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//}


public class JDBC {

    static String jdbcURL = "jdbc:h2:mem:test";

    public static void main(String[] args) {

        Connection con = JDBC.connect2(jdbcURL);
        JDBC.createTable2(con);
        JDBC.insertData2(con);
        JDBC.selectAllData(con);
    }

    public static Connection connect2(String dbURL) {
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            System.out.println("Соединение установлено");
            return connection;
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с db" + e.getMessage());
            throw new RuntimeException(e);
        }
//        try (Connection connection = DriverManager.getConnection(dbURL)) {
//            System.out.println("Соединение установлено");
//            return connection;
//        } catch (SQLException e) {
//            System.err.println("Не удалось установить соединение с db" + e.getMessage());
//            throw new RuntimeException(e);
//        }
    }

    public static void createTable2(Connection connect) {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(
                    """
                            CREATE TABLE `person`(
                                        `person_id` INT PRIMARY KEY AUTO_INCREMENT,
                                        `person_name` VARCHAR(45),
                                        `person_age` INT NOT NULL,
                                        `person_action` BOOLEAN NOT NULL,
                                        `department_id` BIGINT
                                         FOREIGN KEY (department_id) REFERENCES department(department_id)))
                            """
            );
            statement.executeUpdate(
                    """
                            CREATE TABLE `department`(
                                            `department_id` INT PRIMARY KEY AUTO_INCREMENT,
                                            `department_name` VARCHAR(45))
                                                        """
            );
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


//    public static void insertData2(Connection connect) {
//        try (PreparedStatement preparedStatement = connect.prepareStatement(
//                """
//                        INSERT INTO `person` (`person_name`, `person_age`, `person_action`, `department_id`) VALUES (?, ?, ?, ?)
//                        """)) {
//            Person person = new Person();
//            preparedStatement.setString(1, person.getPersonName());
//            preparedStatement.setInt(2, person.getPersonAge());
//            preparedStatement.setBoolean(3, person.getPersonActive());
//            preparedStatement.setLong(4, person.getDepartmentId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Не удалось добавить данные в таблицу " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//        try (PreparedStatement preparedStatement = connect.prepareStatement(
//                """
//                        INSERT INTO `department` (`department_name`)VALUES (?)
//                        """)) {
//            Department department = new Department();
//            preparedStatement.setString(1, department.getDepartmentName());
//            preparedStatement.executeUpdate();
//
//            System.out.println("В таблицу успешно добавлены записи");
//        } catch (SQLException e) {
//            System.err.println("Не удалось добавить данные в таблицу " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//    }

    public static void insertData2(Connection connect) {
        try (PreparedStatement deptStmt = connect.prepareStatement(
                """
                        INSERT INTO department (department_name) VALUES (?)
                        """, Statement.RETURN_GENERATED_KEYS)) {

            Department department = new Department();
            deptStmt.setString(1, department.getDepartmentName());
            deptStmt.executeUpdate();

            ResultSet generatedKeys = deptStmt.getGeneratedKeys();
            long departmentId = 0;
            if (generatedKeys.next()) {
                departmentId = generatedKeys.getLong(1);
            }

            try (PreparedStatement personStmt = connect.prepareStatement(
                    """
                            INSERT INTO person (person_name, person_age, person_action, department_id) VALUES (?, ?, ?, ?)
                            """)) {
                Person person = new Person(departmentId);
                personStmt.setString(1, person.getPersonName());
                personStmt.setInt(2, person.getPersonAge());
                personStmt.setBoolean(3, person.getPersonActive());
                personStmt.setLong(4, person.getDepartmentId());
                personStmt.executeUpdate();

                System.out.println("В таблицу успешно добавлены записи");
            }

        } catch (SQLException e) {
            System.err.println("Не удалось добавить данные в таблицу: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static void selectAllData(Connection connect) {
        try (Statement statement = connect.createStatement()) {
            ResultSet personResultSet = statement.executeQuery("SELECT * FROM person");

            while (personResultSet.next()) {
                int personId = personResultSet.getInt("person_id");
                String personName = personResultSet.getString("person_name");
                int personAge = personResultSet.getInt("person_age");
                boolean personAction = personResultSet.getBoolean("person_action");
                long departmentId = personResultSet.getLong("department_id");

                System.out.println("Person ID: " + personId + ", Name: " + personName + ", Age: " + personAge + ", Active: " + personAction + ", Department ID: " + departmentId);
            }

            ResultSet departmentResultSet = statement.executeQuery("SELECT * FROM department");
            while (departmentResultSet.next()) {
                int departmentId = departmentResultSet.getInt("department_id");
                String departmentName = departmentResultSet.getString("department_name");
                System.out.println("Department ID: " + departmentId + ", Name: " + departmentName);
            }

        } catch (SQLException e) {
            System.err.println("Не удалось получить данные из таблицы: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}