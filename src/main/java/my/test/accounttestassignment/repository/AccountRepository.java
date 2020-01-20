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

    /**
     * find account with specified number
     *
     * @param accountNumber number of required account
     * @return Optional of account with accountNumber
     */
    Optional<Account> findByAccountNumber(String accountNumber);


    /**
     * HQL expression to update account with accountNumber amount to newAmount
     *
     * @param accountNumber number of account which will be updated
     * @param newAmount     amount of money which will be rewritten to accountNumber
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Account a SET a.amount = :newAmount WHERE a.accountNumber = :accountNumber")
    void updateAccountAmountByNumber(@Param("accountNumber") String accountNumber, @Param("newAmount") Long newAmount);

}