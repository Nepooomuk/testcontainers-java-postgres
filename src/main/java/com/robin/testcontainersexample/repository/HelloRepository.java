package com.robin.testcontainersexample.repository;

import com.robin.testcontainersexample.model.Hello;
import org.springframework.data.repository.CrudRepository;

public interface HelloRepository extends CrudRepository<Hello, Long> {
}
