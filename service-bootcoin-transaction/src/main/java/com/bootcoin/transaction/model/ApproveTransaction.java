package com.bootcoin.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("ApproveTransactions")
public class ApproveTransaction {

    @Id
    private String id;
    private Integer numberTransaction;
    private String idPurchaseTransaction;
    private PurchaseTransaction.Status status;
    private LocalDate createDate;
}
