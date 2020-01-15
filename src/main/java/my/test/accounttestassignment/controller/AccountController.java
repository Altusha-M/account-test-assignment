package my.test.accounttestassignment.controller;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.exception.NotEnoughMoneyException;
import my.test.accounttestassignment.service.AccountService;
import my.test.accounttestassignment.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    AccountService accountService;
    OperationService operationService;

    @Autowired
    public AccountController(AccountService accountService, OperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccount() {
        ResponseEntity<List<Account>> result = new ResponseEntity<List<Account>>(
                accountService.findAll(),
                HttpStatus.OK);
        return result;
    }

    @GetMapping("/{number}")
    public ResponseEntity<Account> getAccount(@PathVariable String number) {
        Optional<Account> accountServiceById = accountService.findAccountByAccountNumber(number);
        boolean present = accountServiceById.isPresent();
        return new ResponseEntity<Account>(present ?
                accountServiceById.get() : new Account(),
                present ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {
        return new ResponseEntity<>(accountService.save(newAccount), HttpStatus.CREATED);
    }

    @PatchMapping("/{accountNumber}/credit/{amountToAdd}")
    public Long updateCredit(@PathVariable String amountToAdd,
                               @PathVariable String accountNumber) {
        System.out.println(amountToAdd);

        Long operationAnswer = accountService.credit(accountNumber, Long.parseLong(amountToAdd));
        return operationAnswer;
    }

    @PatchMapping("/{accountNumber}/debit/{amountToRemove}")
    public ResponseEntity<?> updateDebit(@PathVariable String amountToRemove,
                               @PathVariable String accountNumber) {
        System.out.println(amountToRemove);

        Long operationAnswer;
//        try {
            operationAnswer = accountService.debit(accountNumber, Long.parseLong(amountToRemove));
//        } catch (NotEnoughMoneyException e) {
//            Account acc = new Account();
//            acc.setAmount(-1);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        //TODO разобраться с ответом серверу тут и в credit
        return new ResponseEntity<>(operationAnswer > 0 ? "{\"newAmount\": " + operationAnswer + "}" : "{\"newAmount\": -1}", HttpStatus.OK);
    }

}
