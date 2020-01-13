package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void credit(String accountNumber, Long amountToAdd) {
        // TODO
    }

    @Override
    public void debit(String accountNumber, Long amountToRemove) {
        // TODO
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return null;
        // TODO
    }

    @Override
    public Optional<Account> findAccountByAccountNumber(Long number) {
        return accountRepository.findAccountByAccountNumber(number.toString());
    }

}
