package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.TransactionCreditTypeEnum;
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

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private TransactionCreditTypeEnum type;

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El campo 'accountNumber' no puede ser nulo")
    private BigInteger accountNumber;

}
