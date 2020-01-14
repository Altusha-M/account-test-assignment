package my.test.accounttestassignment.controller;

import my.test.accounttestassignment.AccountTestAssignmentApplication;
import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.repository.AccountRepository;
import my.test.accounttestassignment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
            this.accountService = accountService;
    }

    @GetMapping("/account")
    public ResponseEntity<String> getAllAccount() {

        return new ResponseEntity<String>(
                accountService.findAll().stream()
                .map(x -> "<br>"+ x.toString() + "<br>")
                .collect(Collectors.toList())
                .toString(),
                HttpStatus.OK);
    }

    @GetMapping("/account/{number}")
    public ResponseEntity<String> getAccount(@PathVariable Long number) {
        Optional<Account> accountServiceById = accountService.findById(number);
        boolean present = accountServiceById.isPresent();
        return new ResponseEntity<String>( present ?
                accountServiceById.get().toString() : "account " + number + " not found",
                present ? HttpStatus.OK : HttpStatus.NOT_FOUND
                );
    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody Account newAccount, HttpRequest httpMessage){
        return new ResponseEntity<String>(accountService.save(newAccount).toString(), HttpStatus.CREATED);
    }

    @PatchMapping("/account/{accountNumber}/add/{amountToAdd}")
    public String updateAddMoney(@PathVariable String amountToAdd,
                                 @PathVariable String accountNumber){
        System.out.println(amountToAdd);
        accountService.credit(accountNumber, Long.parseLong(amountToAdd));
        return new Account().toString();
    }

}
