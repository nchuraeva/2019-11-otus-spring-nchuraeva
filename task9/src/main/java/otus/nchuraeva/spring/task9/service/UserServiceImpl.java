package otus.nchuraeva.spring.task9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.nchuraeva.spring.task9.document.User;
import otus.nchuraeva.spring.task9.repository.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String login) {
        return userRepository.findUserByLogin(login);
    }
}
