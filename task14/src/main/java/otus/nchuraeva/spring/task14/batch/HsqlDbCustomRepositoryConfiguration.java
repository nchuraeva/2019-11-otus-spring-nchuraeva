package otus.nchuraeva.spring.task14.batch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"otus.nchuraeva.spring.task14.sql.repository"}
)
public class HsqlDbCustomRepositoryConfiguration {
}