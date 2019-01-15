package UI;

import Domain.ProfesoriEntity;
import Repository.*;
import Service.*;
import UI.Console;
import Utils.PasswordSecurity;
import Utils.Time;
import Validation.NotaValidator;
import Validation.ProfessorValidator;
import Validation.StudentValidator;
import Validation.TemaValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int ok = 0;
        while(ok == 0) {
//            String parola = "parola";
//            String encrypt = PasswordSecurity.encrypt(parola);
//            System.out.println(encrypt);
//            String dec = PasswordSecurity.decrypt(encrypt);
//            System.out.println(dec);

            System.out.println("Choose your repository ");
            System.out.println("1. In Memory");
            System.out.println("2. In File");
            System.out.println("3. In XML");
            System.out.println("4. DB Repo");

            Scanner scanner = new Scanner(System.in);
            int op;
            System.out.print("Choose your option: ");
            op = scanner.nextInt();
            if (op == 1) {
//                ok = 1;
//                StudentCrudRepository studentCrudRepository = new StudentCrudRepository();
//                StudentValidator studentValidator = new StudentValidator();
//                StudentService studentService = new StudentService(studentCrudRepository, studentValidator);
//
//                TemaCrudRepository temaCrudRepository = new TemaCrudRepository();
//                TemaValidator temaValidator = new TemaValidator();
//                TemaService temaService = new TemaService(temaCrudRepository, temaValidator);
//
//                InFileNotaRepository inFileNotaRepository = new InFileNotaRepository("resources/catalog.txt");
//                NotaValidator notaValidator = new NotaValidator();
//                NotaService notaService = new NotaService(inFileNotaRepository, notaValidator, studentService, temaService);
//
//                Console console = new Console(studentService, temaService, notaService);
//                console.showConsole();
            } else if (op == 2) {
//                ok = 1;
//                InFileStudentRepository inFileStudentRepository = new InFileStudentRepository("resources/students.txt");
//                StudentValidator studentValidator = new StudentValidator();
//                StudentService studentService = new StudentService(inFileStudentRepository, studentValidator);
//
//                InFileTemaRepository inFileTemaRepository = new InFileTemaRepository("resources/teme.txt");
//                TemaValidator temaValidator = new TemaValidator();
//                TemaService temaService = new TemaService(inFileTemaRepository, temaValidator);
//
//                InFileNotaRepository inFileNotaRepository = new InFileNotaRepository("resources/catalog.txt");
//                NotaValidator notaValidator = new NotaValidator();
//                NotaService notaService = new NotaService(inFileNotaRepository, notaValidator, studentService, temaService);
//
//                Console console = new Console(studentService, temaService, notaService);
//                console.showConsole();
            } else if(op == 3) {
//                ok = 1;
//                XMLStudentRepository xmlStudentRepository = new XMLStudentRepository("resources/students.xml");
//                StudentValidator studentValidator = new StudentValidator();
//                StudentService studentService = new StudentService(xmlStudentRepository, studentValidator);
//
//                XMLTemaRepository xmlTemaRepository = new XMLTemaRepository("resources/teme.xml");
//                TemaValidator temaValidator = new TemaValidator();
//                TemaService temaService = new TemaService(xmlTemaRepository, temaValidator);
//
//                XMLNotaRepository xmlNotaRepository = new XMLNotaRepository("resources/catalog.xml");
//                NotaValidator notaValidator = new NotaValidator();
//                NotaService notaService = new NotaService(xmlNotaRepository, notaValidator, studentService, temaService);
//
//                Console console = new Console(studentService, temaService, notaService);
//                console.showConsole();
            }else  if(op == 4) {
                ok = 1;
                StudentDBRepository dbRepository = new StudentDBRepository();
                StudentValidator studentValidator = new StudentValidator();
                StudentService studentService = new StudentService(dbRepository, studentValidator);

                TemaDBRepository temaDBRepository = new TemaDBRepository();
                TemaValidator temaValidator = new TemaValidator();
                TemaService temaService = new TemaService(temaDBRepository, temaValidator);

                NotaDBRepository notaDBRepository = new NotaDBRepository();
                NotaValidator notaValidator = new NotaValidator();
                NotaService notaService = new NotaService(notaDBRepository, notaValidator, studentService, temaService);

                ProfesorDBRepository profesorDBRepository = new ProfesorDBRepository();
                ProfessorValidator professorValidator = new ProfessorValidator();
                ProfesorService profesorService = new ProfesorService(profesorDBRepository, professorValidator);


                AccountDBRepository accountDBRepository = new AccountDBRepository();
                AccountService accountService = new AccountService(accountDBRepository);
                accountService.addAccount("admin", "admin","a",1);

                Console console = new Console(studentService, temaService, notaService);
                console.showConsole();
            }

        }
    }
}
