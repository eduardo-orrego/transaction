package com.nttdata.movement.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedSigner {
    @JsonProperty(value = "signerId")
    private String signerId;

    @JsonProperty(value = "signerType")
    private String signerType;
}
