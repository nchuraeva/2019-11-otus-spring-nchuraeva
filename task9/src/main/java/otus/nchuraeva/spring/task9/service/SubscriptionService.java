package otus.nchuraeva.spring.task9.service;

import otus.nchuraeva.spring.task9.document.Subscription;
import otus.nchuraeva.spring.task9.document.User;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> findAllSubscriptions();

    void updateSubscription(String userId, Subscription subscription);

    void deleteSubscription(String userId, Subscription subscription);

    void addSubscription(String userId,Subscription subscription);

    Subscription findUserAndSubscription(String userId, String id);

}
