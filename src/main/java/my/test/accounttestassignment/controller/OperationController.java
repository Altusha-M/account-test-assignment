package my.test.accounttestassignment.controller;

import my.test.accounttestassignment.entity.Operation;
import my.test.accounttestassignment.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public List<Operation> getOperation() {
        return operationService.getAllOperation();
    }

    @GetMapping("/{accountNumber}")
    public List<Operation> getOperation(@PathVariable String accountNumber) {
        return operationService.getOperationByAccountNumber(accountNumber);
    }

}
