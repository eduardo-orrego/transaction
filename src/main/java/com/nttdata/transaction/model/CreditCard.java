package com.nttdata.transaction.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    private String id;
    private BigInteger cardNumber;
    private String status;
    private LocalDate expirationDate;
    private LocalDate activateDate;
    private Integer cvv;
    private BigInteger customerDocument;
    private BigInteger creditNumber;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
