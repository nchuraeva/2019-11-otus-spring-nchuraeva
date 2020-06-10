package otus.nchuraeva.spring.task11.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RestRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userRouterHandler,
                                                SubscriptionHandler subscriptionRouterHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/users")
                .and(RequestPredicates.accept(APPLICATION_JSON)), userRouterHandler::create)
                .andRoute(RequestPredicates.GET("/api/users")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), userRouterHandler::findAll)
                .andRoute(RequestPredicates.GET("/api/subscriptions")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), subscriptionRouterHandler::findAll)
                .andRoute(RequestPredicates.POST("/api/subscriptions")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), subscriptionRouterHandler::create)
                .andRoute(RequestPredicates.PUT("/api/subscriptions")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), subscriptionRouterHandler::update)
                .andRoute(RequestPredicates.DELETE("/api/subscriptions/{id}")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), subscriptionRouterHandler::delete)
                .andRoute(RequestPredicates.GET("/api/subscriptions/{id}")
                        .and(RequestPredicates.accept(APPLICATION_JSON)), subscriptionRouterHandler::findById);
    }
}
