package otus.nchuraeva.spring.task10.service;

import otus.nchuraeva.spring.task10.document.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> findAllSubscriptions();

    void updateSubscription(String userId, String subscriptionId, Subscription subscription);

    void deleteSubscription(String userId, Subscription subscription);

    void addSubscription(String userId,Subscription subscription);

    Subscription findUserAndSubscription(String userId, String id);
}
