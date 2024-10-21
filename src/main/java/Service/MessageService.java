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

}
