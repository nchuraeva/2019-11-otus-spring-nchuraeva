package otus.nchuraeva.spring.task14.batch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "otus.nchuraeva.spring.task14.nosql.repository")
public class MongoDbCustomRepositoryConfiguration {
}