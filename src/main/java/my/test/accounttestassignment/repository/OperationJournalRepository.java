package my.test.accounttestassignment.repository;

import my.test.accounttestassignment.entity.OperationJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationJournalRepository extends JpaRepository<OperationJournal, Long> {
}
