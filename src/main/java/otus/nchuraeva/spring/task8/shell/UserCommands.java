package otus.nchuraeva.spring.task8.shell;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.nchuraeva.spring.task8.document.Category;
import otus.nchuraeva.spring.task8.document.Service;
import otus.nchuraeva.spring.task8.document.Subscription;
import otus.nchuraeva.spring.task8.document.User;
import otus.nchuraeva.spring.task8.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCommands {

    private final ShellHelper shellHelper;
    private final UserRepository userRepository;

    @ShellMethod(value = "add user and subscriptions", key = "create user")
    public void createUserSubscription(@ShellOption("--login") String login,
                                       @ShellOption("--email") String email,
                                       @ShellOption("--password") String password) {
        User user = User.builder()
                .login(login)
                .email(email)
                .password(password)
                .build();


        userRepository.save(user);
        shellHelper.printSuccess("User is created!");
    }


    @ShellMethod(value = "add user subscription", key = "add subscription")
    public void addUserSubscription(@ShellOption("--userId") String id, @ShellOption("--price") int price,
                                    @ShellOption("--service") String service, @ShellOption("--link") String link,
                                    @ShellOption("--category") String category) {
        Optional<User> user = userRepository.findById(id);
        if (user.get() == null) {
            shellHelper.printError(String.format("User %s", id) + " is not exist!");
        }

        List<Subscription> subscriptions = user.get().getSubscriptions();
        subscriptions.add(Subscription.builder()
                .id(ObjectId.get().toString())
                .startedAt(LocalDateTime.now())
                .notifyTime(LocalDateTime.of(2020, Month.JULY, 29, 19, 30))
                .price(new BigDecimal(price))
                .service(Service.builder()
                        .id(ObjectId.get().toString())
                        .name(service).
                                link(link).
                                category(Category.builder()
                                        .id(ObjectId.get().toString())
                                        .name(category)
                                        .build())
                        .build())
                .build()
        );

        userRepository.save(user.get());
        shellHelper.printSuccess("User subscription is added!");
    }


    @ShellMethod(value = "delete user subscription", key = "delete subscription")
    public void deleteUserSubscription(@ShellOption("--id") String id, @ShellOption("--subscriptionId") String subscriptionId) {
        Optional<User> user = userRepository.findById(id);
        if (user.get() == null) {
            shellHelper.printError(String.format("User %s", id) + " is not exist!");
        }

        Subscription subscription = userRepository.findUserByIdAndSubscriptionId(id, subscriptionId).getSubscriptions().get(0);
        if (subscription == null) {
            shellHelper.printError(String.format("Subscription %s", id) + " is not exist!");
        }

        List<Subscription> subscriptions = user.get().getSubscriptions();
        subscriptions.remove(subscription);
        userRepository.save(user.get());
        shellHelper.printSuccess("User subscriptions is deleted!");
    }

    @ShellMethod(value = "delete user", key = "delete user")
    public void deleteUser(@ShellOption("--id") String id) {
        userRepository.deleteById(id);
        shellHelper.printSuccess("User is deleted!");
    }

    @ShellMethod(value = "get user", key = "get user")
    public void getUser(@ShellOption("--login") String login) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            shellHelper.printError(String.format("User %s", login) + " is not exist!");
        }
        shellHelper.printSuccess(user.toString());
    }
}
