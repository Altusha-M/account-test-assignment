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

    private final AccountRepository accountRepository;
    private final OperationService operationService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OperationService operationService) {
        this.accountRepository = accountRepository;
        this.operationService = operationService;
    }

    /**
     * Make cash in operation for account with given number.
     *
     * @param accountToCredit account which will deposit money
     * @param amountToAdd     amount of money added to the {@code accountNumber}
     * @return Long amount of money after addition or -1L if there is no account with {@code accountNumber}
     */
    @Override
    @Transactional
    public Optional<Account> credit(Account accountToCredit, Long amountToAdd) {

        String accountNumber = Optional.ofNullable(accountToCredit.getAccountNumber()).orElseGet(String::new);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        boolean accountPresent = optionalAccount.isPresent();

        if (!accountPresent) {
            return Optional.of(new Account());
        } else {
            Account account = optionalAccount.get();
            Long oldAmount = account.getAmount();
            Long newAmount = oldAmount + amountToAdd;
            accountRepository.updateAccountAmountByNumber(accountNumber, newAmount);
            accountToCredit.setAmount(newAmount);
            accountToCredit = accountRepository.findByAccountNumber(accountNumber).orElseGet(Account::new);
            operationService.save("credit", accountToCredit, amountToAdd, oldAmount, newAmount);
            return Optional.of(accountToCredit);
        }
    }

    /**
     * Make charge off operation for account with given number.
     *
     * @param accountToDebit account which will withdraw money
     * @param amountToRemove amount of money added to the {@code accountNumber}
     * @return account from db after update or new Account if there is no accountToDebit
     * @throws NotEnoughMoneyException if there is no enough money on given account
     */
    @Override
    @Transactional
    public Optional<Account> debit(Account accountToDebit, Long amountToRemove) {

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String accountNumber = Optional.ofNullable(accountToDebit.getAccountNumber()).orElseGet(String::new);
        Optional<Account> optionalAccount;
        optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        boolean accountPresent = optionalAccount.isPresent();

        if (!accountPresent) {
            return Optional.of(new Account());
        } else {
            Account account = optionalAccount.get();
            Long oldAmount = account.getAmount();
            if (oldAmount < amountToRemove) {
                operationService.save("debitError", accountToDebit, amountToRemove, oldAmount, oldAmount);
                throw new NotEnoughMoneyException("add more money before remove");
            }
            Long newAmount = oldAmount - amountToRemove;
            accountToDebit.setAmount(newAmount);
            accountRepository.updateAccountAmountByNumber(accountNumber, newAmount);
            accountToDebit = accountRepository.findByAccountNumber(accountNumber).orElseGet(Account::new);
            operationService.save("debit", accountToDebit, amountToRemove, oldAmount, newAmount);
            return Optional.of(accountToDebit);
        }
    }

    /**
     * @param id id of account
     * @return Optional of Account if exists or empty Optional
     */
    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * @return all existing accounts
     */
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * Finds account with specified number
     *
     * @param number number of account
     * @return Optional of Account if exists or empty Optional
     */
    @Override
    public Optional<Account> findAccountByAccountNumber(String number) {
        return accountRepository.findByAccountNumber(number);
    }

    /**
     * creates account in DB and creates first credit operation on this account
     *
     * @param account account which wanted to be create
     * @return Optional of created account or empty account if account with accountNumber already exists
     */
    @Override
    @Transactional
    public Optional<Account> save(Account account) {
        if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            return Optional.of(new Account());
        }
        Long amountToCredit = account.getAmount();
        Account accountToSave = new Account(null, account.getAccountNumber(), 0L);
        accountRepository.save(accountToSave);
        this.credit(accountToSave, amountToCredit);
        return accountRepository.findByAccountNumber(accountToSave.getAccountNumber());
    }


}
