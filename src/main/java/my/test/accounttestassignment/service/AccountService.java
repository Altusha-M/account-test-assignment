package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void credit(String accountNumber, Long amountToAdd);

    void debit(String accountNumber, Long amountTo);

    Optional<Account> findById(Long id);

    List<Account> findAll();

    Optional<Account> findAccountByAccountNumber(Long number);
}
