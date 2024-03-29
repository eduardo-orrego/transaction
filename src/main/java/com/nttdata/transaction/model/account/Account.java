package com.nttdata.transaction.model.account;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: Account. <br/>
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
public class Account {

  private String id;
  private BigInteger accountNumber;
  private String type;
  private String status;
  private LocalDate openingDate;
  private BigDecimal openingAmount;
  private String currency;
  private BigDecimal interestRate;
  private BigDecimal maintenanceCommission;
  private Integer monthlyLimitMovement;
  private Integer limitFreeMovements;
  private BigDecimal commissionMovement;
  private Integer specificDayMonthMovement;
  private BigDecimal availableBalance;
  private List<AccountHolder> accountHolders;
  private List<AuthorizedSigner> authorizedSigners;
  private LocalDateTime lastTransactionDate;
  private LocalDateTime dateCreated;
  private LocalDateTime lastUpdated;
}