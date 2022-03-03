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
@Document("PurchaseTransactions")
public class PurchaseTransaction {

    @Id
    private String id;
    private String originNumber;
    private String destinationNumber;
    private Double amountSol;
    private String idBootcoinTasaCompra;
    private Double amountBootcoin;
    private String paymentMode;
    private Status status;
    private LocalDate createDate;

    public enum Status {
        PENDING,
        REJECTED,
        SUCCESSFUL
    }

}
