package com.netcetera.demo.replenishment;

import com.netcetera.demo.replenishment.infrastructure.logging.CommandLoggingDispatchInterceptor;
import com.netcetera.demo.replenishment.infrastructure.logging.EventLoggingDispatchInterceptor;
import com.netcetera.demo.replenishment.infrastructure.logging.QueryLoggingDispatchInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    @Bean
    public EventStorageEngine storageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Autowired
    public void registerCommandInterceptor(CommandBus commandBus, CommandLoggingDispatchInterceptor interceptor) {
        commandBus.registerDispatchInterceptor(interceptor);
    }

    @Autowired
    public void registerEventInterceptor(EventBus eventBus, EventLoggingDispatchInterceptor interceptor) {
        eventBus.registerDispatchInterceptor(interceptor);
    }

    @Autowired
    public void registerQueryInterceptor(QueryBus queryBus, QueryLoggingDispatchInterceptor interceptor) {
        queryBus.registerDispatchInterceptor(interceptor);
    }
}
