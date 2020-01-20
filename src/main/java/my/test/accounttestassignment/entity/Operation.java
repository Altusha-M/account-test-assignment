package my.test.accounttestassignment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "pk_operation_sequence")
    @SequenceGenerator(name = "pk_operation_sequence",
            sequenceName = "operation_id_seq",
            allocationSize = 1)
    private Long id;

    @Column
    private String type;

    @Column
    private Long sum;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private Long amountBefore;

    @Column
    private Long amountAfter;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(Long amountBefore) {
        this.amountBefore = amountBefore;
    }

    public Long getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(Long amountAfter) {
        this.amountAfter = amountAfter;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sum=" + sum +
                ", dateTime=" + dateTime +
                ", account=" + account +
                ", amountBefore=" + amountBefore +
                ", amountAfter=" + amountAfter +
                '}';
    }
}
