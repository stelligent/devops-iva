package com.singlestoneconsulting.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import com.singlestoneconsulting.Application;

@Configuration
@ComponentScan(basePackageClasses = Application.class,
        excludeFilters = @Filter({ Controller.class, Configuration.class }))
class ApplicationConfig {
}
