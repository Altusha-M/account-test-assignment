package my.test.accounttestassignment.entity;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;
import java.time.LocalDateTime;

@Entity
@Table
public class OperationJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "pk_operation_sequence")
    @SequenceGenerator(name = "pk_operation_sequence",
            sequenceName = "operation_id_seq",
            allocationSize = 1)
    private Long id;

    @Column
    private String httpMessage;

    @Column
    private String httpMethod;

    @Column
    private String httpBody;

    @Column
    private String httpResponseCode;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column
    private Long accountId;

}
