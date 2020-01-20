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

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * save new operation in db
     *
     * @param operationType type of operation (debit or credit)
     * @param account       instance of operation's Account
     * @param operationSum  sum of money on operation
     * @param amountBefore  amount of money on Account before operation
     * @param amountAfter   amount of money on Account after operation
     */
    @Override
    public void save(String operationType, Account account, Long operationSum, Long amountBefore, Long amountAfter) {
        Operation operation = new Operation();
        operation.setType(operationType);
        operation.setAccount(account);
        operation.setDateTime(LocalDateTime.now());
        operation.setSum(operationSum);
        operation.setAmountBefore(amountBefore);
        operation.setAmountAfter(amountAfter);
        operationRepository.save(operation);
    }

    /**
     * find all operations in db
     *
     * @return List of all operations
     */
    @Override
    public List<Operation> getAllOperation() {
        return operationRepository.findAll();
    }

    /**
     * find all operations by specified account in db
     *
     * @param accountNumber number of required account
     * @return List of operations by required account
     */
    @Override
    public List<Operation> getOperationByAccountNumber(String accountNumber) {
        return operationRepository.findOperationByAccount_AccountNumber(accountNumber);
    }

}
