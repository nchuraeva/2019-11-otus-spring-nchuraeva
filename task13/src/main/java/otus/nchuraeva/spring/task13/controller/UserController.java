package otus.nchuraeva.spring.task13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import otus.nchuraeva.spring.task13.document.User;
import otus.nchuraeva.spring.task13.dto.UserRequest;
import otus.nchuraeva.spring.task13.service.UserService;

import java.util.Set;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(ModelMap model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(ModelMap model) {
        model.addAttribute("request", new UserRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String register(UserRequest userRequest) {
        if(userService.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("User existed with username: " + userRequest.getUsername());
        }

        if(userService.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("User existed with email: " + userRequest.getEmail());
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        userService.save(user);

        return "redirect:/login";
    }
}
