package com.revature.AKBanking.Users;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Crudable;
import com.revature.AKBanking.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Crudable<User> {
    @Override
    public boolean update(User updatedModel) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public boolean delete(User modelToDelete) {
        return false;
    }

    @Override
    public User findById(String id) throws DataNotFoundException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new DataNotFoundException(String.format("User with ID: %s not found", id));
            }

            return generateUserFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByLoginInfo(String email, String password) throws DataNotFoundException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new DataNotFoundException(String.format("User with email: %s not found", email));
            }

            return generateUserFromResultSet(resultSet);
        } catch (SQLException | DataNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User create(User newObject) throws InvalidInputException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "INSERT INTO users (id, first_name, last_name, email, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newObject.getId());
            preparedStatement.setString(2, newObject.getFirstName());
            preparedStatement.setString(3, newObject.getLastName());
            preparedStatement.setString(4, newObject.getEmail());
            preparedStatement.setString(5, newObject.getPassword());
            preparedStatement.setString(6, newObject.getType().toString());

            boolean success = preparedStatement.executeUpdate() == 1;
            if(!success) {
                throw new SQLException("Data could not be inserted");
            }
            System.out.println("Inserting new User...");

            return newObject;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<User> users = new ArrayList<>();

            String sql = "SELECT * FROM users";
            ResultSet results = connection.createStatement().executeQuery(sql);

            while(results.next()) {
                users.add(generateUserFromResultSet(results));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User generateUserFromResultSet(ResultSet results) throws SQLException{
        int id = results.getInt("id");
        String firstName = results.getString("first_name");
        String lastName = results.getString("last_name");
        String email = results.getString("email");
        String password = results.getString("password");
        User.userType type = User.userType.valueOf(results.getString("user_type"));

        return new User(id, firstName, lastName, email, password, type);

    }
}
