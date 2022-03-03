package com.bootcoin.transaction.service;

import com.bootcoin.transaction.model.ApproveTransaction;
import com.bootcoin.transaction.model.PurchaseTransaction;
import com.bootcoin.transaction.model.dto.PurchaseTransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPurchaseTransactionService {

    Flux<PurchaseTransactionDto> findAll();

    Mono<PurchaseTransactionDto> findById(String id);

    Mono<PurchaseTransactionDto> create(PurchaseTransaction purchaseTransaction);

    PurchaseTransactionDto getPurchaseDto(PurchaseTransaction purchaseTransaction);

    PurchaseTransaction getPurchase(PurchaseTransactionDto purchaseTransactionDto);

    Mono<ApproveTransaction> createApproveTransaction(ApproveTransaction approveTransaction);
}
