package otus.nchuraeva.spring.task8.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import otus.nchuraeva.spring.task8.document.Session;
import org.springframework.data.mongodb.core.query.Query;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionRepositoryCustomImpl implements SessionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeSessionsByUserId(String id) {
        val query = Query.query(Criteria.where("user.$id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Session.class);
    }
}
