package mv.orm.r2dbc.service;

import mv.orm.r2dbc.model.Student;
import mv.orm.r2dbc.repository.StudentRepositoryImpl;
import mv.orm.r2dbc.util.QueryBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
    private final StudentRepositoryImpl studentRepository;

    public StudentService(StudentRepositoryImpl studentRepository) {
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
