package otus.nchuraeva.spring.task11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.nchuraeva.spring.task11.dto.UserCreateRequest;
import otus.nchuraeva.spring.task11.dto.UserResponse;
import otus.nchuraeva.spring.task11.document.User;
import otus.nchuraeva.spring.task11.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.body(BodyExtractors.toMono(UserCreateRequest.class))
                .flatMap(req -> userRepository.save(buildUser(req))
                        .flatMap(user -> buildJsonServerResponse(buildUserResponse(user)))
                        .onErrorResume(err -> buildErrorServerResponse("Internal error server", 500)));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
      return userRepository.findAll()
              .map(this::buildUserResponse)
              .collect(Collectors.toList())
              .flatMap(this::buildJsonServerResponse);
    }

    private User buildUser(UserCreateRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        return user;
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setLogin(user.getLogin());
        return response;
    }

    private Mono<ServerResponse> buildJsonServerResponse(Object response) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response));
    }

    private Mono<ServerResponse> buildErrorServerResponse(String message, int status) {
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponse(message)));
    }
}
