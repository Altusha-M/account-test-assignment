package my.test.accounttestassignment.service;


import my.test.accounttestassignment.entity.Account;
import my.test.accounttestassignment.entity.Operation;
import my.test.accounttestassignment.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("operationService")
public class OperationServiceImpl implements OperationService {

    OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void save(String operationType, Account account, Long operationSum) {
        Operation operation = new Operation();
        operation.setType(operationType);
        operation.setAccount(account);
        operation.setDateTime(LocalDateTime.now());
        operation.setSum(operationSum);
        operationRepository.save(operation);
    }

    @Override
    public List<Operation> getAllOperation() {
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> getOperationByAccountNumber(String accountNumber) {
        return operationRepository.findOperationByAccount_AccountNumber(accountNumber);
    }

}
