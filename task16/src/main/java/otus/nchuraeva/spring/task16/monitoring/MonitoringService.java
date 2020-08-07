package otus.nchuraeva.spring.task16.monitoring;

public interface MonitoringService {

    void countAll(String className, String methodName);

    void countPositive(String className, String methodName);

    void countError(String className, String methodName);
}
