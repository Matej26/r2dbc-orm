package io.github.matej26.r2dbc.repository;

import io.github.matej26.r2dbc.model.Student;
import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final QueryExecutor executor;

    public StudentRepositoryImpl(QueryExecutor executor) {
        this.executor = executor;
    }

    @Override
    public Mono<Student> findById(Long id) {
        return executor.find(Query.where().equal("id", id), Student.class);
    }

    @Override
    public Flux<Student> findAll() {
        return executor.findAll(Student.class);
    }

    @Override
    public Mono<Student> save(Student student) {
        return executor.save(student);
    }

    @Override
    public Mono<Student> update(Student student) {
        return executor.update(student);
    }

    @Override
    public Mono<Integer> deleteById(Long id) {
        return executor.delete(Query.where().equal("id", id), Student.class);
    }
}
