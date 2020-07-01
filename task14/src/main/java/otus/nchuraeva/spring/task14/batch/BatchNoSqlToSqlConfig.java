package otus.nchuraeva.spring.task14.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.nchuraeva.spring.task14.nosql.document.Subscription;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsSubscription;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BatchNoSqlToSqlConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final EntityManager entityManager;

    private <T> MongoItemReader<T> getReader(Class<T> targetType) {
        MongoItemReader<T> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.DESC);
        }});
        reader.setTargetType(targetType);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public MongoItemReader<Subscription> subscriptionReader() {
        return getReader(Subscription.class);
    }

    private <T> JpaItemWriter<T> getWriter(Class<T> clazz) {
        JpaItemWriter<T> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return writer;
    }

    @Bean
    public JpaItemWriter<RdbmsSubscription> subscriptionWriter() {
        return getWriter(RdbmsSubscription.class);
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Subscription, RdbmsSubscription>chunk(3)
                .reader(subscriptionReader())
                .processor(new SubscriptionItemProcessor())
                .writer(subscriptionWriter())
                .build();
    }

    @Bean
    public Job noSqlToSqlMigration(JobCompletionNotificationListener listener, Step step, JobRegistry jobRegistry) throws DuplicateJobException {
        Job job = jobBuilderFactory.get("noSqlToSqlMigration")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
        ReferenceJobFactory referenceJobFactory = new ReferenceJobFactory(job);
        jobRegistry.register(referenceJobFactory);

        return job;
    }
}
