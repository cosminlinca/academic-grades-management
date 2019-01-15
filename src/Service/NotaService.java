package Service;

import Domain.*;
import Repository.CrudRepository;
import Utils.*;
import Validation.NotaValidator;
import Validation.StudentPresentException;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotActiveException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;

public class NotaService implements Observable<NotaChangeEvent> {
    CrudRepository<String, NoteEntity> catalog;
    NotaValidator notaValidator;
    StudentService studentService;
    TemaService temaService;
    private final BiPredicate<Integer, Integer> temaValida = (x,y)-> x-y>2;
    /**
     * @param catalog
     * @param notaValidator
     * @param studentService
     * @param temaService
     */
    public NotaService(CrudRepository<String, NoteEntity> catalog, NotaValidator notaValidator, StudentService studentService, TemaService temaService) {
        this.catalog = catalog;
        this.notaValidator = notaValidator;
        this.studentService = studentService;
        this.temaService = temaService;
    }

    /**
     *
     * @param idStudent
     * @param idTema
     * @param idCadruDidactic
     * @param valoare
     * @param predareTema
     * @param feedback
     * @param motivated
     * @implNote: add a grade for a student with id = idStudent;
     *            If the submission week is greater than deadline for a task, the grade remains the same.
     *            else, the grade will be lesser with 2,5*(submission week - deadline day)
     *
     *            if motivated = true, the grade will remain the same
     */
    public NoteEntity addNota(Integer idStudent, Integer idTema, Integer idCadruDidactic , Double valoare, LocalDate predareTema, String feedback, Boolean motivated) {
        NoteEntity nota = new NoteEntity(idStudent, idTema, idCadruDidactic, valoare, predareTema, feedback);
        this.notaValidator.validate(nota);

        TemeEntity tema = this.temaService.getTemaById(idTema);
        StudentiEntity student = this.studentService.getStudentById(idStudent);
        if(student.isPrezent() == false) {
            throw new StudentPresentException("Studentul e momentan absent, reveniti cu adaugarea notei!");
        }
        int saptamanaDePredare = Time.getUniversityWeek(predareTema);
        if(motivated == false) {
            if(saptamanaDePredare > tema.getDeadline()) {
                if(temaValida.test(saptamanaDePredare, tema.getDeadline())) {
                    valoare = 1d;
                }
                else {
                    valoare = valoare - 2.5*(saptamanaDePredare - tema.getDeadline());
                }
            }
        }
        nota.setValoare(valoare);
        NoteEntity n = this.catalog.save(nota);
        if(n==null) addToStudentGrade(idStudent, nota, saptamanaDePredare, tema.getDeadline());
        notifyObservers(new NotaChangeEvent(EventType.ADD, n));
        return n;
    }

    /**
     * @param idStudent
     * @param nota
     * @param saptamanaDePredare
     * @param deadline
     * A grade is added for a student with ID = idStudent
     * @implNote: the grade will be added in a specific file with the name of the student:
     * ex: John will have the grades in John.txt file.
     */
    private void addToStudentGrade(Integer idStudent, NoteEntity nota, Integer saptamanaDePredare, Integer deadline) {
        StudentiEntity student = this.studentService.getStudentById(idStudent);
        String extension = ".txt";
        String fileName ="resources/students/" + student.getNume() + extension;
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write("Tema: " + String.valueOf(nota.getIdTema()));
            bW.newLine();
            bW.write("Nota: " + String.valueOf(nota.getValoare()));
            bW.newLine();
            bW.write("Predata in saptamana: " + String.valueOf(saptamanaDePredare));
            bW.newLine();
            bW.write("Deadline: " + String.valueOf(deadline));
            bW.newLine();
            bW.write("Feedback: " + String.valueOf(nota.getFeedback()));
            bW.newLine();
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the number of grades
     */
    public int getNumberOfGrades() {
        Iterator<NoteEntity> iterator = this.catalog.findAll().iterator();
        AtomicInteger size = new AtomicInteger(0);
        iterator.forEachRemaining(it-> size.getAndIncrement());
        return size.get();
    }

    /**
     *
     * @return an iterator for the grades entities
     */
    public Iterator<NoteEntity> getGradesIterator() {
        return this.catalog.findAll().iterator();
    }

    private List<Observer<NotaChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<NotaChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<NotaChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(NotaChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public NoteEntity getGradeById(int idStudent, int idTema) {
        return this.catalog.findOne(idStudent + "/" + idTema);
    }

    public void deleteGradesForAStudent(Integer id) {
        List<String> deleted = new ArrayList<>();
        Iterator<NoteEntity> noteEntities = this.catalog.findAll().iterator();
        while(noteEntities.hasNext()) {
            NoteEntity noteEntity = noteEntities.next();
            if(noteEntity.getIdStudent().equals(id))
                deleted.add(noteEntity.getID());
        }
        for (String d: deleted
             ) {
            this.catalog.delete(d);
        }
    }
}
