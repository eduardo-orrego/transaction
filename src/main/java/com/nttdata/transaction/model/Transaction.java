package com.nttdata.transaction.model;

import java.math.BigDecimal;
import java.math.BigInteger;
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
@Document(collection = "transaction")
public class Transaction {

    @Id
    private String id;
    private BigInteger number;
    private String status;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private TransactionAccount source;
    private TransactionAccount target;
    private BigDecimal maintenanceCommission;
    private BigDecimal commissionMovement;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}

