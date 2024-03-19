package com.nttdata.transaction.api.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTransferRequest {

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El campo 'accountSource' no puede ser nulo")
    private BigInteger accountNumberSource;

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigInteger accountNumberTarget;

}
