package com.netcetera.demo.replenishment.infrastructure.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class AxonBeanLogger {

    private ApplicationContext applicationContext;

    @Autowired
    public AxonBeanLogger(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @EventListener
    public void onApplicationStarted(ContextRefreshedEvent event) {
        log.info("------------------Axon Spring Boot Starter Beans------------------");
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(beanDefinitionName -> applicationContext.getBean(beanDefinitionName))
                .filter(bean -> bean.getClass().toString().contains("org.axonframework"))
                .forEach(axonBean -> log.info(axonBean.getClass().toString()));
        log.info("------------------------------------------------------------------");
    }
}
