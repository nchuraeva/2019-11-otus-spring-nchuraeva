package otus.nchuraeva.spring.task11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.nchuraeva.spring.task11.document.Subscription;
import otus.nchuraeva.spring.task11.document.User;
import otus.nchuraeva.spring.task11.repository.UserRepository;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findById(request.queryParam("userId").get())
                .flatMapIterable(User::getSubscriptions), Subscription.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.body(BodyExtractors.toMono(Subscription.class))
                .flatMap(subscription -> userRepository.addSubscription(subscription.getUserId(), subscription))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.body(BodyExtractors.toMono(Subscription.class))
                .flatMap(updateSubscription -> userRepository.updateSubscription(updateSubscription))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String subscriptionId = request.pathVariable("id");
        String userId = request.queryParam("userId").get();

        return userRepository.findUserByIdAndSubscriptionId(userId, subscriptionId)
                        .flatMap(subscription -> userRepository.deleteSubscription(subscription.getUserId(), subscription))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(err -> buildErrorServerResponse("Subscription not found", 404));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String subscriptionId = request.pathVariable("id");
        String userId = request.queryParam("userId").get();

        return userRepository.findUserByIdAndSubscriptionId(userId, subscriptionId)
                                .flatMap(subscription -> buildJsonServerResponse(subscription))
                                .onErrorResume(err -> buildErrorServerResponse("Subscription not found", 404));
    }

    private Mono<ServerResponse> buildJsonServerResponse(Object response) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response));
    }

    private Mono<ServerResponse> buildErrorServerResponse(String message, int status) {
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponse(message)));
    }
}
