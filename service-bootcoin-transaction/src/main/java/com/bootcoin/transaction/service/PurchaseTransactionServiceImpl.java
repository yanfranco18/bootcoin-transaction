package com.bootcoin.transaction.service;

import com.bootcoin.transaction.configWebClient.bootcoinClient.service.BootcoinServiceWebClient;
import com.bootcoin.transaction.model.ApproveTransaction;
import com.bootcoin.transaction.model.PurchaseTransaction;
import com.bootcoin.transaction.model.dto.PurchaseTransactionDto;
import com.bootcoin.transaction.repository.IApproveTransaction;
import com.bootcoin.transaction.repository.IPurchaseTransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PurchaseTransactionServiceImpl implements IPurchaseTransactionService{

    private final IPurchaseTransactionRepository repository;
    private final IApproveTransaction approveRepository;
    //permite convertir objetos java para que coincidan con la esrtuctura json
    private final ObjectMapper objectMapper;
    private final BootcoinServiceWebClient bootcoinServiceWebClient;

    @Override
    public Flux<PurchaseTransactionDto> findAll() {
        return repository.findAll().flatMap(this::getPurchaseTransactionDto);
    }

    @Override
    public Mono<PurchaseTransactionDto> findById(String id) {
        return repository.findById(id).flatMap(this::getPurchaseTransactionDto);
    }

    @Override
    public Mono<PurchaseTransactionDto> create(PurchaseTransaction purchaseTransaction) {
        return bootcoinServiceWebClient.findById(purchaseTransaction.getIdBootcoinTasaCompra())
                        .flatMap(a -> {
                            purchaseTransaction.setIdBootcoinTasaCompra(a.getId());
                            purchaseTransaction.setDestinationNumber(purchaseTransaction.getDestinationNumber());
                            purchaseTransaction.setOriginNumber(purchaseTransaction.getOriginNumber());
                            purchaseTransaction.setPaymentMode(purchaseTransaction.getPaymentMode());
                            purchaseTransaction.setAmountSol(purchaseTransaction.getAmountSol());
                            Double tasaCompra = a.getTasaCompra();
                            Double cambio = tasaCompra * purchaseTransaction.getAmountSol();
                            purchaseTransaction.setAmountBootcoin(cambio);
                         return repository.save(purchaseTransaction).flatMap(this::getPurchaseTransactionDto);
                        });
    }

    private Mono<PurchaseTransactionDto>  getPurchaseTransactionDto(PurchaseTransaction purchaseTransaction) {
        return Mono.just(objectMapper.convertValue(purchaseTransaction, PurchaseTransactionDto.class));
    }

    @Override
    public PurchaseTransactionDto getPurchaseDto(PurchaseTransaction purchaseTransaction) {
        return objectMapper.convertValue(purchaseTransaction, PurchaseTransactionDto.class);
    }

    @Override
    public PurchaseTransaction getPurchase(PurchaseTransactionDto purchaseTransactionDto) {
        return objectMapper.convertValue(purchaseTransactionDto, PurchaseTransaction.class);
    }

    @Override
    public Mono<ApproveTransaction> createApproveTransaction(ApproveTransaction approveTransaction) {
        return repository.findById(approveTransaction.getIdPurchaseTransaction())
                .flatMap(at -> {
                    approveTransaction.setNumberTransaction(approveTransaction.getNumberTransaction());
                    approveTransaction.setIdPurchaseTransaction(at.getId());
                    approveTransaction.setStatus(approveTransaction.getStatus());
                    return approveRepository.save(approveTransaction);
                });
    }
}
