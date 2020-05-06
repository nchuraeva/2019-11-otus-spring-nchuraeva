package otus.nchuraeva.spring.task8.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.nchuraeva.spring.task8.document.User;
import otus.nchuraeva.spring.task8.repository.SessionRepository;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCascadeDeleteEventListener extends AbstractMongoEventListener<User> {

    private final SessionRepository sessionRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<User> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        sessionRepository.removeSessionsByUserId(id);
    }
}
