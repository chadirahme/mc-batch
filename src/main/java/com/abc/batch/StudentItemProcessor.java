package com.abc.batch;

import com.abc.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public Student process(final Student student) throws Exception {
        final String name = student.getName().toUpperCase();
        final String passportNumber = student.getPassportNumber().toUpperCase();

        final Student transformedPerson = new Student(name, passportNumber);

        log.info("Converting (" + student + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}