package io.github.matej26.r2dbc.controller;

import io.github.matej26.r2dbc.service.StudentService;
import io.github.matej26.r2dbc.model.Student;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public Mono<Student> findOne(@PathVariable Long id) {
        return studentService.findOne(id);
    }

    @GetMapping
    public Flux<Student> findAll() {
        return studentService.findAll();
    }

    @PostMapping
    public Mono<Student> create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Mono<Student> update(@RequestBody Student student, @PathVariable Long id) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public Mono<Integer> delete(@PathVariable Long id) {
        return studentService.delete(id);
    }
}
