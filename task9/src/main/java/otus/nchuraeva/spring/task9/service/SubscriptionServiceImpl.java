package otus.nchuraeva.spring.task9.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import otus.nchuraeva.spring.task9.document.Subscription;
import otus.nchuraeva.spring.task9.document.User;
import otus.nchuraeva.spring.task9.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String ADMIN = "admin";

    private final UserRepository userRepository;

    @Override
    public List<Subscription> findAllSubscriptions() {
        User user = userRepository.findUserByLogin(ADMIN);
        return user.getSubscriptions();
    }

    @Override
    public void updateSubscription(String userId, Subscription subscription) {
        Query query = Query.query(Criteria.where("id").is(userId).and("subscriptions.id").is(subscription.getId()));
        Update update = new Update();
        update.set("subscriptions.$.price", subscription.getPrice());
        update.set("subscriptions.$.service.name", subscription.getService().getName());
        update.set("subscriptions.$.service.link", subscription.getService().getLink());
        update.set("subscriptions.$.service.category.name", subscription.getService().getCategory().getName());

        userRepository.updateSubscription(query, update);
    }

    @Override
    public void deleteSubscription(String userId, Subscription subscription) {
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update().pull("subscriptions", subscription);

        userRepository.deleteSubscription(query, update);

    }

    @Override
    public void addSubscription(String userId, Subscription subscription) {
        subscription.setId(ObjectId.get().toString());

        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update().push("subscriptions", subscription);

        userRepository.addSubscription(query, update);
    }

    @Override
    public Subscription findUserAndSubscription(String userId, String subscriptionId) {
        Criteria findUserCriteria = Criteria.where("_id").is(userId);
        Criteria findSubscriptionCriteria = Criteria.where("subscriptions").elemMatch(Criteria.where("_id").is(subscriptionId));
        BasicQuery query = new BasicQuery(findUserCriteria.getCriteriaObject(), findSubscriptionCriteria.getCriteriaObject());
        User user = userRepository.findUserByIdAndSubscriptionId(query);

        if(user != null && !user.getSubscriptions().isEmpty()) {
                return user.getSubscriptions().get(0);
        }

        return null;
    }
}
