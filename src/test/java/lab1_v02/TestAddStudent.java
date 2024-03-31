package lab1_v02;

import domain.Student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestAddStudent {
    public static Service service;

    @BeforeAll
    public static void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        TestAddStudent.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudent_ValidData_CreatedSuccessfully() {
        String idStudent = "test";
        String numeStudent = "john";
        int grupa = 934;
        String email = "john_doe@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assertFalse(true);
        }

        assert(service.findStudent(idStudent) != null);
    }

    @Test
    public void addStudent_EmptyId_ThrowError() {
        String idStudent = "";
        String numeStudent = "john";
        int grupa = 934;
        String email = "john_doe@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
            assert(false);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

    @Test
    public void testAddStudentDuplicate(){
        Student newStudent1 = new Student("1", "Ana", 931, "ana@gmail.com");

        Student stud1 = this.service.addStudent(newStudent1);

        Student stud2 = this.service.addStudent(newStudent1);
        assertEquals(newStudent1.getID(), stud2.getID());

        service.deleteStudent("1");
    }

    @Test
    public void testAddStudentEmptyName(){
        Student newStudent2 = new Student("2", "", 931, "ana@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));

    }

//    @Test
//    public void addStudent_NullId_ThrowError() {
//        String idStudent = null;
//        String numeStudent = "john";
//        int grupa = 934;
//        String email = "john_doe@yahoo.com";
//        Student student = new Student(idStudent, numeStudent, grupa, email);
//
//        try {
//            service.addStudent(student);
//            assert(true);
//        } catch (ValidationException exception) {
//            System.out.println(exception);
//            assert(true);
//        }
//    }
//
//    @Test
//    public void addStudent_DuplicateId_ThrowError() {
//        String idStudent = "test";
//        String numeStudent = "john";
//        int grupa = 934;
//        String email = "john_doe@yahoo.com";
//        Student student = new Student(idStudent, numeStudent, grupa, email);
//
//        try {
//            Student result = service.addStudent(student);
//            assert(result == student);
//        } catch (ValidationException exception) {
//            System.out.println(exception);
//            assert(true);
//        }
//    }



}