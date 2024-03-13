package com.leduyminh.metricsserver.config;

import com.leduyminh.metricsserver.utils.MetricServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

    @Autowired
    Environment environment;

    @Bean
    MetricServiceUtils metricService() {
        return new MetricServiceUtils(environment);
    }
}
