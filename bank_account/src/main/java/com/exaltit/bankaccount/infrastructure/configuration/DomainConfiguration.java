package com.exaltit.bankaccount.infrastructure.configuration;

import com.exaltit.bankaccount.domain.DomainService;
import com.exaltit.bankaccount.domain.Stub;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "com.exaltit.bankaccount.domain",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        DomainService.class}),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        Stub.class}))
public class DomainConfiguration {
}
