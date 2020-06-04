package otus.nchuraeva.spring.task10.service;

import otus.nchuraeva.spring.task10.document.User;

public interface UserService {
    User getUser(String login);
}
