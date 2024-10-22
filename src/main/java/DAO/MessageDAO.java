package DAO;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;
import java.util.*;

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

    public List<Message> retrieveAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> list = new ArrayList<>();
        try {
            String sql = "SELECT * from message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                list.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));

            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return list;
    }

    // retrive single message given a message_id
    public Message retrieveSingleMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "Select * from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return new Message(message_id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return null;
    }


    public void deleteMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();

        try {
                String sql = "DELETE from message where message_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, messageID);
                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }


    public void updateMessageText(int messageID, String newMessageText){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Update message Set message_text=? Where message_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMessageText);
            preparedStatement.setInt(2, messageID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }


    public List<Message> retrieveAllMessagesByUser(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            String sql = "Select * from message where posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                allMessages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
            
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return allMessages;
    }
}
