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

    public Account registerAccount(Account account){
        if(account.getUsername()!="" && account.password.length()>=4 && !accountDAO.checkIfAlreadyExist(account.getUsername())){
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    public Account logInToAccount(Account account){
        return new Account();
    }

}
