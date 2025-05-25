package com.example.test_service.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TestMetricsService {

    private final Counter testCreatedCounter;
    private final AtomicInteger activeTestCount;
    private final Gauge testActiveGauge;
    private final Timer testCreationTimer;

    public TestMetricsService(MeterRegistry meterRegistry) {
        this.testCreatedCounter = Counter.builder("test_total")
                .description("Total number of tests created")
                .tag("source", "api")
                .register(meterRegistry);

        this.activeTestCount = new AtomicInteger(0);
        this.testActiveGauge = Gauge.builder("test_active_count", activeTestCount, AtomicInteger::get)
                .description("Current number of active tests")
                .register(meterRegistry);

        this.testCreationTimer = Timer.builder("test_creation_duration")
                .description("Time taken to create a test")
                .tag("source", "api")
                .publishPercentileHistogram()
                .register(meterRegistry);
    }

    public void incrementCreatedTests() {
        testCreatedCounter.increment();
    }

    public void incrementActiveTests() {
        activeTestCount.incrementAndGet();
    }

    public void decrementActiveTests() {
        activeTestCount.decrementAndGet();
    }

    public Timer getTestCreationTimer() {
        return testCreationTimer;
    }
}
