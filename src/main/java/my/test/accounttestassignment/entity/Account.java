package my.test.accounttestassignment.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO,
//                    generator = "pk_account_sequence")
//    @SequenceGenerator(name = "pk_account_sequence",
//                        sequenceName = "account_id_seq",
//                        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Long amount;

    public Long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
