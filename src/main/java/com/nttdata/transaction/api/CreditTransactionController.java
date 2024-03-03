package com.nttdata.transaction.api;

import com.nttdata.transaction.business.CreditTransactionService;
import com.nttdata.transaction.model.CreditTransaction;
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
@RequestMapping(value = "/api/credits")
public class CreditTransactionController {

    private final CreditTransactionService creditTransactionService;

    @Autowired
    public CreditTransactionController(CreditTransactionService creditTransactionService) {
        this.creditTransactionService = creditTransactionService;
    }

    /**
     * GET /{creditNumber}/movements : Get movements about a specific credit
     *
     * @param creditNumber (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "creditsMovementsCreditNumberGet",
        summary = "Get movements about a specific credit",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreditTransaction.class))
            })
        }
    )
    @GetMapping(
        value = "/{creditNumber}/movements",
        produces = {"application/json"}
    )
    public Flux<CreditTransaction> creditsMovementsCreditNumberGet(
        @Parameter(name = "creditNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("creditNumber") String creditNumber
    ) {
        return creditTransactionService.getCreditTransaction(creditNumber);
    }

    /**
     * POST /movements : Create a new credit movement
     *
     * @param creditTransaction (optional)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "creditsPost",
        summary = "Create a new credit movement",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreditTransaction.class))
            })
        }
    )
    @PostMapping(
        value = "/movements",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<CreditTransaction> creditsPost(
        @Parameter(name = "creditTransaction", description = "")
        @Validated @RequestBody(required = false) CreditTransaction creditTransaction
    ) {
        return creditTransactionService.saveCreditTransaction(creditTransaction);
    }
}
