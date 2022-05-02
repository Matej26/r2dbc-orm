package io.github.matej26.r2dbc.service;

import io.github.matej26.r2dbc.model.Student;
import io.github.matej26.r2dbc.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Mono<Student> findOne(Long id) {
        return studentRepository.findById(id);
    }

    public Flux<Student> findAll() {
        return studentRepository.findAll();
    }

    public Mono<Student> create(Student student) {
        return studentRepository.save(student);
    }

    public Mono<Student> update(Long id, Student student) {
        return studentRepository
                .findById(id)
                .flatMap(studentFromDb -> Mono.just(student)
                        .map(s -> {
                            studentFromDb.setName(s.getName());
                            studentFromDb.setAddress(s.getAddress());
                            return studentFromDb;
                }))
                .flatMap(studentRepository::update);
    }

    public Mono<Integer> delete(Long id) {
        return studentRepository.deleteById(id);
    }
}
