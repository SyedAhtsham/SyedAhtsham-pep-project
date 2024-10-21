package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try {

            String sql = "insert into account (username, password) values (?, ?)";
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

    public boolean checkIfAlreadyExist(String username){
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "Select * from account where username=?";

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

    
    public Account logInAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from account where username=? AND password=?";

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
