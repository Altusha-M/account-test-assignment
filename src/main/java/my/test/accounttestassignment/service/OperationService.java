package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.entity.Operation;

import java.util.List;


public interface OperationService {

    void save(String operationType, Account account, Long operationSum, Long amountBefore, Long amountAfter);

    List<Operation> getAllOperation();

    List<Operation> getOperationByAccountNumber(String accountNumber);

}
