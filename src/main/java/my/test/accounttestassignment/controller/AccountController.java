package my.test.accounttestassignment.controller;

import my.test.accounttestassignment.AccountTestAssignmentApplication;
import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.repository.AccountRepository;
import my.test.accounttestassignment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    AccountService accountService;
    AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public List<Account> getAllAccount(){
        return accountService.findAll();
    }

    @GetMapping("/account/{number}")
    public String getAccount(@PathVariable String number){
        Optional<Account> accountServiceById = accountService.findById(Long.parseLong(number));
        return accountServiceById.isPresent() ? accountServiceById.get().toString() : new String ("account " + number + " not found");
    }

    @PostMapping("/account")
    public Account createAccount(@RequestBody Account newAccount){
        accountRepository.save(newAccount);
        return newAccount;
    }

    @PatchMapping("/account/{number}/add")
    public Account updateAddMoney(@RequestBody String amount, @PathVariable String number){
//        Optional<Account> accountToDebit = accountRepository.findByAccountNumber(Long.parseLong(number));
        return new Account();
        // TODO
//        return accountToDebit;
    }
}
