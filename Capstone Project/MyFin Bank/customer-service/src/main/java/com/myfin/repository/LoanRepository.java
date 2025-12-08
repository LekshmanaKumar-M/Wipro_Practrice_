package com.myfin.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myfin.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByAccountNumber(String accountNumber);
}
