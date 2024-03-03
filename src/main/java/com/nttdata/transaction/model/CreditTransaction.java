package com.nttdata.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.transaction.model.enums.AccountTransactionTypeEnum;
import com.nttdata.transaction.model.enums.CreditTransactionTypeEnum;
import com.nttdata.transaction.model.enums.CreditTypeEnum;
import com.nttdata.transaction.model.enums.CurrencyTypeEnum;
import com.nttdata.transaction.model.enums.StatusTypeEnum;
import jakarta.validation.constraints.NotNull;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "credit_movement")
public class CreditTransaction {

    @Id
    private String id;

    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @JsonProperty(value = "creditType")
    @Enumerated(EnumType.STRING)
    private CreditTypeEnum creditType;

    @JsonProperty(value = "creditNumber")
    private BigInteger creditNumber;

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