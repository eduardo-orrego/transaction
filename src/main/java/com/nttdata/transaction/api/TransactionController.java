package com.nttdata.transaction.api;

import com.nttdata.transaction.api.request.TransactionAccountRequest;
import com.nttdata.transaction.api.request.TransactionCreditCardRequest;
import com.nttdata.transaction.api.request.TransactionCreditRequest;
import com.nttdata.transaction.api.request.TransactionDebitCardRequest;
import com.nttdata.transaction.api.request.TransactionTransferRequest;
import com.nttdata.transaction.business.TransactionCreditService;
import com.nttdata.transaction.business.TransactionDebitService;
import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionController. <br/>
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
@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionDebitService transactionDebitService;

  @Autowired
  private TransactionCreditService transactionCreditService;

  /**
   * POST  : Create a new Account transaction.
   *
   * @param transaction (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "transactionAccountSavePost",
    summary = "Create a new account transaction",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @PostMapping(
    value = "/accounts",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  public Mono<Transaction> transactionAccountSavePost(
    @Parameter(name = "transaction", description = "")
    @Validated @RequestBody TransactionAccountRequest transaction
  ) {
    return transactionDebitService.saveAccountTransaction(transaction);
  }

  /**
   * POST  : Create a new credit transaction.
   *
   * @param transaction (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "transactionCreditSavePost",
    summary = "Create a new credit transaction",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @PostMapping(
    value = "/debitCards",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  public Mono<Transaction> transactionDebitCardsSavePost(
    @Parameter(name = "transaction", description = "")
    @Validated @RequestBody TransactionDebitCardRequest transaction
  ) {
    return transactionDebitService.saveDebitCardTransaction(transaction);
  }

  /**
   * POST  : Create a new credit transaction.
   *
   * @param transaction (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "transactionCreditSavePost",
    summary = "Create a new credit transaction",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @PostMapping(
    value = "/credits",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  public Mono<Transaction> transactionCreditSavePost(
    @Parameter(name = "transaction", description = "")
    @Validated @RequestBody TransactionCreditRequest transaction
  ) {
    return transactionCreditService.saveCreditTransaction(transaction);
  }

  /**
   * POST  : Create a new credit card transaction.
   *
   * @param transaction (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "transactionCreditCardSavePost",
    summary = "Create a new credit card transaction",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @PostMapping(
    value = "/creditCards",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  public Mono<Transaction> transactionCreditCardSavePost(
    @Parameter(name = "transaction", description = "")
    @Validated @RequestBody TransactionCreditCardRequest transaction
  ) {
    return transactionCreditService.saveCreditCardTransaction(transaction);
  }

  /**
   * POST  : Create a new transfer transaction.
   *
   * @param transaction (required)
   * @return Created (status code 201)
   */
  @Operation(
    operationId = "transactionTransferSavePost",
    summary = "Create a new Transfer transaction",
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @PostMapping(
    value = "/transfers",
    produces = {"application/json"},
    consumes = {"application/json"}
  )
  public Mono<Transaction> transactionTransferSavePost(
    @Parameter(name = "transaction", description = "")
    @Validated @RequestBody TransactionTransferRequest transaction
  ) {
    return transactionDebitService.saveTransferTransaction(transaction);
  }


  /**
   * GET /{transactionNumber} : Get transaction about a specific transaction number.
   *
   * @param transactionNumber (required)
   * @return OK (status code 200)
   */
  @Operation(
    operationId = "transactionNumberGet",
    summary = "Get transaction about a specific transaction number",
    responses = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = "application/json",
          schema = @Schema(implementation = Transaction.class))
      })
    }
  )
  @GetMapping(
    value = "/{transactionNumber}",
    produces = {"application/json"}
  )
  public Mono<Transaction> transactionNumberGet(
    @Parameter(name = "transactionNumber", description = "", required = true,
      in = ParameterIn.PATH)
    @PathVariable("transactionNumber") BigInteger transactionNumber
  ) {
    return transactionService.findByTransactionNumber(transactionNumber);
  }

  /**
   * GET : Get a list of transactions for the customer
   *
   * @param documentCustomer (required)
   * @return OK (status code 200)
   */
  @Operation(
    operationId = "transactionsCustomerGet",
    summary = "Get a list of transactions for the customer",
    responses = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))
      })
    }
  )
  @GetMapping(
    value = "",
    produces = {"application/json"}
  )
  public Flux<Transaction> transactionsCustomerGet(
    @NotNull @Parameter(name = "documentCustomer", description = "", required = true,
      in = ParameterIn.QUERY)
    @Validated @RequestParam(value = "documentCustomer") BigInteger documentCustomer
  ) {
    return transactionService.findByCustomerDocument(documentCustomer);
  }


}
