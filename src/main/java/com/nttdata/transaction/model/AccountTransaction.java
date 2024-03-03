package com.nttdata.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.transaction.model.enums.AccountTransactionTypeEnum;
import com.nttdata.transaction.model.enums.AccountTypeEnum;
import com.nttdata.transaction.model.enums.CurrencyTypeEnum;
import com.nttdata.transaction.model.enums.HolderTypeEnum;
import com.nttdata.transaction.model.enums.SignerTypeEnum;
import com.nttdata.transaction.model.enums.StatusTypeEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account_transaction")
public class AccountTransaction {

    @Id
    private String id;

    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @JsonProperty(value = "accountType")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @JsonProperty(value = "accountNumber")
    private String accountNumber;

    @JsonProperty(value = "currency")
    private CurrencyTypeEnum currency;

    @JsonProperty(value = "transactionType")
    @Enumerated(EnumType.STRING)
    private AccountTransactionTypeEnum transactionType;

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "transactionDate")
    private LocalDateTime transactionDate;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;
}

