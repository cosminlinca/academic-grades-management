package Service;

import Domain.Student;
import Domain.StudentiEntity;
import Repository.CrudRepository;
import Utils.EventType;
import Utils.Observable;
import Utils.Observer;
import Utils.StudentChangeEvent;
import Validation.StudentValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentService implements Observable<StudentChangeEvent> {

    private CrudRepository<Integer,StudentiEntity> studentCrudRepository;
    private StudentValidator studentValidator;


    public StudentService(CrudRepository studentCrudRepository,StudentValidator studentValidator) {
        this.studentCrudRepository = studentCrudRepository;
        this.studentValidator = studentValidator;
    }


    public StudentiEntity addStudent(String nume, String email) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("resources/students_id.txt"));
            Integer id = Integer.parseInt(br.readLine());
            br.close();

            StudentiEntity student = new StudentiEntity(id, nume, email);
            studentValidator.validate(student);

            StudentiEntity valid = studentCrudRepository.save(student);
            if(valid == null)
                notifyObservers(new StudentChangeEvent(EventType.ADD,student));

            id++;
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/students_id.txt"));
            writer.write(id.toString());
            writer.close();

            return valid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentiEntity addStudent(String nume, Integer grupa, String email) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("resources/students_id.txt"));
            Integer id = Integer.parseInt(br.readLine());
            br.close();

            StudentiEntity student = new StudentiEntity(id, nume, grupa, email);
            studentValidator.validate(student);

            StudentiEntity valid = studentCrudRepository.save(student);
            if(valid == null)
                notifyObservers(new StudentChangeEvent(EventType.ADD,student));

            id++;
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/students_id.txt"));
            writer.write(id.toString());
            writer.close();

            return valid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public StudentiEntity addStudent(int idStudent, String nume, Integer grupa, String email) {
        StudentiEntity student = new StudentiEntity(idStudent, nume, grupa, email);
        studentValidator.validate(student);

        StudentiEntity valid = studentCrudRepository.save(student);
        if(valid == null)
            notifyObservers(new StudentChangeEvent(EventType.ADD,student));

        return valid;
    }

    public StudentiEntity addStudent(int idStudent, String nume, String email) {
        StudentiEntity student = new StudentiEntity(idStudent, nume, email);
        studentValidator.validate(student);

        StudentiEntity valid = studentCrudRepository.save(student);
        if(valid == null)
            notifyObservers(new StudentChangeEvent(EventType.ADD,student));

        return valid;
    }

    public StudentiEntity deleteStudent(int idStudent) {
        StudentiEntity st = this.studentCrudRepository.findOne(idStudent);
        StudentiEntity valid = studentCrudRepository.delete(idStudent);
        if(valid != null)
            notifyObservers(new StudentChangeEvent(EventType.DELETE,st));

        return valid;
    }

    /**
     * @param idStudent
     * @param nume
     * @param grupa
     * @param email
     * @return a Student entity, if the student can't be update, or null, otherwise
     */
    public StudentiEntity updateStudent(int idStudent, String nume, Integer grupa, String email){
        StudentiEntity student = new StudentiEntity(idStudent, nume, grupa, email);
        studentValidator.validate(student);
        StudentiEntity valid = studentCrudRepository.update(student);
        if(valid != null)
            notifyObservers(new StudentChangeEvent(EventType.UPDATE,student));

        return valid;
    }

    /**
     * @param idStudent
     * @param nume
     * @param email
     * @return a Student entity, if the student can't be update, or null, otherwise
     */
    public StudentiEntity updateStudent(int idStudent, String nume, String email) {
        StudentiEntity student = new StudentiEntity(idStudent, nume, email);
        studentValidator.validate(student);
        StudentiEntity valid = studentCrudRepository.update(student);
        if(valid != null)
            notifyObservers(new StudentChangeEvent(EventType.UPDATE,student));
        return valid;
    }

    /**
     * @return an iterator for the students entities
     */
    public Iterator getStudentsIterator() {
        Iterator<StudentiEntity> iterator= this.studentCrudRepository.findAll().iterator();
        return iterator;
    }

    /**
     * @return the number of students
     */
    public int getNumberOfStudents() {
        Iterator<StudentiEntity> iterator = this.studentCrudRepository.findAll().iterator();
        /**
        An int value that may be updated atomically.*/
        AtomicInteger size = new AtomicInteger(0);
        iterator.forEachRemaining(it -> size.getAndIncrement());
        return size.get();
    }

    public StudentiEntity getStudentById(Integer id) {
        return this.studentCrudRepository.findOne(id);
    }

    /**
     @param integer
     @return null, if the ID is not exist, or the student with id ID, otherwise
     @implNote : if there is a student with ID=integer , the student will NOT be deleted; it will be marked as "missing"
     using setPresent(boolean) method.
     **/
    public StudentiEntity markStudentAsMissing(Integer integer) {
        StudentiEntity student = studentCrudRepository.findOne(integer);
        if (student == null)
            return null;

        //student.setPrezent(false);
        StudentiEntity studentiEntity = new StudentiEntity(student.getID(), student.getNume(), student.getGrupa(), student.getEmail());
        studentiEntity.setPrezent(false);
        StudentiEntity valid = this.studentCrudRepository.update(studentiEntity);
        if(valid != null)
            notifyObservers(new StudentChangeEvent(EventType.ABSENT,student));

        return valid;
    }

    public StudentiEntity markStudentAsPresent(Integer integer) {
        StudentiEntity student = studentCrudRepository.findOne(integer);
        if (student == null)
            return null;

        //student.setPrezent(true);
        StudentiEntity studentiEntity = new StudentiEntity(student.getID(), student.getNume(), student.getGrupa(), student.getEmail());
        studentiEntity.setPrezent(true);
        StudentiEntity valid = this.studentCrudRepository.update(studentiEntity);
        if(valid != null)
            notifyObservers(new StudentChangeEvent(EventType.ABSENT,student));
        return valid;
    }

    public StudentiEntity findByName(String name, int group) {
        Iterator<StudentiEntity> students = this.studentCrudRepository.findAll().iterator();
        while(students.hasNext()) {
            StudentiEntity student = students.next();
            if(student.getNume().equalsIgnoreCase(name) && student.getGrupa().equals(group)) return student;
        }
        return null;
    }

    private List<Observer<StudentChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Iterable<StudentiEntity> getAll() {
        return this.studentCrudRepository.findAll();
    }
}
