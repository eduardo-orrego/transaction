package com.nttdata.transaction.enums;

import lombok.Getter;

/**
 * Class: TransactionTypeEnum. <br/>
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
public enum TransactionTypeEnum {
  PURCHASE,
  PAYMENT,
  DEPOSIT,
  WITHDRAWAL,
  WIRE_TRANSFER,
  MAINTENANCE_CHARGE
}
