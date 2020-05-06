package otus.nchuraeva.spring.task8.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import otus.nchuraeva.spring.task8.annotation.MongoTest;
import otus.nchuraeva.spring.task8.repository.SessionRepository;
import otus.nchuraeva.spring.task8.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@MongoTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryWithListenerTest {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;


    @DisplayName("при удалении пользователя должна удаляться сессия")
    @Test
    void shouldRemoveSessionWhenRemoveUser() {
        val sessions = sessionRepository.findAll();
        val session = sessions.get(0);
        val user = session.getUser();
        userRepository.delete(session.getUser());

        val expectedExperienceArrayLength = sessions.size() - 1;
        val actualSessions = sessionRepository.findAll();
        assertThat(actualSessions.size()).isEqualTo(expectedExperienceArrayLength);

        val actualExperienceArrayLength = sessionRepository.findByUserId(user.getId());
        assertThat(actualExperienceArrayLength.size()).isEqualTo(expectedExperienceArrayLength);
    }
}
