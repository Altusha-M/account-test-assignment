package my.test.accounttestassignment.repository;

import my.test.accounttestassignment.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findOperationByAccount_AccountNumber(String accountNumber);

}
