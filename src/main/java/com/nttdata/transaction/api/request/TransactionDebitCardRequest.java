package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.TransactionDebitCardTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDebitCardRequest {

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private TransactionDebitCardTypeEnum type;

    @NotNull(message = "El campo 'amount' no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El campo 'cardNumber' no puede ser vacío")
    private BigInteger cardNumber;

}
