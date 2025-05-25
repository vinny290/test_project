package com.example.test_service.repository;

import com.example.test_service.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

}
