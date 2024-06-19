package org.example.DZ3V4;

import java.sql.*;
import java.util.*;

public class JDBC {

    static String jdbcURL = "jdbc:h2:mem:test";

    public static void main(String[] args) {
        Connection con = JDBC.connect2(jdbcURL);
        System.out.println("\u001B[34m");
        JDBC.createTable2(con);
        JDBC.insertData2(con);
        JDBC.selectAllData(con);

        System.out.println("\u001B[32m");
        System.out.println("<=====================4.Метод, который загружает Имя department по Идентификатору person================================================================================================>");
        System.out.println("\u001B[33m");
        int idPerson = 1;
        System.out.println("id_person = " + idPerson + ", имя департамента = " + JDBC.getDepartmentNameByPersonId(con, idPerson));
        System.out.println("\u001B[32m");
        System.out.println("<====================5.Метод, который загружает Map<Person, Department>, в котором маппинг person.name -> department.name================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getPersonDepartmentMapping(con));
//        for (Map.Entry<Person, Department> personDepartmentEntry : JDBC.getPersonDepartmentMapping(con).entrySet()) {
//            System.out.println(personDepartmentEntry.getKey() + " : " + personDepartmentEntry.getValue());
//        }
        JDBC.getPersonDepartmentMapping(con)
                .entrySet()
                .stream()
                .forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
        System.out.println("\u001B[32m");
        System.out.println("<====================5.getPersonToMultipleDepartmentsMapping==================================================================================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getPersonToMultipleDepartmentsMapping(con));
//        for (Map.Entry<Person, List<Department>> personToMultipleDepartmentsEntry : JDBC.getPersonToMultipleDepartmentsMapping(con).entrySet()) {
//            System.out.println(personToMultipleDepartmentsEntry.getKey() + " : " + personToMultipleDepartmentsEntry.getValue());
//        }
        JDBC.getPersonToMultipleDepartmentsMapping(con)
                .entrySet()
                .stream()
                .forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
        System.out.println("\u001B[32m");
        System.out.println("<====================6.Метод, который загружает Map<Department, List<Person>>, в котором маппинг department.name -> <person.name>================================================================>");
        System.out.println("\u001B[33m");
//        System.out.println(JDBC.getDepartmentToMultiplePersonsMapping(con));
//        for (Map.Entry<Department, List<Person>> departmentToMultiplePersonsEntry : JDBC.getDepartmentToMultiplePersonsMapping(con).entrySet()) {
//            System.out.println(departmentToMultiplePersonsEntry.getKey() + " : " + departmentToMultiplePersonsEntry.getValue());
//        }
        JDBC.getDepartmentToMultiplePersonsMapping(con)
                .entrySet()
                .stream()
                .forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
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
            int numberOfPerson = 5;
            for (int i = 1; i <= numberOfPerson; i++) {
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

    public static Map<Person, Department> getPersonDepartmentMapping(Connection connect) {
        String sql = """
                SELECT p.person_id, p.person_name, p.person_age, p.person_action, p.department_id,
                       d.department_id, d.department_name
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<Person, Department> personDepartmentMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_age"),
                        resultSet.getBoolean("person_action"),
                        resultSet.getLong("department_id")
                );

                Department department = new Department(
                        resultSet.getLong("department_id"),
                        resultSet.getString("department_name")
                );

                personDepartmentMap.put(person, department);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return personDepartmentMap;
    }

    public static Map<Person, List<Department>> getPersonToMultipleDepartmentsMapping(Connection connect) {
        String sql = """
                SELECT p.person_id, p.person_name, p.person_age, p.person_action, p.department_id,
                       d.department_id, d.department_name
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<Person, List<Department>> personDepartmentMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_age"),
                        resultSet.getBoolean("person_action"),
                        resultSet.getLong("department_id")
                );

                Department department = new Department(
                        resultSet.getLong("department_id"),
                        resultSet.getString("department_name")
                );

                personDepartmentMap
                        .computeIfAbsent(person, k -> new ArrayList<>())
                        .add(department);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return personDepartmentMap;
    }

    public static Map<Department, List<Person>> getDepartmentToMultiplePersonsMapping(Connection connect) {
        String sql = """
                SELECT p.person_id, p.person_name, p.person_age, p.person_action, p.department_id,
                       d.department_id, d.department_name
                FROM person p
                JOIN department d ON p.department_id = d.department_id
                """;

        Map<Department, List<Person>> departmentPersonMap = new HashMap<>();

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_age"),
                        resultSet.getBoolean("person_action"),
                        resultSet.getLong("department_id")
                );

                Department department = new Department(
                        resultSet.getLong("department_id"),
                        resultSet.getString("department_name")
                );

                departmentPersonMap
                        .computeIfAbsent(department, k -> new ArrayList<>())
                        .add(person);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return departmentPersonMap;
    }
}


