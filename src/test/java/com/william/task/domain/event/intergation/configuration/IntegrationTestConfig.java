package com.william.task.domain.event.intergation.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration(proxyBeanMethods = false)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.william.task"})
public class IntegrationTestConfig {

}
