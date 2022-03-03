package com.bootcoin.transaction.repository;

import com.bootcoin.transaction.model.ApproveTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IApproveTransaction extends ReactiveMongoRepository<ApproveTransaction, String> {
}
