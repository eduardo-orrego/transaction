package com.nttdata.transaction.api.request;

import com.nttdata.transaction.enums.CardTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: CardRequest. <br/>
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
public class CardRequest {

  @NotNull(message = "El campo 'type' no puede ser nulo")
  private CardTypeEnum type;

  @NotNull(message = "El campo 'number' no puede ser vacío")
  private BigInteger number;

}
