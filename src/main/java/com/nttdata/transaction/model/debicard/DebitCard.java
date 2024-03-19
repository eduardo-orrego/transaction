package com.nttdata.transaction.model.debicard;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "debitCard")
public class DebitCard {

    @Id
    private String id;
    private String status;
    private BigInteger customerDocument;
    private List<AccountAssociated> accountsAssociated;
    private BigInteger cardNumber;
    private Integer cvv;
    private LocalDate expirationDate;
    private LocalDate activateDate;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
