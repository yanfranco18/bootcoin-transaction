package com.bootcoin.transaction.repository;

import com.bootcoin.transaction.model.PurchaseTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPurchaseTransactionRepository extends ReactiveMongoRepository<PurchaseTransaction, String> {
}
