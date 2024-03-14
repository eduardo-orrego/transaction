package com.nttdata.transaction.model;

import java.math.BigDecimal;
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
public class Credit {

    private String id;
    private String type;
    private String status;
    private String customerId;
    private BigInteger creditNumber;
    private LocalDate disbursementDate;
    private BigDecimal amount;
    private BigDecimal outstandingBalance;
    private String currency;
    private BigDecimal interestRate;
    private LocalDate dueDate;
    private LocalDateTime lastTransactionDate;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
