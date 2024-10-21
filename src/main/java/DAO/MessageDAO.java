package DAO;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message insertMessage(Message message){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
                                
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            
            if(pkeyResultSet.next()){
                
                return new Message((int) pkeyResultSet.getLong(1), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return null;
        
        
    }

}
