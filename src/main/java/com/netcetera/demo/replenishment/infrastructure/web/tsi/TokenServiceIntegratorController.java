package com.netcetera.demo.replenishment.infrastructure.web.tsi;

import com.netcetera.demo.replenishment.api.command.CreateTokenCommand;
import com.netcetera.demo.replenishment.api.command.FinishReplenishmentCommand;
import com.netcetera.demo.replenishment.api.command.SupplyKeysCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/tsi")
public class TokenServiceIntegratorController {

    private final CommandGateway commandGateway;

    @Autowired
    public TokenServiceIntegratorController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "/token",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreateTokenResponse createToken(@RequestBody @Valid CreateTokenRequest request){

        CreateTokenCommand command = new CreateTokenCommand(request.getTokenStatus());

        String tokenId = commandGateway.sendAndWait(command);

        return new CreateTokenResponse(tokenId);
    }

    @RequestMapping(value = "/replenishment/{replenishmentId}/keys",
            method = POST,
            consumes = APPLICATION_JSON_VALUE)
    public void supplyKeys(@PathVariable("replenishmentId") String replenishmentId, @RequestBody @Valid SupplyKeysRequest request){

        SupplyKeysCommand command = new SupplyKeysCommand(replenishmentId, request.getKeys());

        commandGateway.sendAndWait(command);
    }

    @RequestMapping(value = "/replenishment/{replenishmentId}/finish",
            method = POST)
    public void finishReplenishment(@PathVariable("replenishmentId") String replenishmentId){

        FinishReplenishmentCommand command = new FinishReplenishmentCommand(replenishmentId);

        commandGateway.sendAndWait(command);
    }
}
