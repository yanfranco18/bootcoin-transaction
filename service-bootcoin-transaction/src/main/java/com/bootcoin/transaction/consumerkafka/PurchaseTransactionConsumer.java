package com.bootcoin.transaction.consumerkafka;

import com.bootcoin.transaction.model.PurchaseTransaction;
import com.bootcoin.transaction.model.dto.PurchaseTransactionDto;
import com.bootcoin.transaction.service.IPurchaseTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PurchaseTransactionConsumer {
    @Autowired
    private IPurchaseTransactionService service;

    @KafkaListener(
            topics = "${custom.kafka.topic-name-bootcoin}",
            groupId = "${custom.kafka.group-id}",
            containerFactory = "purchaseKafkaListenerContainerFactory")

    public void consumer(PurchaseTransactionDto purchaseTransactionDto) {
        try {
            System.out.println("Consumer init");
            log.info("Consumer [{}]", purchaseTransactionDto);
            System.out.println("Consumer enviado");
            PurchaseTransaction mt = service.getPurchase(purchaseTransactionDto);
            service.create(mt).subscribe(c -> log.info(mt.toString()));
            log.info("Debe guardar en bd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
