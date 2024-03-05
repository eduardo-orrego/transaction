package com.nttdata.movement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.movement.model.enums.AccountMovementTypeEnum;
import com.nttdata.movement.model.enums.AccountTypeEnum;
import com.nttdata.movement.model.enums.CurrencyTypeEnum;
import com.nttdata.movement.model.enums.StatusTypeEnum;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account_transaction")
public class AccountMovement {

    @Id
    private String id;

    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @JsonProperty(value = "accountType")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @JsonProperty(value = "accountNumber")
    private BigInteger accountNumber;

    @JsonProperty(value = "transactionType")
    @Enumerated(EnumType.STRING)
    private AccountMovementTypeEnum transactionType;

    @JsonProperty(value = "currency")
    private CurrencyTypeEnum currency;

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "availableBalance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "transactionDate")
    private LocalDateTime transactionDate;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;
}

