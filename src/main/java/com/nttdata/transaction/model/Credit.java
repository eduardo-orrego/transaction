package com.nttdata.transaction.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: Credit. <br/>
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
public class Credit {

  private String id;
  private String type;
  private String status;
  private String currency;
  private BigDecimal amount;
  private LocalDate disbursementDate;
  private BigInteger customerDocument;
  private LocalDate dueDate;

  private BigInteger creditNumber;
  private BigDecimal outstandingBalance;
  private BigDecimal interestRate;
  private BigDecimal availableBalance;
  private BigDecimal creditLimit;

  private LocalDateTime lastTransactionDate;
  private LocalDateTime dateCreated;
  private LocalDateTime lastUpdated;

}
