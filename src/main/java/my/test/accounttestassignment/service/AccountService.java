package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.exception.NotEnoughMoneyException;

import java.util.List;
import java.util.Optional;


public interface AccountService {

    Optional<Account> credit(Account accountToCredit, Long amountToAdd);

    Optional<Account> debit(Account accountToDebit, Long amountToRemove) throws NotEnoughMoneyException;

    Optional<Account> findById(Long id);

    List<Account> findAll();

    Optional<Account> findAccountByAccountNumber(String number);

    Optional<Account> save(Account account);

}
