package com.bootcoin.transaction.configWebClient.bootcoinClient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BootcoinClient {

    private String id;
    private Double  tasaCompra;
    private Double tasaVenta;
    private String tipoMoneda;
    private LocalDate createDate;
}
