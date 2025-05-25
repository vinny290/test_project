package com.example.test_service.service;

import com.example.test_service.metrics.TestMetricsService;
import com.example.test_service.model.Test;
import com.example.test_service.repository.TestRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final TestEventPublisher testEventPublisher;
    private final TestMetricsService metricsService;

    public TestService(TestRepository testRepository,
                       TestEventPublisher testEventPublisher,
                       TestMetricsService metricsService) {
        this.testRepository = testRepository;
        this.testEventPublisher = testEventPublisher;
        this.metricsService = metricsService;
    }

    @Cacheable("tests")
    public List<Test> getAllTests() {
        System.out.println(">>> Fetching tests from database...");
        return testRepository.findAll();
    }

    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }

    @CacheEvict(value = "tests", allEntries = true)
    public Test createTest(Test test) {
        return metricsService.getTestCreationTimer().record(() -> {
            Test savedTest = testRepository.save(test);
            testEventPublisher.sendTestCreatedEvent(savedTest);
            metricsService.incrementCreatedTests();
            metricsService.incrementActiveTests();
            return savedTest;
        });
    }

    @CacheEvict(value = "tests", allEntries = true)
    public Optional<Test> updateTest(Long id, Test updatedTest) {
        return testRepository.findById(id).map(existing -> {
            existing.setTitle(updatedTest.getTitle());
            existing.setDescription(updatedTest.getDescription());
            existing.setStatus(updatedTest.getStatus());
            return testRepository.save(existing);
        });
    }

    @CacheEvict(value = "tests", allEntries = true)
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
        metricsService.decrementActiveTests();
    }
}
