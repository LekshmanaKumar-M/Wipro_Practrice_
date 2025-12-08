package com.myfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myfin.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
