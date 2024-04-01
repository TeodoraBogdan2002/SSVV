package lab1_v02;

import domain.Student;

import domain.Tema;
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

//@RunWith(JUnit4.class)
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

    // White box testing
    @Test
    public void addTema_ValidData_CreateSuccessfully() {
        String nrTema = "100";
        String descriere = "test";
        int deadline = 12;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(true);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @Test
    public void addTema_Invalid_nrTema_duplicate_ThrowsError() {
        String nrTema = "100";
        String descriere = "test";
        int deadline = 12;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            Tema response = service.addTema(tema);
            assert(tema == response);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(true);
        }
    }

    @Test
    public void addTema_Invalid_nrTema_emptyString_ThrowsError() {
        String nrTema = "";
        String descriere = "test";
        int deadline = 12;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(true);
        }
    }

    @Test
    public void addTema_Invalid_descriere_emptyString_ThrowsError() {

        String nrTema = "101";
        String descriere = "";
        int deadline = 12;
        int primire = 2;

        Tema tema = new Tema(nrTema, descriere, deadline, primire );

        try{
            service.addTema(tema);
            assert(false);

        }catch (ValidationException ve){
            System.out.println("Validation Exception: " + ve.getMessage());
            assert(true);

        }

    }

    @Test
    public void addTema_Invalid_deadline_smallerThan1_ThrowsError() {

        String nrTema = "102";
        String descriere = "test";
        int deadline = 0;
        int primire = 11;

        Tema tema = new Tema(nrTema, descriere, deadline, primire );

        try{
            service.addTema(tema);
            assert(false);

        }catch (ValidationException ve){
            System.out.println("Validation Exception: " + ve.getMessage());
            assert(true);

        }

    }

    @Test
    public void addTema_Invalid_deadline_greaterThan14_ThrowsError() {

        String nrTema = "102";
        String descriere = "test";
        int deadline = 15;
        int primire = 11;

        Tema tema = new Tema(nrTema, descriere, deadline, primire );

        try{
            service.addTema(tema);
            assert(false);

        }catch (ValidationException ve){
            System.out.println("Validation Exception: " + ve.getMessage());
            assert(true);

        }

    }

    @Test
    public void addTema_Invalid_primire_smallerThan1_ThrowsError() {

        String nrTema = "103";
        String descriere = "test";
        int deadline = 12;
        int primire = 0;

        Tema tema = new Tema(nrTema, descriere, deadline, primire );

        try{
            service.addTema(tema);
            assert(false);

        }catch (ValidationException ve){
            System.out.println("Validation Exception: " + ve.getMessage());
            assert(true);

        }

    }

    @Test
    public void addTema_Invalid_primire_greaterThan14_ThrowsError() {

        String nrTema = "103";
        String descriere = "test";
        int deadline = 12;
        int primire = 15;

        Tema tema = new Tema(nrTema, descriere, deadline, primire );

        try{
            service.addTema(tema);
            assert(false);

        }catch (ValidationException ve){
            System.out.println("Validation Exception: " + ve.getMessage());
            assert(true);

        }

    }

    // Black box testing
    @Test
    public void addStudent_ValidData_CreatedSuccessfully() {
        String idStudent = "test";
        String numeStudent = "miguel";
        int grupa = 931;
        String email = "miguel@yahoo.com";
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
        String numeStudent = "miguel";
        int grupa = 931;
        String email = "miguel@yahoo.com";
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
    public void addStudent_DuplicateId_ThrowError() {
        String idStudent = "test";
        String numeStudent = "miguel";
        int grupa = 931;
        String email = "miguel@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);
        try {
            Student result = service.addStudent(student);
            assert(result == student);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

    @Test
    public void addStudent_EmptyName_ThrowError() {
        String idStudent = "test1";
        String numeStudent = "";
        int grupa = 931;
        String email = "miguel@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
            assert(false);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }


}