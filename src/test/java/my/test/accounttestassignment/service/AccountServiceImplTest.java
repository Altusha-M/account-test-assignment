package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Operation;
import my.test.accounttestassignment.repository.AccountRepository;
import my.test.accounttestassignment.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountService accountService;
    private OperationRepository operationRepository;
    private OperationService operationService;

    public AccountServiceImplTest(AccountRepository accountRepository,
                                  AccountService accountService,
                                  OperationRepository operationRepository,
                                  OperationService operationService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.operationRepository = operationRepository;
        this.operationService = operationService;
    }

    @Test
    void credit() {
    }

    @Test
    void debit() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAccountByAccountNumber() {
    }

    @Test
    void save() {
    }
}