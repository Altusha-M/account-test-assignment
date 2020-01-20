package my.test.accounttestassignment.controller;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.service.AccountService;
import my.test.accounttestassignment.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest API for my application for /api/account URI's
 *
 * @author m.altynov
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;

    @Autowired
    public AccountController(AccountService accountService, OperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    /**
     * Returns list of all existing accounts or empty list if no one
     *
     * @return list of all existing accounts or empty list if no one
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAccount() {
        ResponseEntity<List<Account>> result = new ResponseEntity<List<Account>>(
                accountService.findAll(),
                HttpStatus.OK);
        return result;
    }

    /**
     * Returns account with specified number and http 200 response code if account exists
     * or account with null fields and http 404 response code if there is no account with specified number
     *
     * @param number specified number of account to return
     * @return Account and HttpStatus.OK if exists or empty Account and HttpStatus.NOT_FOUND
     */
    @GetMapping("/{number}")
    public ResponseEntity<Account> getAccount(@PathVariable String number) {
        Optional<Account> accountServiceById = accountService.findAccountByAccountNumber(number);
        boolean present = accountServiceById.isPresent();
        return new ResponseEntity<Account>(
                present ? accountServiceById.get() : new Account(),
                present ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Creates new account in DB in response on POST
     *
     * @param newAccount account without id
     * @return 201 created response with created object of Account or empty Account
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {
        Optional<Account> savedAccount = accountService.save(newAccount);
        boolean present = savedAccount.isPresent();
        return new ResponseEntity<>(
                present ? savedAccount.get() : new Account(),
                present ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    /**
     * @param amountToAdd   amount of money added to account with accountNumber
     * @param accountNumber number of account to upgrade
     * @return updated Account and 200 response code if update successfully or empty Account and 400 response code
     */
    @PatchMapping("/{accountNumber}/credit/{amountToAdd}")
    public ResponseEntity<Account> updateCredit(@PathVariable String amountToAdd,
                                                @PathVariable String accountNumber) {
        Optional<Account> account;
        synchronized (Account.class) {
            account = accountService.credit(
                    accountService.findAccountByAccountNumber(accountNumber).orElseGet(Account::new),
                    Long.parseLong(amountToAdd)
            );
        }
        boolean present = account.isPresent();
        return new ResponseEntity<Account>(
                present ? account.get() : new Account(),
                present ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @param amountToRemove amount of money removed from account with accountNumber
     * @param accountNumber  number of account to upgrade
     * @return updated Account and 200 response code if update successfully or empty Account and 400 response code
     */
    @PatchMapping("/{accountNumber}/debit/{amountToRemove}")
    public ResponseEntity<?> updateDebit(@PathVariable String amountToRemove,
                                         @PathVariable String accountNumber) {
        Optional<Account> account;
        synchronized (Account.class) {
            account = accountService.debit(
                    accountService.findAccountByAccountNumber(accountNumber).orElseGet(Account::new),
                    Long.parseLong(amountToRemove)
            );
        }
        boolean present = account.isPresent();

        return new ResponseEntity<Account>(
                present ? account.get() : new Account(),
                present ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST
        );
    }

}
