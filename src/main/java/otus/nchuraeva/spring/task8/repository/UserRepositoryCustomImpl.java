package otus.nchuraeva.spring.task8.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import otus.nchuraeva.spring.task8.document.Subscription;
import otus.nchuraeva.spring.task8.document.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Subscription> getSubscriptions(String id) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), User.class);
        return (user != null) ? user.getSubscriptions() : new ArrayList<>();
    }

    @Override
    public void updateSubscription(String id, Subscription subscription) {
        Query query = Query.query(Criteria.where("id").is(id).and("subscriptions.id").is(subscription.getId()));
        Update update = new Update();
        update.set("subscriptions.$.price", subscription.getPrice());
        update.set("subscriptions.$.notifyTime", subscription.getNotifyTime());

        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void setSubscription(String id, Subscription subscription) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().push("subscriptions", subscription), User.class);
    }

    @Override
    public void deleteSubscription(String userId, Subscription subscription) {
        Update update = new Update().pull("subscriptions", subscription);
        mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(userId)), update, User.class);
    }

    @Override
    public User findUserByIdAndSubscriptionId(String userId, String subscriptionId) {
        Criteria findUserCriteria = Criteria.where("_id").is(userId);
        Criteria findSubscriptionCriteria = Criteria.where("subscriptions").elemMatch(Criteria.where("_id").is(subscriptionId));
        BasicQuery query = new BasicQuery(findUserCriteria.getCriteriaObject(), findSubscriptionCriteria.getCriteriaObject());
        List<User> users = mongoTemplate.find(query, User.class);
        if (users != null && !CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }
}
