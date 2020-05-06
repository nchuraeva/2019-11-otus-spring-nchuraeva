package otus.nchuraeva.spring.task8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.nchuraeva.spring.task8.document.Session;
import otus.nchuraeva.spring.task8.document.User;
import otus.nchuraeva.spring.task8.repository.SessionRepository;
import otus.nchuraeva.spring.task8.repository.UserRepository;

import java.time.LocalDateTime;

@ShellComponent
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionCommands {

    private final ShellHelper shellHelper;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @ShellMethod(value = "create session", key = "create session")
    public void create(@ShellOption("--login") String login, @ShellOption("--ip") String ip, @ShellOption("--userAgent") String userAgent) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            shellHelper.printError(String.format("User %s", login) + " is not exist!");
            return;
        }
        Session session = Session.builder()
                .createAt(LocalDateTime.now())
                .user(user)
                .ip(ip)
                .userAgent(userAgent)
                .build();

        sessionRepository.save(session);
        shellHelper.printSuccess("Session is created!");
    }
}
