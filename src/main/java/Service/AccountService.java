package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Insert a new account into the database using Account Data Access Object-DAO function
     * @param account account object that needs to be persisted into database
     * @return if account is inserted successfully, return an account object containing account_id and other fields otherwise return
     * null
     */
    public Account registerAccount(Account account){
        //checking if username is non-empty, password has atleast 4 characters, and account doesn't already exist with this username
        if(account.getUsername()!="" && account.password.length()>=4 && !accountDAO.checkIfExistByUsername(account.getUsername())){
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    /**
     * Function to log in to account using Account DAO's login method
     * @param account account object containing username and password
     * @return account object that is logged in 
     */
    public Account logInToAccount(Account account){
        return accountDAO.logInAccount(account);
    }

}
