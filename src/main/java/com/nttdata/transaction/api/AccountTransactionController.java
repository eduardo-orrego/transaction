package com.nttdata.transaction.api;

import com.nttdata.transaction.business.AccountTransactionService;
import com.nttdata.transaction.model.AccountTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @Autowired
    public AccountTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }

    /**
     * GET /{accountNumber}/movements : Get movements about a specific account
     *
     * @param accountNumber (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "accountsMovementsAccountNumberGet",
        summary = "Get movements about a specific account",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))
            })
        }
    )
    @GetMapping(
        value = "/{accountNumber}/movements",
        produces = {"application/json"}
    )
    public Flux<AccountTransaction> accountsMovementsAccountNumberGet(
        @Parameter(name = "accountNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("accountNumber") String accountNumber
    ) {
        return accountTransactionService.getAccountTransaction(accountNumber);
    }

    /**
     * POST /movements : Create a new account movement
     *
     * @param accountMovement (optional)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "accountsPost",
        summary = "Create a new account movement",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))
            })
        }
    )
    @PostMapping(
        value = "/movements",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<AccountTransaction> accountsPost(
        @Parameter(name = "AccountTransaction", description = "")
        @Validated @RequestBody(required = false) AccountTransaction accountTransaction
    ) {
        return accountTransactionService.saveAccountTransaction(accountTransaction);
    }

}
