package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.CurrencyTypeEnum;
import com.nttdata.transaction.enums.StatusTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private BigInteger number;

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private TransactionTypeEnum type;

    @NotNull(message = "El campo 'status' no puede ser nulo")
    private StatusTypeEnum status;

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El campo 'currency' no puede ser nulo")
    private CurrencyTypeEnum currency;

    @NotBlank(message = "El campo 'customerId' no puede ser nulo")
    private String customerId;

    @NotNull(message = "El campo 'accountSource' no puede ser nulo")
    private BigInteger accountNumberSource;

    @Valid
    private CardRequest card;

    private BigInteger accountNumberTarget;

}

