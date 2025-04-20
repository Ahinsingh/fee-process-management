package com.feeprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeeProcessApplication {

    public static void main(String... args) {
        SpringApplication.run(FeeProcessApplication.class, args);
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}