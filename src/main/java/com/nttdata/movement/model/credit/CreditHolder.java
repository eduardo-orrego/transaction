package com.nttdata.movement.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditHolder {

    @JsonProperty(value = "holderId")
    private String holderId;

    @JsonProperty(value = "holderType")
    private String holderType;

}
