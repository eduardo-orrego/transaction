package com.nttdata.transaction.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAccount {

    private String id;
    private BigInteger number;
    private String productType;
    private TransactionCustomer transactionCustomer;
    private TransactionCard transactionCard;
    private BigDecimal balance;

}
