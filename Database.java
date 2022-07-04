package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import static banking.AccountTable.create_table;
import static banking.AccountTable.delete;
import static banking.AccountTable.do_tranfer;
import static banking.AccountTable.get_account_number;
import static banking.AccountTable.get_balance;
import static banking.AccountTable.insert;
import static banking.AccountTable.update_balance;

public class Database {

    private final DataSource dataSource;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
        init();
    }
    
    
    private void init() {

        try (final Connection dataSourceConnection = dataSource.getConnection();

             final Statement statement = dataSourceConnection.createStatement()) {
            statement.executeUpdate(create_table);

        } catch (SQLException e) {
            System.out.println("Error while creating table " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void add(Account card) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(insert)) {

            statement.setString(1, card.getAccountNumber());
            statement.setString(2, card.getPin());
            statement.setDouble(3, card.getBalance());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding Account parameters" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Account getAccountInformation(String number) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(get_account_number)) {
            statement.setString(1, number);

            try (final ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    final String pin = res.getString("pin");
                    final double balance = res.getDouble("balance");
                    return new Account(number, pin, balance);

                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
            throw new RuntimeException("Error while finding element" + e.getMessage());
        }
    }
    
    void updateBalanceAddingMoney(Double income, String number) {
        prepareUpdateStatement(income, number, update_balance);
    }
    
    void updateBalanceTransfer(Double amountOfTransfer, String number) {
        prepareUpdateStatement(amountOfTransfer, number, do_tranfer);
    }
    
    
    private void prepareUpdateStatement(Double income, String number, String update_balance) {
        try (final Connection connection = dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement(update_balance)) {
            statement.setDouble(1, income);
            statement.setString(2, number);
            
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
            throw new RuntimeException("Error finding element");
        }
    }
    
    
    public double getBalance(String number) {
        try (final Connection connection = dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement(get_balance)) {
            statement.setString(1, number);
            
            try (final ResultSet res = statement.executeQuery()) {
                return res.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
            throw new RuntimeException("Error while finding element");
        }
    }
    
    public void deleteAccount(String number) {
        try (final Connection connection = dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setString(1, number);
        
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
            throw new RuntimeException("Error finding element");
        }
    }


}
