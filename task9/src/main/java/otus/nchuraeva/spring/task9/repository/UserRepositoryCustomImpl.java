package otus.nchuraeva.spring.task9.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import otus.nchuraeva.spring.task9.document.User;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateSubscription(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void addSubscription(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void deleteSubscription(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, User.class);
    }

    @Override
    public User findUserByIdAndSubscriptionId(Query query) {
        List<User> users = mongoTemplate.find(query, User.class);
        if (users != null && !CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }
}
