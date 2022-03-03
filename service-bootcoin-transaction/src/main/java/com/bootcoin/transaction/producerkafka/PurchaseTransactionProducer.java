package com.bootcoin.transaction.producerkafka;

import com.bootcoin.transaction.model.dto.PurchaseTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseTransactionProducer {
    @Value("${custom.kafka.topic-name-transaction}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, PurchaseTransactionDto> transactionDtoKafkaTemplate;

    public void producer(PurchaseTransactionDto transactionDto) {
        transactionDtoKafkaTemplate.send(topicName, transactionDto);
    }
}
