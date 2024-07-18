package com.revature.AKBanking.Transactions;

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

public class TransactionRepository implements Crudable<Transaction> {
    @Override
    public boolean update(Transaction updatedModel) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "UPDATE transactions SET account_id = ?, amount = ?, credit = ?, description = ? WHERE transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, updatedModel.getAccountID());
            preparedStatement.setInt(2, updatedModel.getAmount());
            preparedStatement.setBoolean(3, updatedModel.isCredit());
            preparedStatement.setString(4, updatedModel.getDescription());
            preparedStatement.setInt(5, updatedModel.getTransactionID());

            boolean updated = preparedStatement.executeUpdate() == 1;

            if(!updated) {
                throw new DataNotFoundException(String.format("Transaction with ID: %s not found", updatedModel.getTransactionID()));
            }
            System.out.println("Updating Transaction...");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Transaction modelToDelete) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, modelToDelete.getTransactionID());

            boolean deleted = preparedStatement.executeUpdate() == 1;

            if(!deleted) {
                throw new DataNotFoundException(String.format("Transaction with ID: %s not found", modelToDelete.getTransactionID()));
            }
            System.out.println("Deleting Transaction...");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Transaction findById(String id) throws DataNotFoundException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT * FROM transactions WHERE transaction_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet results = preparedStatement.executeQuery();

            if(!results.next()) {
                throw new DataNotFoundException(String.format("Transaction with ID: %s not found", id));
            }

            return generateTransactionFromResultSet(results);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transaction> findAll() {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Transaction> transactions = new ArrayList<>();

            String sql = "SELECT * FROM transactions";
            ResultSet results = connection.createStatement().executeQuery(sql);

            while(results.next()) {
                transactions.add(generateTransactionFromResultSet(results));
            }

            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Transaction create(Transaction newObject) throws InvalidInputException {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "INSERT INTO transactions (transaction_id, account_id, amount, credit, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newObject.getTransactionID());
            preparedStatement.setInt(2, newObject.getAccountID());
            preparedStatement.setInt(3, newObject.getAmount());
            preparedStatement.setBoolean(4, newObject.isCredit());
            preparedStatement.setString(5, newObject.getDescription());

            boolean success = preparedStatement.executeUpdate() == 1;

            if(!success) {
                throw new SQLException("Data could not be inserted");
            }
            System.out.println("Creating Transaction...");

            return newObject;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transaction> findByAccountID(int accountID) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Transaction> transactions = new ArrayList<>();

            String sql = "SELECT * FROM transactions WHERE account_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accountID);

            ResultSet results = preparedStatement.executeQuery();

            while(results.next()) {
                transactions.add(generateTransactionFromResultSet(results));
            }

            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Transaction generateTransactionFromResultSet(ResultSet results) throws SQLException {
        int id = results.getInt("transactionID");
        int accountNumber = results.getInt("accountID");
        int amount = results.getInt("amount");
        boolean credit = results.getBoolean("credit");
        String description = results.getString("description");

        return new Transaction(id, accountNumber, amount, credit, description);
    }
}
