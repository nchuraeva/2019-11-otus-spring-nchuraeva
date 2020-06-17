package otus.nchuraeva.spring.task11.dto;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String login;
    private String email;
    private String password;
}
