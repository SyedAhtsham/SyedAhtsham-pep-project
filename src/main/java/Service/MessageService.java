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

    public Message createNewMessage(Message message){
        
        if(message.getMessage_text()!="" && message.getMessage_text().length()<255 && accountDAO.checkIfExist(message.getPosted_by()) ){
            return messageDAO.insertMessage(message);
        }
        
        return null;
        
    }

    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public Message getMessageByID(int message_id){
        return messageDAO.retrieveSingleMessage(message_id);
    }

    public Message deleteMessage(int message_id){
        Message message = messageDAO.retrieveSingleMessage(message_id);
        if(message==null){
            return null;
        }
        messageDAO.deleteMessageByID(message_id);
        return message;
        
    }

    public Message updateMessage(int message_id, String newMessageText){
        // if the message doesn't exist
        if( messageDAO.retrieveSingleMessage(message_id)==null){
            return null;
        }
        if(newMessageText!="" && newMessageText.length()<255){
            messageDAO.updateMessageText(message_id, newMessageText);
            return messageDAO.retrieveSingleMessage(message_id);
        }
        return null;
        
    }

}
