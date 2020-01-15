package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.exception.NotEnoughMoneyException;
import my.test.accounttestassignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    OperationService operationService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OperationService operationService) {
        this.accountRepository = accountRepository;
        this.operationService = operationService;
    }

    /**
     * Make cash in operation for account with given number.
     * @param accountNumber
     *          number of account which will deposit money
     * @param amountToAdd
     *          amount of money added to the {@code accountNumber}
     * @return
     *          Long amount of money after addition or -1L if there is no account with {@code accountNumber}
     */
    @Override
    @Transactional
    public Long credit(String accountNumber, Long amountToAdd) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        boolean accountPresent = optionalAccount.isPresent();
        if (!accountPresent) {
            return -1L;
        } else {
            Account account = optionalAccount.get();
            Long newAmount = account.getAmount() + amountToAdd;
            accountRepository.updateAccountAmountByNumber(accountNumber, newAmount);
            operationService.save("credit", account, amountToAdd);
            return newAmount;
        }
    }

    /**
     * Make charge off operation for account with given number.
     * @param accountNumber
     *          number of account which will withdraw money
     * @param amountToRemove
     *          amount of money added to the {@code accountNumber}
     * @return
     *          Long amount of money after removing or -1L if there is no account with {@code accountNumber}
     * @throws NotEnoughMoneyException
     *          if there is no enough money on given account
     */
    @Override
    @Transactional
    public Long debit(String accountNumber, Long amountToRemove) {

        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        boolean accountPresent = optionalAccount.isPresent();

        if (!accountPresent) {
            return -1L;
        } else {
            Account account = optionalAccount.get();
            Long oldAmount = account.getAmount();
            if (oldAmount < amountToRemove) {
                throw new NotEnoughMoneyException("add more money before remove");
            }
            Long newAmount = oldAmount - amountToRemove;

            accountRepository.updateAccountAmountByNumber(accountNumber, newAmount);
            operationService.save("debit", account, amountToRemove);
            return newAmount;
        }

    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findAccountByAccountNumber(String number) {
        return accountRepository.findByAccountNumber(number.toString());
    }

    @Override
    public Account save(Account account){
        return accountRepository.save(account);
    }

}
