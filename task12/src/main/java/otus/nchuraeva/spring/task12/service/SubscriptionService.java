package otus.nchuraeva.spring.task12.service;

import otus.nchuraeva.spring.task12.document.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> findAllSubscriptions(String userId);

    void updateSubscription(String subscriptionId, Subscription updateSubscription);

    void deleteSubscription(String subscriptionId);

    void addSubscription(Subscription subscription);

    Subscription findSubscriptionById(String subscriptionId);
}
