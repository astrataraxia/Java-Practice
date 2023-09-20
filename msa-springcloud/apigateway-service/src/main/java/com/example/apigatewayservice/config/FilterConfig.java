package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


//@Configuration
public class FilterConfig {

//    applictaion.yml에 있는 cloud 관련 설정을 java로 가져왔다.
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/first-service/**")
                        .filters(filter -> filter
                                .addRequestHeader("first-request", "first-request-header")
                                .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081"))
                .route(route -> route
                        .path("/second-service/**")
                        .filters(filter -> filter
                                .addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))

                .build();
    }
}
