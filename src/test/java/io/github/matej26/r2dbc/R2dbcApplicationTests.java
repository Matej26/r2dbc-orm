package io.github.matej26.r2dbc;

import io.github.matej26.r2dbc.model.Student;
import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryExecutor;
import io.github.matej26.r2dbc.query.SortingQuery;
import io.github.matej26.r2dbc.query.util.SortingDirection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class R2dbcApplicationTests {
	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:9.6.12");

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
				+ postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
				+ "/" + postgreSQLContainer.getDatabaseName());
		registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
		registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
	}

	@Autowired
	QueryExecutor executor;

	@Test
	void shouldReturnAllStudents() {
		executor.findAll(Student.class)
				.as(StepVerifier::create)
				.expectNextCount(6)
				.verifyComplete();
	}

	@Test
	void shouldReturnStudentById() {
		executor.find(Query.where().equal("id", 1), Student.class)
				.as(StepVerifier::create)
				.consumeNextWith(student -> assertEquals("matej", student.getName()))
				.expectNextCount(0)
				.verifyComplete();
	}

	@Test
	void shouldReturnNothingByNotExistingValue() {
		executor.find(Query.where().equal("id", 9), Student.class)
				.as(StepVerifier::create)
				.expectNextCount(0)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByParam() {
		executor.findAll(Query.where().equal("name", "nick"), Student.class)
				.as(StepVerifier::create)
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByMultipleParamsThroughOr() {
		executor.findAll(
						Query.where().equal("id", 1).or().equal("name", "misha"),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void shouldReturnStudentByMultipleParamsThroughAnd() {
		executor.find(
						Query.where().equal("name", "nick").and().equal("address", "spb"),
						Student.class
				)
				.as(StepVerifier::create)
				.consumeNextWith(student -> {
					assertEquals("nick", student.getName());
					assertEquals("spb", student.getAddress());
				})
				.expectNextCount(0)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByNotEqual() {
		executor.findAll(
						Query.where().notEqual("id", 1),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(5)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByLessThan() {
		executor.findAll(
						Query.where().lessThan("id", 4),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByGreaterThan() {
		executor.findAll(
						Query.where().greaterThan("id", 3),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByLessThanOrEqualTo() {
		executor.findAll(
						Query.where().lessThanOrEqualTo("id", 4),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(4)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByGreaterThanOrEqualTo() {
		executor.findAll(
						Query.where().greaterThanOrEqualTo("id", 3),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(4)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByIsNull() {
		executor.findAll(
						Query.where().isNull("id"),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(0)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByIsNotNull() {
		executor.findAll(
						Query.where().isNotNull("id"),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(6)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByLike() {
		executor.findAll(
						Query.where().like("name", "n%"),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByNotLike() {
		executor.findAll(
						Query.where().notLike("name", "n%"),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(4)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByInCollection() {
		executor.findAll(
						Query.where().in("id", List.of(1, 2, 3)),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByInVarargs() {
		executor.findAll(
						Query.where().in("id", 1, 2, 3),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByNotInCollection() {
		executor.findAll(
						Query.where().notIn("id", List.of(1, 2, 3)),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByNotInVarargs() {
		executor.findAll(
						Query.where().notIn("id", 1, 2, 3),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByLimit() {
		executor.findAll(
						SortingQuery.sortAndPage("id", 3),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsByOffset() {
		executor.findAll(
						SortingQuery.sortAndPage("id", 3L),
						Student.class
				)
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnAllStudentsBySort() {
		executor.findAll(
						SortingQuery.sort("id", SortingDirection.DESC),
						Student.class
				)
				.as(StepVerifier::create)
				.consumeNextWith(student -> {
					assertEquals("liza", student.getName());
					assertEquals("spb", student.getAddress());
				})
				.expectNextCount(5)
				.verifyComplete();
	}
}
