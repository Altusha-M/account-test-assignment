package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.entity.Operation;
import my.test.accounttestassignment.repository.AccountRepository;
import my.test.accounttestassignment.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

/**
 * unit-testing of AccountService methods
 * @author m.altynov
 */
class AccountServiceUnitTest {

    private final Account account = new Account(1L, "12345", 50L);

    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    private AccountService accountService;

    @BeforeEach
    void initAccountService() {
        OperationService operationService = new OperationServiceImpl(operationRepository);
        accountService = new AccountServiceImpl(accountRepository, operationService);

        Mockito.doNothing()
                .when(accountRepository)
                .updateAccountAmountByNumber(account.getAccountNumber(), 0L);
        Mockito.when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepository.save(account))
                .thenReturn(account);
    }

    @Test
    void creditTest() {
        assertEquals(-1L, accountService.credit(anyString(), 50L));

        assertEquals(100L, accountService.credit(account.getAccountNumber(), 50L));

        Mockito.verify(accountRepository, Mockito.times(1))
                .updateAccountAmountByNumber(anyString(), anyLong());
        Mockito.verify(operationRepository, Mockito.times(1))
                .save(any(Operation.class));
    }

    @Test
    void debitTest() {
        assertEquals(-1L, accountService.debit(anyString(), 50L));

        assertEquals(0L, accountService.debit(account.getAccountNumber(), 50L));

        Mockito.verify(accountRepository, Mockito.times(1))
                .updateAccountAmountByNumber(anyString(), anyLong());
        Mockito.verify(operationRepository, Mockito.times(1))
                .save(any(Operation.class));
    }

    @Test
    void findByIdTest() {

        assertEquals(Optional.empty(), accountService.findById(1L));
        Mockito.verify(accountRepository, Mockito.times(1))
                .findById(anyLong());


    }

    @Test
    void findAllTest() {
        assertEquals(new ArrayList<>(), accountService.findAll());
        assertTrue(accountService.findAll().isEmpty());

        Mockito.verify(accountRepository, Mockito.times(2))
                .findAll();
    }

    @Test
    void findAccountByAccountNumberTest() {

        assertEquals(Optional.empty(), accountService.findAccountByAccountNumber("1234"));

        Mockito.verify(accountRepository, Mockito.times(1))
                .findByAccountNumber(anyString());

    }

    @Test
    void saveTest() {
        assertEquals(Optional.of(new Account()), accountService.save(account));
        Account accountToSave = new Account(2L, "123", 100L);

        Mockito.when(accountRepository.findByAccountNumber(accountToSave.getAccountNumber()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(accountToSave));
        Optional<Account> savedAccount = accountService.save(accountToSave);
        assertEquals(accountToSave, savedAccount.orElseGet(Account::new));
    }

}