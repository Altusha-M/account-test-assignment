package my.test.accounttestassignment.service;

import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.entity.Operation;

import java.util.List;


public interface OperationService {

    void save(String methodName, Account account, Long operationSum);

    List<Operation> getAllOperation();

    List<Operation> getOperationByAccountNumber(String accountNumber);

}
