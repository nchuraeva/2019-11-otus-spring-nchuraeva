package otus.nchuraeva.spring.task11.repository;

import otus.nchuraeva.spring.task11.document.Subscription;
import reactor.core.publisher.Mono;


public interface UserRepositoryCustom {

    Mono<Void> updateSubscription(Subscription subscription);

    Mono<Void> addSubscription(String userId, Subscription subscription);

    Mono<Void> deleteSubscription(String userId, Subscription subscription);

    Mono<Subscription> findUserByIdAndSubscriptionId(String userId, String subscriptionId);
}
