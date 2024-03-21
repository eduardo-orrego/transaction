package com.nttdata.transaction.model.debicard;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class: DebitCard. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
@Getter
@Setter
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
