package otus.nchuraeva.spring.task9.repository;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import otus.nchuraeva.spring.task9.document.Subscription;
import otus.nchuraeva.spring.task9.document.User;


public interface UserRepositoryCustom {

    void updateSubscription(Query query, Update update);

    void addSubscription(Query query, Update update);

    void deleteSubscription(Query query, Update update);

    User findUserByIdAndSubscriptionId(Query query);
}
