package com.michal_mm.ois.orderservice.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    /**
     * Method used to create instance of RestClient object.
     * Currently, provides a very simplified implementation,
     * however, in the future can be extended to use settings
     * from the properties file(s)
     * @return RestClient instance
     */
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
