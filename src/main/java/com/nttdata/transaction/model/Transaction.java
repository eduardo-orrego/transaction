package com.nttdata.transaction.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class: Transaction. <br/>
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
@Document(collection = "transaction")
public class Transaction {

  @Id
  private String id;
  private BigInteger number;
  private String status;
  private String transactionType;
  private BigDecimal amount;
  private String currency;
  private BigInteger customerDocument;
  private String cardType;
  private BigInteger cardNumber;
  private BigInteger accountNumberSource;
  private BigInteger accountNumberTarget;
  private BigDecimal commission;
  private LocalDateTime dateCreated;
  private LocalDateTime lastUpdated;

}