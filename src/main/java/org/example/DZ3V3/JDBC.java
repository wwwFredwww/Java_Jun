package org.example.DZ3V3;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC {

    static String jdbcURL = "jdbc:h2:mem:test";

    public static void main(String[] args) {

        Connection con = JDBC.connect2(jdbcURL);
        System.out.println("\u001B[34m");
        JDBC.createTable2(con);
        JDBC.insertData2(con);
        JDBC.selectAllData(con);
        System.out.println("\u001B[32m");
        System.out.println("<=====================Метод, который загружает Имя department по Идентификатору person=======================================================================================================>");
        System.out.print("\u001B[33m");
        int idPerson = 1;
        System.out.println("id_person = " + idPerson + ", имя департамента = " + JDBC.getDepartmentNameByPersonId(con, idPerson));
        System.out.println("\u001B[32m");
        System.out.println("<====================Метод, который загружает Map<String, String>, в котором маппинг person.name -> department.name==========================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getPersonDepartmentMapping(con));
        JDBC.getPersonDepartmentMapping(con)
                .entrySet()
                .stream()
                .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
        System.out.println("\u001B[32m");
        System.out.println("<====================getPersonToMultipleDepartmentsMapping=================================================================================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getPersonToMultipleDepartmentsMapping(con));
        JDBC.getPersonToMultipleDepartmentsMapping(con)
                .entrySet()
                .stream()
                .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
        System.out.println("\u001B[32m");
        System.out.println("<====================Метод, который загружает Map<String, List<String>>, в котором маппинг department.name -> <person.name>================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getDepartmentToMultiplePersonsMapping(con));
        JDBC.getDepartmentToMultiplePersonsMapping(con)
                .entrySet()
                .stream()
                .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
    }

    public static Connection connect2(String dbURL) {
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            System.out.println("Соединение установлено");
            return connection;
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с db: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void createTable2(Connection connect) {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(
                    """
                            CREATE TABLE department (
                                            department_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            department_name VARCHAR(128) NOT NULL)
                            """
            );
            statement.executeUpdate(
                    """
                            CREATE TABLE person (
                                        person_id INT PRIMARY KEY AUTO_INCREMENT,
                                        person_name VARCHAR(45),
                                        person_age INT NOT NULL,
                                        person_action BOOLEAN NOT NULL,
                                        department_id BIGINT,
                                        FOREIGN KEY (department_id) REFERENCES department(department_id))
                            """
            );
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void insertData2(Connection connect) {
        try (PreparedStatement deptStmt = connect.prepareStatement(
                """
                        INSERT INTO department (department_name) VALUES (?)
                        """, Statement.RETURN_GENERATED_KEYS)) {
            int NumberOfPerson = 5;
            for (int i = 1; i <= NumberOfPerson; i++) {
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


                }
            }
            System.out.println("В таблицу успешно добавлены записи");

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

    public static String getDepartmentNameByPersonId(Connection connect, int personId) {
        String sql = """
                SELECT d.department_name 
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                WHERE p.person_id = ?
                """;

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("department_name");
            } else {
                return "Department not found";
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getPersonDepartmentMapping(Connection connect) {
        String sql = """
                SELECT p.person_name, d.department_name 
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<String, String> personDepartmentMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String personName = resultSet.getString("person_name");
                String departmentName = resultSet.getString("department_name");
                personDepartmentMap.put(personName, departmentName);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return personDepartmentMap;
    }


    public static Map<String, List<String>> getPersonToMultipleDepartmentsMapping(Connection connect) {
        String sql = """
                SELECT p.person_name, d.department_name 
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<String, List<String>> personDepartmentMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String personName = resultSet.getString("person_name");
                String departmentName = resultSet.getString("department_name");

                personDepartmentMap
                        .computeIfAbsent(personName, k -> new ArrayList<>())
                        .add(departmentName);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return personDepartmentMap;
    }

    public static Map<String, List<String>> getDepartmentToMultiplePersonsMapping(Connection connect) {
        String sql = """
                SELECT p.person_name, d.department_name 
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<String, List<String>> departmentPersonMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String departmentName = resultSet.getString("department_name");
                String personName = resultSet.getString("person_name");

                departmentPersonMap
                        .computeIfAbsent(departmentName, k -> new ArrayList<>())
                        .add(personName);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return departmentPersonMap;
    }
}

