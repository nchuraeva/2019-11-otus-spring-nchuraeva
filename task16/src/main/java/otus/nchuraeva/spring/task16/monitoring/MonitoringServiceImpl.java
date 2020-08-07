package otus.nchuraeva.spring.task16.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MonitoringServiceImpl implements MonitoringService {


    @Override
    public void countAll(String className, String methodName) {
        getCounter("ALL", className, methodName).increment();
    }

    @Override
    public void countPositive(String className, String methodName) {
        getCounter("POSITIVE", className, methodName).increment();
    }

    @Override
    public void countError(String className, String methodName) {
        getCounter("NEGATIVE", className, methodName).increment();
    }

    private Counter getCounter(String type, String className, String methodName) {
        return Metrics.globalRegistry.counter("catalog_monitoring_events_counter", "TYPE", type, "CLASS", className, "METHOD", methodName);
    }
}
