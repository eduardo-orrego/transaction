package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.CurrencyTypeEnum;
import com.nttdata.transaction.enums.StatusTypeEnum;
import com.nttdata.transaction.enums.TransactionAccountTypeEnum;
import com.nttdata.transaction.enums.TransactionCreditTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreditRequest {

    private BigInteger number;

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private TransactionCreditTypeEnum type;

    @NotNull(message = "El campo 'status' no puede ser nulo")
    private StatusTypeEnum status;

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El campo 'currency' no puede ser nulo")
    private CurrencyTypeEnum currency;

    @NotNull(message = "El campo 'customerDocument' no puede ser nulo")
    private BigInteger customerDocument;

    @NotNull(message = "El campo 'accountSource' no puede ser nulo")
    private BigInteger accountNumberSource;

    @Valid
    private CardRequest card;

    private BigDecimal commission;

    private BigInteger accountNumberTarget;

}
