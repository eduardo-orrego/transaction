package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.DocumentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotNull(message = "El campo 'typeDocument' no puede ser nulo")
    private DocumentTypeEnum typeDocument;

    @NotBlank(message = "El campo 'documentNumber' no puede ser vacío")
    private String documentNumber;

}
