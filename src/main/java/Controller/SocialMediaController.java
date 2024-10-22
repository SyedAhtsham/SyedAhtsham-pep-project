package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getSingleMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        
        
        Account addedAccount = accountService.registerAccount(account);

        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }
        else{
            context.status(400);
        }

    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);

        Account loggedInAccount = accountService.logInToAccount(account);

        if(loggedInAccount!=null){
            context.json(mapper.writeValueAsString(loggedInAccount));
        }else{
            context.status(401);
        }

    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);

        Message postedMessage = messageService.createNewMessage(message);

        if(postedMessage!=null){
            context.json(mapper.writeValueAsString(postedMessage)).status(200);

        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();

        List<Message> allMessages = messageService.getAllMessages();
        context.json(mapper.writeValueAsString(allMessages));
    }

    public void getSingleMessageHandler(Context context) throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();

        int messageID = Integer.parseInt(context.pathParam("message_id"));

        Message message = messageService.getMessageByID(messageID);
        if(message == null){
            context.json("");
        }else{
            context.json(mapper.writeValueAsString(message));
        }
        
    }

    public void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(context.pathParam("message_id"));

        Message message = messageService.deleteMessage(messageID);

        if(message==null){
            context.json("");
        }else{
            context.json(mapper.writeValueAsString(message));
        }
    }
}