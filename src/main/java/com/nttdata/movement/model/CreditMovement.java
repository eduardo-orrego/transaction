package com.nttdata.movement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.movement.model.enums.CreditMovementTypeEnum;
import com.nttdata.movement.model.enums.CreditTypeEnum;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "credit_movement")
public class CreditMovement {

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

    @JsonProperty(value = "transactionType")
    @Enumerated(EnumType.STRING)
    private CreditMovementTypeEnum transactionType;

    @JsonProperty(value = "currency")
    private CurrencyTypeEnum currency;

    @JsonProperty(value = "outstandingBalance")
    private BigDecimal outstandingBalance;

    @JsonProperty(value = "creditCardLimit")
    private BigDecimal creditLimit;

    @JsonProperty(value = "creditCardAvailableBalance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "transactionDate")
    private LocalDateTime transactionDate;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;

}