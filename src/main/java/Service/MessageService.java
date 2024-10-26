package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }


    // post a new message into the database
    public Message createNewMessage(Message message){
        // checking if the message is non-empty, having characters less than 255 and message poster's account exist
        if(message.getMessage_text()!="" && message.getMessage_text().length()<255 && accountDAO.checkIfExistByAccoundID(message.getPosted_by()) ){
            return messageDAO.insertMessage(message);
        }
        
        return null;
        
    }

    // retrive all messages stored in the database
    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    // retrieve a message using its id
    public Message getMessageByID(int message_id){
        return messageDAO.retrieveSingleMessage(message_id);
    }

    public Message deleteMessage(int message_id){
        // check if the message exists with the given message_id
        Message message = messageDAO.retrieveSingleMessage(message_id);
        if(message==null){
            return null;
        }
        // if exists, delete it and return the message object that we have above
        messageDAO.deleteMessageByID(message_id);
        return message;
        
    }

    public Message updateMessage(int message_id, String newMessageText){
        // if the message doesn't exist, we can't update it
        if( messageDAO.retrieveSingleMessage(message_id)==null){
            return null;
        }
        // new message should be non-empty and has less than 255 characters, first we update the message and then retrieve the updated message
        if(newMessageText!="" && newMessageText.length()<255){
            messageDAO.updateMessageText(message_id, newMessageText);
            return messageDAO.retrieveSingleMessage(message_id);
        }
        return null;
        
    }

    // we get all the messages posted by a user specified by his account_id
    public List<Message> getAllMessagesByUser(int accountID){
        return messageDAO.retrieveAllMessagesByUser(accountID);
    }

}
