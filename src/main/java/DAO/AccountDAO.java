package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    

    /*
     * Add a new account into the database, account registeration
     * @param account an object modeling an Account, the account object does not contain an account_id
     * @return account object just added into database along containing newly generated account_id
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try {

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());

            }


        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /*
     * This function checks if an account already exist with a username in the database
     * @param username a string containing username of an account
     * @return boolean true representing if the account with the given username already exists or false if doesn't
     */
    public boolean checkIfExistByUsername(String username){
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return true;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /*
     * This function checks if an account exist given an account_id in the database
     * @param id an integer account_id of an account
     * @return boolean true representing if the account with the given account_id already exists or false if doesn't 
     */
    public boolean checkIfExistByAccoundID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return true;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /*
     * Log in to an account using username and password
     * @param account an account object containing username and password for a given user, it doesn't contain account_id
     * @return account object containing the account_id if the log in is successful otherwise return null
     */
    public Account logInAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username=? AND password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Account((int)rs.getLong(1), account.getUsername(), account.getPassword());
            }
            
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return null;
    }
}
