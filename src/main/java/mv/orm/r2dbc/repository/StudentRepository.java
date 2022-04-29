package mv.orm.r2dbc.repository;

import mv.orm.r2dbc.model.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Integer, Student> {

    @Query(value = "select * from student where age < ?1")
    Optional<Student> findByAgeLessThan(int age);
}
