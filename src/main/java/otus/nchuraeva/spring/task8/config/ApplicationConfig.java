package otus.nchuraeva.spring.task8.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import otus.nchuraeva.spring.task8.shell.ShellHelper;


@Configuration
public class ApplicationConfig {

    private static final String CHANGE_LOGS_PACKAGE = "otus.nchuraeva.spring.task8.changelogs";

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGE_LOGS_PACKAGE)
                .build();
    }

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }
}

