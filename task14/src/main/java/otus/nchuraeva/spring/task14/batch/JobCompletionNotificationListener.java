package otus.nchuraeva.spring.task14.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import otus.nchuraeva.spring.task14.nosql.repository.SubscriptionRepository;
import otus.nchuraeva.spring.task14.nosql.repository.UserRepository;
import otus.nchuraeva.spring.task14.sql.repository.RdbmsSubscriptionRepository;
import otus.nchuraeva.spring.task14.sql.repository.RdbmsUserRepository;
import otus.nchuraeva.spring.task14.sql.repository.RdmsCategoryRepository;
import otus.nchuraeva.spring.task14.sql.repository.RdmsServiceRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final RdmsCategoryRepository categoryRepository;
    private final RdbmsSubscriptionRepository rdbmsSubscriptionRepository;
    private final RdmsServiceRepository rdmsServiceRepository;
    private final RdbmsUserRepository rdbmsUserRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Before job.");
        checkList("Users", userRepository.findAll());
        checkList("Subscriptions", subscriptionRepository.findAll());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished!");
            checkList("Users", rdbmsUserRepository.findAll());
            checkList("Subscriptions", rdbmsSubscriptionRepository.findAll());
            checkList("Services", rdmsServiceRepository.findAll());
            checkList("Categories", categoryRepository.findAll());
        }
    }

    private <T> void checkList(String tableName, List<T> list) {
        log.info("Number of rows in table " + tableName + ": " + list.size());
        list.forEach(System.out::println);
    }
}
