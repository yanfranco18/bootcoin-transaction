package com.bootcoin.transaction.model.dto;

import com.bootcoin.transaction.model.PurchaseTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=false) //permite comprobar si son del mismo tipo y coincide
@SuperBuilder //genera un costructor protegido de la clase, establece los campos de la nueva instancia
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTransactionDto {

    private String id;
    private String originNumber;
    private String destinationNumber;
    private Double amountSol;
    private String idBootcoinTasaCompra;
    private Double amountBootcoin;
    private String paymentMode;
    private PurchaseTransaction.Status status;
    private LocalDate createDate;

    public enum Status {
        PENDING,
        REJECTED,
        SUCCESSFUL
    }
}
