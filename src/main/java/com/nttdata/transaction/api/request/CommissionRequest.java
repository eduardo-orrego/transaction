package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.AccountFeeTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionRequest {

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private AccountFeeTypeEnum type;

    @NotNull(message = "El campo 'amount' no puede ser vacío")
    private BigDecimal amount;

}