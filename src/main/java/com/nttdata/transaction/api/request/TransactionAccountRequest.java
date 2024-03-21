package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.TransactionAccountTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: TransactionAccountRequest. <br/>
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
public class TransactionAccountRequest {

  @NotNull(message = "El campo 'type' no puede ser nulo")
  private TransactionAccountTypeEnum type;

  @NotNull(message = "El campo 'amount' no puede ser nulo")
  private BigDecimal amount;

  @NotNull(message = "El campo 'accountSource' no puede ser nulo")
  private BigInteger accountNumberSource;

}
