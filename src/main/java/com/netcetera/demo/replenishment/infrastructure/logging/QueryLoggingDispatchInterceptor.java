package com.netcetera.demo.replenishment.infrastructure.logging;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.queryhandling.QueryMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
@Component
public class QueryLoggingDispatchInterceptor implements MessageDispatchInterceptor<QueryMessage<?, ?>> {

    @Override
    public BiFunction<Integer, QueryMessage<?, ?>, QueryMessage<?, ?>> handle(List<? extends QueryMessage<?, ?>> list) {
        return (index, query) -> {
            log.info("Publishing query: [{}].", query);
            return query;
        };
    }
}
