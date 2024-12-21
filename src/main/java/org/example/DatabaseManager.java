package org.example;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DatabaseManager {

    private DatabaseInfo dbI;
    private Connection connection;

    public DatabaseManager() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var fileName = getClass().getClassLoader()
                        .getResource("option.json").toURI();
            File file = new File(fileName);

            dbI = objectMapper.readValue(file, DatabaseInfo.class);


            String url = "jdbc:postgresql://"+dbI.host+":5432/"+dbI.database;
            connection = DriverManager.getConnection(url, dbI.username, dbI.password);
        } catch (SQLException e) {
            System.out.println("Error connecting to DB"+e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("Error, no such file found" + e.getMessage());
        } catch (StreamReadException e) {
            System.out.println("Error with mapping");
        } catch (DatabindException e) {
            System.out.println("Error with mapping1");
        } catch (IOException e) {
            System.out.println("Error with mapping2");
        }
    }

    public void createTables() {
        createContacts();
    }
    private void createContacts() {
        try {
            var fileName = getClass().getClassLoader()
                    .getResource("createContacts.sql").toURI();
            Path filePath = Path.of(fileName);
            String sql = Files.readString(filePath);
            Statement command = connection.createStatement();
            command.executeUpdate(sql);
            command.close();
            System.out.println("Created successfully");
        } catch (URISyntaxException e) {
            System.err.println("Error with file "+e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file"+e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error creating command"+e.getMessage());
        }
    }

    public void printContacts() {
        String query = "SELECT * FROM contacts";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String createdAt = resultSet.getString("created_at");
                String updatedAt = resultSet.getString("updated_at");

                System.out.printf("ID: %d, First Name: %s, Last Name: %s, Email: %s, Phone: %s, Created At: %s, Updated At: %s%n",
                        id, firstName, lastName, email, phoneNumber, createdAt, updatedAt);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }

    public void addContact(String firstName, String lastName, String email, String phoneNumber) {
        String sql = "INSERT INTO contacts (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Contact added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding contact: " + e.getMessage());
        }
    }

}

