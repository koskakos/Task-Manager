package com.task.manager.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CachingConfig {

    public static final String TASKS = "TASKS";
}