package com.nttdata.transaction.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private String id;
    private String type;
    private String status;
    private String currency;
    private BigDecimal amount;
    private LocalDate disbursementDate;
    private BigInteger customerDocument;
    private LocalDate dueDate;

    private BigInteger creditNumber;
    private BigDecimal outstandingBalance;
    private BigDecimal interestRate;
    private BigDecimal availableBalance;
    private BigDecimal creditLimit;

    private LocalDateTime lastTransactionDate;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
