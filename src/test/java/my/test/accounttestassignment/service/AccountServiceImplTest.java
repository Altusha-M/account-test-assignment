package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceImplTest {

    private static String accountNumber = "40817810112341234567";
    private static Long accountAmount = 10000L;
    private static Account testAccount = new Account(1L, accountNumber, accountAmount);

    private AccountService accountService;
    private OperationService operationService;

    @Autowired
    public AccountServiceImplTest(AccountService accountService,
                                  OperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    /**
     * tries to get all accounts from empty db(expects empty @code{List<>}), save new account to db, find it in db and
     * compare saved and founded accounts
     */
    @Test
    void saveTest() {
        assertTrue(accountService.findAll().isEmpty());
        accountService.save(testAccount);
        assertEquals(Optional.of(testAccount), accountService.findAccountByAccountNumber(accountNumber));
        assertFalse(accountService.findAll().isEmpty());
        assertEquals(Optional.of(new Account()), accountService.save(testAccount));
    }

    @Test
    void creditTest() {
        assertEquals(Optional.of(new Account()), accountService.credit(new Account(), anyLong()));
        System.out.println(testAccount);
        assertEquals(Optional.of(testAccount), accountService.credit(testAccount, 100L));
        System.out.println(testAccount);
        assertEquals(2, operationService.getOperationByAccountNumber(testAccount.getAccountNumber()).size());
    }

    @Test
    void debitTest() {
        assertEquals(Optional.of(new Account()), accountService.debit(new Account(), anyLong()));
        System.out.println(testAccount);
        assertEquals(Optional.of(testAccount), accountService.debit(testAccount, 1000L));
        System.out.println(testAccount);
        assertEquals(3, operationService.getOperationByAccountNumber(testAccount.getAccountNumber()).size());
    }

    @Test
    void findAllTest() {
        System.out.println(accountService.findAccountByAccountNumber(testAccount.getAccountNumber()));
        System.out.println(testAccount);
        assertEquals(Collections.singletonList(testAccount), accountService.findAll());
        Account newAccountToSave = new Account(null, "123456", 100L);
        accountService.save(newAccountToSave);
        assertEquals(Arrays.asList(testAccount, newAccountToSave), accountService.findAll());
    }

    @Test
    void findByIdTest() {
        System.out.println(accountService.findAccountByAccountNumber(testAccount.getAccountNumber()));
        System.out.println(testAccount);
        assertEquals(Optional.of(testAccount), accountService.findAccountByAccountNumber(testAccount.getAccountNumber()));
    }

    @Test
    void findAccountByAccountNumberTest() {
        assertEquals(Optional.of(testAccount), accountService.findAccountByAccountNumber(testAccount.getAccountNumber()));
    }

}