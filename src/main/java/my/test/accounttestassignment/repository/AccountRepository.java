package my.test.accounttestassignment.repository;

import my.test.accounttestassignment.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Account a SET a.amount = :newAmount WHERE a.accountNumber = :accountNumber")
    void updateAccountAmountByNumber(@Param("accountNumber") String accountNumber, @Param("newAmount") Long newAmount);
    
}