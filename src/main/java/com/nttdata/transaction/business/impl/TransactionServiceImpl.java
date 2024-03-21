package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.Transaction;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionServiceImpl. <br/>
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
@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  public Mono<Transaction> findByTransactionNumber(BigInteger transactionNumber) {
    return transactionRepository.findTransaction(transactionNumber)
      .switchIfEmpty(Mono.defer(() ->
        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Transaction not found - transactionNumber: ".concat(transactionNumber.toString()))))
      );
  }

  @Override
  public Flux<Transaction> findByAccountNumberSource(BigInteger accountNumberSource) {
    return transactionRepository.findTransactions(accountNumberSource)
      .switchIfEmpty(Mono.defer(() ->
        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Transaction not found - accountNumberSource: ".concat(accountNumberSource.toString()))))
      );
  }

  @Override
  public Flux<Transaction> findByCustomerDocument(BigInteger customerDocument) {
    return transactionRepository.findTransactionsByCustomerDocument(customerDocument)
      .switchIfEmpty(Mono.defer(() ->
        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Transaction not found - customerDocument: ".concat(customerDocument.toString()))))
      );
  }


}
