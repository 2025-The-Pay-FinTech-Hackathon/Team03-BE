package com.example.fintech.domain.transaction.repository;


import com.example.fintech.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.id = :accountId " +
            "AND t.amount < 0 " +
            "AND t.timestamp BETWEEN :startOfDay AND :endOfDay")
    List<Transaction> findTodayExpenses(
            @Param("accountId") Long accountId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.account.id = :accountId " +
            "AND t.amount < 0 " +
            "AND t.timestamp BETWEEN :startOfDay AND :endOfDay")
    int sumTodayExpenses(
            @Param("accountId") Long accountId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.id IN :accountId " +
            "AND t.amount < 0 " +
            "AND t.timestamp BETWEEN :start AND :end")
    List<Transaction> findMonthlyExpenses(@Param("accountId") Long accountId,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.id = :accountId " +
            "AND t.timestamp BETWEEN :startDate AND :endDate " +
            "ORDER BY t.timestamp DESC")
    List<Transaction> findMonthlyTransactions(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


}
