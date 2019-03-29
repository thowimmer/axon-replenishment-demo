package com.netcetera.demo.replenishment.infrastructure.web.reporting;

import com.netcetera.demo.replenishment.api.query.ReplenishmentPerTokenResponse;
import com.netcetera.demo.replenishment.api.query.ReplenishmentsPerTokenQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/reporting")
public class ReportingController {

    private final QueryGateway queryGateway;

    @Autowired
    public ReportingController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/token/{tokenId}/replenishments",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public GetTokenReplenishmentsResponse getTokenReplenishments(@PathVariable("tokenId") String tokenId) throws ExecutionException, InterruptedException {

        ReplenishmentsPerTokenQuery query = new ReplenishmentsPerTokenQuery(tokenId);

        ReplenishmentPerTokenResponse response = queryGateway.query(query, ReplenishmentPerTokenResponse.class).get();

        return new GetTokenReplenishmentsResponse(response.getReplenishmentAmount());
    }
}
