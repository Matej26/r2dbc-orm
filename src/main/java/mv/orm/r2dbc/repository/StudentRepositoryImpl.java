package mv.orm.r2dbc.repository;

import mv.orm.r2dbc.model.Student;
import mv.orm.r2dbc.util.QueryBuilder;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.data.relational.core.query.Query.query;

@Repository
public class StudentRepositoryImpl {
    private final QueryBuilder<Student> builder;
    private final R2dbcEntityTemplate template;
    private StudentRepository studentRepository;

    public StudentRepositoryImpl(QueryBuilder<Student> builder, R2dbcEntityTemplate template) {
        this.builder = builder;
        this.template = template;
        Optional<Student> student = studentRepository.findByAgeLessThan(1);
    }

    public Mono<Student> findById(Long id) {
        return builder
                .where()
                .equal("id", id)
                .and()
                .equal("name", "studenft")
                .build(Student.class);
    }

    public Flux<Student> findAll() {
        return template
                .select(Student.class)
                .all();
    }

    public Mono<Student> save(Student student) {
        return template
                .insert(student);
    }

    public Mono<Student> update(Student student) {
        return template.update(student);
    }

    public Mono<Integer> deleteById(Long id) {
        return template
                .delete(Student.class)
                .matching(query(Criteria.where("id").is(id)))
                .all();
    }
}
