package com.revature.AKBanking.Accounts;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.ConnectionFactory;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Crudable<Account> {
    @Override
    public boolean update(Account updatedModel) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "UPDATE accounts SET owner_id = ?, balance = ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, updatedModel.getOwnerID());
            preparedStatement.setInt(2, updatedModel.getBalance());
            preparedStatement.setInt(3, updatedModel.getAccountNumber());

            boolean updated = preparedStatement.executeUpdate() == 1;

            if(!updated) {
                throw new DataNotFoundException(String.format("Account with number: %s not found", updatedModel.getAccountNumber()));
            }
            System.out.println("Updating Account...");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account modelToDelete) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "DELETE FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, modelToDelete.getAccountNumber());

            boolean deleted = preparedStatement.executeUpdate() == 1;

            if(!deleted) {
                throw new DataNotFoundException(String.format("User with ID: %s not found", modelToDelete.getAccountNumber()));
            }
            System.out.println("Deleting Account...");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account findById(String id) throws DataNotFoundException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new DataNotFoundException(String.format("User with ID: %s not found", id));
            }

            return generateAccountFromResultSet(resultSet);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> findAll() {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Account> accounts = new ArrayList<>();

            String sql = "SELECT * FROM accounts";
            ResultSet results = connection.createStatement().executeQuery(sql);

            while (results.next()) {
                accounts.add(generateAccountFromResultSet(results));
            }

            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account create(Account newObject) throws InvalidInputException {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "INSERT INTO accounts (account_number, owner_id, balance) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newObject.getAccountNumber());
            preparedStatement.setInt(2, newObject.getOwnerID());
            preparedStatement.setInt(3, newObject.getBalance());

            boolean success = preparedStatement.executeUpdate() == 1;
            if (!success) {
                throw new SQLException("Data could not be inserted");
            }
            System.out.println("Inserting new Account...");

            return newObject;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> findByOwnerId(String ownerId) {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Account> accounts = new ArrayList<>();

            String sql = "SELECT * FROM accounts WHERE owner_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(ownerId));

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                accounts.add(generateAccountFromResultSet(results));
            }

            return accounts;
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Account generateAccountFromResultSet(ResultSet results) throws SQLException {
        int accountNumber = results.getInt("account_number");
        int ownerID = results.getInt("owner_id");
        int balance = results.getInt("balance");

        return new Account(accountNumber, ownerID, balance);

    }
}
