package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.CurrencyTypeEnum;
import com.nttdata.transaction.enums.StatusTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: TransactionRequest. <br/>
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
public class TransactionRequest {

  private BigInteger number;

  @NotNull(message = "El campo 'type' no puede ser nulo")
  private TransactionTypeEnum type;

  @NotNull(message = "El campo 'status' no puede ser nulo")
  private StatusTypeEnum status;

  @NotNull(message = "El campo 'amount' no puede ser nulo")
  private BigDecimal amount;

  @NotNull(message = "El campo 'currency' no puede ser nulo")
  private CurrencyTypeEnum currency;

  @NotNull(message = "El campo 'customerDocument' no puede ser nulo")
  private BigInteger customerDocument;

  @NotNull(message = "El campo 'accountSource' no puede ser nulo")
  private BigInteger accountNumberSource;

  @Valid
  private CardRequest card;

  private BigDecimal commission;

  private BigInteger accountNumberTarget;

}

