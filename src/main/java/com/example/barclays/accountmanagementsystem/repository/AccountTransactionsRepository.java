package com.example.barclays.accountmanagementsystem.repository;

import com.example.barclays.accountmanagementsystem.entity.AccountTransactions;
import com.example.barclays.accountmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions,Integer> {

    @Query(value = "SELECT * FROM account_transactions WHERE ACCOUNT_NO = ?1 ORDER BY DATE DESC LIMIT 5;",nativeQuery = true)
    List<AccountTransactions> fetchMiniTransactions(int account_no);

    @Query(value = "SELECT * FROM account_transactions WHERE ACCOUNT_NO = ?1 AND DATE BETWEEN ?2 AND ?3 ORDER BY DATE DESC LIMIT 5;",nativeQuery = true)
    List<AccountTransactions> fetchMiniTransactionsBetweenDates(int account_no, Date ll,Date ul);

    @Query(value = "SELECT sum(amount) FROM account_transactions where account_no = ?1 and type = \"Debit\" and date(date)=curdate()",nativeQuery = true)
    double fetchDailyDebitTransactionsSum(int account_no);
}
