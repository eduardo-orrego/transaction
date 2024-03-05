package com.nttdata.movement.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "accountNumber")
    private BigInteger accountNumber;

    @JsonProperty(value = "openingDate")
    private LocalDateTime openingDate;

    @JsonProperty(value = "availableBalance")
    private BigDecimal availableBalance;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "accountHolders")
    private List<AccountHolder> accountHolders;

    @JsonProperty(value = "authorizedSigners")
    private List<AuthorizedSigner> authorizedSigners;

    @JsonProperty(value = "savingsInfo")
    private SavingsInfo savingsInfo;

    @JsonProperty(value = "termDepositInfo")
    private TermDepositInfo termDepositInfo;

    @JsonProperty(value = "checkingInfo")
    private CheckingInfo checkingInfo;

    @JsonProperty(value = "dateCreated")
    private LocalDateTime dateCreated;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;
}