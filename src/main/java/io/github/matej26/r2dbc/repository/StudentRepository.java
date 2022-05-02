package io.github.matej26.r2dbc.repository;

import io.github.matej26.r2dbc.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository {
    Mono<Student> findById(Long id);

    Flux<Student> findAll();

    Mono<Student> save(Student student);

    Mono<Student> update(Student student);

    Mono<Integer> deleteById(Long id);
}
