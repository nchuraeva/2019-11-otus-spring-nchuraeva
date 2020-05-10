package otus.nchuraeva.spring.task9.service;

import otus.nchuraeva.spring.task9.document.User;

public interface UserService {
    User getUser(String login);
}
