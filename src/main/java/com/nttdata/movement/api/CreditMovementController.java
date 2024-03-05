package com.nttdata.movement.api;

import com.nttdata.movement.business.CreditMovementService;
import com.nttdata.movement.model.CreditMovement;
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
public class CreditMovementController {

    private final CreditMovementService creditMovementService;

    @Autowired
    public CreditMovementController(CreditMovementService creditMovementService) {
        this.creditMovementService = creditMovementService;
    }

    /**
     * GET /credits/{creditNumber} : Get movements about a specific credit
     *
     * @param creditNumber (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "movementsCreditNumberGet",
        summary = "Get movements about a specific credit",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreditMovement.class))
            })
        }
    )
    @GetMapping(
        value = "/credits/{creditNumber}",
        produces = {"application/json"}
    )
    public Flux<CreditMovement> movementsCreditNumberGet(
        @Parameter(name = "creditNumber", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("creditNumber") String creditNumber
    ) {
        return creditMovementService.getCreditMovements(creditNumber);
    }

    /**
     * POST /credits : Create a new credit movement
     *
     * @param creditMovement (optional)
     * @return Created (status code 201)
     */
    @Operation(
        operationId = "movementsCreditPost",
        summary = "Create a new credit movement",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreditMovement.class))
            })
        }
    )
    @PostMapping(
        value = "/credits",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public Mono<CreditMovement> movementsCreditPost(
        @Parameter(name = "creditMovement", description = "")
        @Validated @RequestBody(required = false) CreditMovement creditMovement
    ) {
        return creditMovementService.saveCreditMovement(creditMovement);
    }
}
