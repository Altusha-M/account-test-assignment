package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.exception.NotEnoughMoneyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AccountService {

    Long credit(String accountNumber, Long amountToAdd);

    Long debit(String accountNumber, Long amountTo) throws NotEnoughMoneyException;

    Optional<Account> findById(Long id);

    List<Account> findAll();

    Optional<Account> findAccountByAccountNumber(String number);

    Account save(Account account);

}
