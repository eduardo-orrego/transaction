package com.nttdata.transaction.api;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.TransactionEntity;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * GET /{transactionNumber} : Get transaction about a specific transaction number
     *
     * @param transactionNumber (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "transactionNumberGet",
        summary = "Get transaction about a specific transaction number",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionEntity.class))
            })
        }
    )
    @GetMapping(
        value = "/{transactionNumber}",
        produces = {"application/json"}
    )
    public Mono<TransactionEntity> transactionNumberGet(
        @Parameter(name = "transactionNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("transactionNumber") BigInteger transactionNumber
    ) {
        return transactionService.findByTransactionNumber(transactionNumber);
    }

    /**
     * GET : Get a list of transactions for the customer
     *
     * @param customerId (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "transactionsCustomerGet",
        summary = "Get a list of transactions for the customer",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TransactionEntity.class)))
            })
        }
    )
    @GetMapping(
        value = "",
        produces = {"application/json"}
    )
    public Flux<TransactionEntity> transactionsCustomerGet(
        @NotNull @Parameter(name = "customerId", description = "", required = true, in = ParameterIn.QUERY)
        @Validated @RequestParam(value = "customerId") String customerId
    ) {
        return transactionService.findByCustomerId(customerId);
    }


    /**
     * POST  : Create a new transaction
     *
     * @param transaction (required)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "transactionSavePost",
        summary = "Create a new transaction",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionEntity.class))
            })
        }
    )
    @PostMapping(
        value = "",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<TransactionEntity> transactionSavePost(
        @Parameter(name = "TransactionRequest", description = "")
        @Validated @RequestBody TransactionRequest transaction
    ) {
        return transactionService.saveTransaction(transaction);
    }

    /**
     * PUT /{transactionNumber} : Update a transaction exists
     *
     * @param transactionId (required)
     * @param transaction (required)
     * @return Ok (status code 200)
     */
    @Operation(
        operationId = "transactionUpdatePut",
        summary = "Update a transaction exists",
        responses = {
            @ApiResponse(responseCode = "200", description = "Updated", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionEntity.class))
            })
        }
    )
    @PutMapping(
        value = "/{transactionId}",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<TransactionEntity> transactionUpdatePut(
        @Parameter(name = "transactionId", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("transactionId") String transactionId,
        @Parameter(name = "TransactionRequest", description = "")
        @Validated @RequestBody TransactionRequest transaction
    ) {
        return transactionService.updateTransaction(transaction, transactionId);
    }

}
