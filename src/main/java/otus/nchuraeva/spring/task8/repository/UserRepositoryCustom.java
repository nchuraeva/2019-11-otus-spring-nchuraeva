package otus.nchuraeva.spring.task8.repository;

import otus.nchuraeva.spring.task8.document.Subscription;
import otus.nchuraeva.spring.task8.document.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<Subscription> getSubscriptions(String id);

    void updateSubscription(String id, Subscription subscription);

    void setSubscription(String id, Subscription subscription);

    void deleteSubscription(String userId, Subscription subscription);

    User findUserByIdAndSubscriptionId(String userId, String subscriptionId);
}
