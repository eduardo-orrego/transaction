package com.nttdata.movement.api;

import com.nttdata.movement.business.AccountMovementService;
import com.nttdata.movement.model.AccountMovement;
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
@RequestMapping(value = "/api/movements")
public class AccountMovementController {

    private final AccountMovementService accountMovementService;

    @Autowired
    public AccountMovementController(AccountMovementService accountMovementService) {
        this.accountMovementService = accountMovementService;
    }

    /**
     * GET /accounts/{accountNumber} : Get movements about a specific account
     *
     * @param accountNumber (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "movementsAccountNumberGet",
        summary = "Get movements about a specific account",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AccountMovement.class))
            })
        }
    )
    @GetMapping(
        value = "/accounts/{accountNumber}",
        produces = {"application/json"}
    )
    public Flux<AccountMovement> movementsAccountNumberGet(
        @Parameter(name = "accountNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("accountNumber") String accountNumber
    ) {
        return accountMovementService.getAccountMovements(accountNumber);
    }

    /**
     * POST /accounts : Create a new account movement
     *
     * @param accountMovement (optional)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "movementsAccountPost",
        summary = "Create a new account movement",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AccountMovement.class))
            })
        }
    )
    @PostMapping(
        value = "/accounts",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<AccountMovement> movementsAccountPost(
        @Parameter(name = "accountMovement", description = "")
        @Validated @RequestBody AccountMovement accountMovement
    ) {
        return accountMovementService.saveAccountMovement(accountMovement);
    }

}
