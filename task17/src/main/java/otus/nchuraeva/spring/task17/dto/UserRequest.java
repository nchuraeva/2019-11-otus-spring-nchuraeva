package otus.nchuraeva.spring.task17.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
