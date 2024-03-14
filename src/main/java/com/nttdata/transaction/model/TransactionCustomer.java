package com.nttdata.transaction.model;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCustomer {
    private String id;
    private String type;
    private String subType;
    private String typeDocument;
    private BigInteger documentNumber;
    private String name;
}
