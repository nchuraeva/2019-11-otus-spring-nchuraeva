package otus.nchuraeva.spring.task14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobServiceImpl implements JobService {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    @Override
    public void start(String jobName) {
        try {
            Job job = jobRegistry.getJob(jobName);
            jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
