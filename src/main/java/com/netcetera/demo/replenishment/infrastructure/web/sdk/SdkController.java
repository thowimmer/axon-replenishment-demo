package com.netcetera.demo.replenishment.infrastructure.web.sdk;

import com.netcetera.demo.replenishment.api.command.ConfirmKeyDeliveryCommand;
import com.netcetera.demo.replenishment.api.command.StartReplenishmentCommand;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfo;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfoQuery;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfoResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/sdk")
public class SdkController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public SdkController(CommandGateway commandGateway, QueryGateway queryGateway){
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/replenishment/start",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public StartReplenishmentResponse startReplenishmentRequest(@RequestBody @Valid StartReplenishmentRequest request){

        StartReplenishmentCommand command = new StartReplenishmentCommand(request.getTokenId());

        String replenishmentId = commandGateway.sendAndWait(command);

        return new StartReplenishmentResponse(replenishmentId);
    }

    @RequestMapping(value = "/replenishment/{replenishmentId}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public GetReplenishmentInfoResponse getReplenishmentInfo(@PathVariable("replenishmentId") String replenishmentId) throws ExecutionException, InterruptedException {

        ReplenishmentInfoQuery query = new ReplenishmentInfoQuery(replenishmentId);

        ReplenishmentInfoResponse response = queryGateway.query(query, ReplenishmentInfoResponse.class).get();

        ReplenishmentInfo replenishmentInfo = response.getInfo();
        return new GetReplenishmentInfoResponse(replenishmentInfo.getStatus(), replenishmentInfo.getReplenishedKeys());
    }

    @RequestMapping(value = "/replenishment/{replenishmentId}/confirm",
            method = POST)
    public void confirmKeyDelivery(@PathVariable("replenishmentId") String replenishmentId) {
        ConfirmKeyDeliveryCommand command = new ConfirmKeyDeliveryCommand(replenishmentId);

        commandGateway.sendAndWait(command);
    }
}
