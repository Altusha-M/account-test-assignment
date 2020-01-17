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
 *
 * @author m.altynov
 */
class AccountServiceUnitTest {

    private final Account account = new Account(1L, "12345", 50L);

    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    private AccountService accountService;

    /**
     * initializes services and specifies methods for mock's
     */
    @BeforeEach
    void initAccountService() {
        OperationService operationService = new OperationServiceImpl(operationRepository);
        accountService = new AccountServiceImpl(accountRepository, operationService);

        Mockito.doNothing()
                .when(accountRepository)
                .updateAccountAmountByNumber(account.getAccountNumber(), 0L);

        Mockito.when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepository.save(account))
                .thenReturn(account);
    }

    /**
     * checks equality of expected and actual values if account doesn't exists and if exists
     * after that checks number of calls methods inside the credit method
     */
    @Test
    void creditTest() {
        assertEquals(Optional.of(new Account()), accountService.credit(new Account(), 50L));

        assertEquals(Optional.of(account), accountService.credit(account, 50L));

        Mockito.verify(accountRepository, Mockito.times(1))
                .updateAccountAmountByNumber(anyString(), anyLong());
        Mockito.verify(operationRepository, Mockito.times(1))
                .save(any(Operation.class));
    }


    /**
     * checks equality of expected and actual values if account doesn't exists and if exists
     * after that checks number of calls methods inside the debit method
     */
    @Test
    void debitTest() {
        assertEquals(Optional.of(new Account()), accountService.debit(new Account(), 50L));

        assertEquals(Optional.of(account), accountService.debit(account, 50L));

        Mockito.verify(accountRepository, Mockito.times(1))
                .updateAccountAmountByNumber(anyString(), anyLong());
        Mockito.verify(operationRepository, Mockito.times(1))
                .save(any(Operation.class));
    }

    /**
     * checks equality of expected and actual values if account doesn't exists and if exists
     * after that checks number of calls methods inside this method
     */
    @Test
    void findByIdTest() {
        assertEquals(Optional.empty(), accountService.findById(3L));

        assertEquals(Optional.of(account), accountService.findById(1L));

        Mockito.verify(accountRepository, Mockito.times(2))
                .findById(anyLong());
    }


    /**
     * checks equality of expected and actual values if account doesn't exists and if exists
     * after that checks number of calls methods inside this method
     */
    @Test
    void findAllTest() {
        assertEquals(new ArrayList<>(), accountService.findAll());
        assertTrue(accountService.findAll().isEmpty());

        Mockito.verify(accountRepository, Mockito.times(2))
                .findAll();
    }

    /**
     * checks equality of expected and actual values if account doesn't exists and if exists
     * after that checks number of calls methods inside this method
     */
    @Test
    void findAccountByAccountNumberTest() {
        assertEquals(Optional.empty(), accountService.findAccountByAccountNumber("1234"));
        assertEquals(Optional.of(account), accountService.findAccountByAccountNumber("12345"));

        Mockito.verify(accountRepository, Mockito.times(2))
                .findByAccountNumber(anyString());
    }

    /**
     * checks equality of expected and actual values
     * after that checks number of calls methods inside this method
     */
    @Test
    void saveTest() {
        Mockito.when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(account));
        Optional<Account> savedAccount = accountService.save(account);
        assertEquals(account, savedAccount.orElseGet(Account::new));
    }

}