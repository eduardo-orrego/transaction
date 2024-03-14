package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.CardTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    @NotNull(message = "El campo 'type' no puede ser nulo")
    private CardTypeEnum type;

    @NotNull(message = "El campo 'number' no puede ser vacío")
    private BigInteger number;

}
