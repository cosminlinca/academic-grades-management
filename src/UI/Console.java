package UI;

import Domain.*;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import Validation.StudentPresentException;
import Validation.ValidationException;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

public class Console {

    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;

    public Console(StudentService studentService, TemaService temaService, NotaService notaService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
    }


    public void showConsole() {
        this.showOptions();
        this.options();
    }

    private void showOptions() {
        System.out.println("--------------------------------");
        System.out.println("1.  Show all students");
        System.out.println("2.  Show all tasks");
        System.out.println("3.  Add a student");
        System.out.println("4.  Delete a student");
        System.out.println("5.  Update a student");
        System.out.println("6.  Add a task");
        System.out.println("7.  Extend deadline with one week");
        System.out.println("8.  Mark a student as 'missing' ");
        System.out.println("9.  Add a grade");
        System.out.println("10. Show grades");
        System.out.println("0.  Show options again");
        System.out.println("-1: EXIT");
        System.out.println("---------------------------------");
    }

    private int options() {
        int op=1;
        Scanner sc = new Scanner(System.in);

        while (op != -1) {
            System.out.print("Choose an option: ");
            op = sc.nextInt();
            switch (op) {
                case 0: op = 0;
                        showOptions();
                        break;
                case 1: op = 1;
                        showStudents();
                        break;
                case 2: op = 2;
                        showTasks();
                        break;
                case 3: op = 3;
                        addStudent();
                        break;
                case 4: op = 4;
                        delStudent();
                        break;
                case 5: op = 5;
                        updStudent();
                        break;
                case 6: op = 6;
                        addTask();
                        break;
                case 7:op = 7;
                       extendDeadline();
                       break;
                case 8:op = 8;
                       markStAsMissing();
                       break;
                case 9: op = 9;
                        addGrade();
                        break;
                case 10:
                        op = 10;
                        showGrades();
                        break;
                case 11: op = -1;
                         break;
                         default:
                           System.out.println("Comanda invalida");
                           break;
            }
        }
        return 0;
    }

    private void showStudents() {
        Iterator<StudentiEntity> it = this.studentService.getStudentsIterator();
        StudentiEntity s;
        while (it.hasNext()) {
            s = it.next();
            System.out.println(this.studentService.getStudentById(s.getID()));
        }
    }

    private void showTasks() {
        Iterator<TemeEntity> it = this.temaService.getTemaIterator();
        TemeEntity t;
        while (it.hasNext()) {
            t = it.next();
            System.out.println(this.temaService.getTemaById(t.getID()));
        }
    }

    private void addStudent() {
        Scanner sc1 = new Scanner(System.in);
//        System.out.print("Id: ");
//        int id = sc1.nextInt(); sc1.nextLine();
        System.out.print("Nume: ");
        String nume = sc1.nextLine();
        System.out.print("Grupa: ");
        int gr = sc1.nextInt(); sc1.nextLine();
        System.out.print("Email: ");
        String email = sc1.next();
        try
        {
            this.studentService.addStudent(nume, gr, email);
        }catch (ValidationException e) {
            System.out.println(e.getError());
        }

    }

    private void delStudent() {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Se va sterge studentul cu id: ");
        int id = sc1.nextInt();
        try
        {
            this.studentService.deleteStudent(id);
        }catch (ValidationException e) {
            System.out.println(e.getError());
        }

    }

    private void updStudent() {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Id: ");
        int id = sc1.nextInt(); sc1.nextLine();
        System.out.print("Nume: ");
        String nume = sc1.next();
        System.out.print("Grupa: ");
        int gr = sc1.nextInt();
        System.out.print("Email: ");
        String email = sc1.next();
        try
        {
            this.studentService.updateStudent(id, nume, gr, email);
        }catch(ValidationException e) {
            System.out.println(e.getError());
        }
    }

    private void addTask() {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Id: ");
        int id = sc1.nextInt(); sc1.nextLine();
        System.out.print("Descriere: ");
        String descriere = sc1.nextLine();
        System.out.print("Deadline: ");
        int termen = sc1.nextInt();
        System.out.print("Saptamana primire: ");
        int primire = sc1.nextInt();
        try
        {
            this.temaService.addTema(id, descriere, termen, primire);
        }catch (ValidationException e) {
            System.out.println(e.getError());
        }

    }

    private void extendDeadline() {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Id: ");
        int id = sc1.nextInt();
        if(this.temaService.extendsDeadlineWithOneWeek(id) == -1)
            System.out.println("Tema nu a putut fi extinsa");

    }

    private void markStAsMissing() {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Id student: ");
        int id = sc1.nextInt();
        StudentiEntity student = this.studentService.markStudentAsMissing(id);
        if(student != null)
            System.out.println(student + " este prezent = " + student.isPrezent());
        else
            System.out.println("Acest student nu exista");

    }
    private void showGrades() {
        Iterator<NoteEntity> it = this.notaService.getGradesIterator();
        NoteEntity n;
        while(it.hasNext()) {
            n = it.next();
            System.out.println(this.notaService.getGradeById(n.getIdStudent(), n.getIdTema()));
        }
    }

    private void addGrade() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Se acorda o nota");
        System.out.print("Id student: ");
        int id_student = sc1.nextInt(); sc1.nextLine();
        System.out.print("Id teme: ");
        int id_teme = sc1.nextInt(); sc1.nextLine();
        System.out.print("Id profesor: ");
        int id_profesor = sc1.nextInt(); sc1.nextLine();
        System.out.print("Nota: ");
        double valoare = sc1.nextDouble(); sc1.nextLine();
        System.out.print("Data predarii: ");
        String data = sc1.next(); sc1.nextLine();
        LocalDate localDate = LocalDate.parse(data);
        System.out.print("Feedback: ");
        String feedback = sc1.nextLine();

        try{
            this.notaService.addNota(id_student, id_teme, id_profesor, valoare, localDate, feedback, false);
        }catch (StudentPresentException e) {
            System.out.println(e.getError());
        }
    }
}