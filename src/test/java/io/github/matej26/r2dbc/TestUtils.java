package io.github.matej26.r2dbc;

import io.github.matej26.r2dbc.model.Student;

public class TestUtils {
    static Student getValidStudentForSave() {
        Student student = new Student();
        student.setName("test_name");
        student.setAddress("test_address");
        return student;
    }

    static Student getInvalidStudentForSave() {
        Student student = new Student();
        student.setName("test_name");
        student.setAddress(null);
        return student;
    }

    static Student getValidStudentForUpdate() {
        return new Student(1L, "matej", "spb");
    }

    static Student getInvalidStudentForUpdate() {
        return new Student(1L, null, "spb");
    }

    static Student getNotExistingStudent() {
        return new Student(Long.MAX_VALUE, "test_name", "test_address");
    }
}
