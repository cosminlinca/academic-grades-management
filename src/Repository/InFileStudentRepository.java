package Repository;

import Domain.Student;

import java.util.List;

public class InFileStudentRepository extends AbstractFileRepository<Integer, Student> {
    public InFileStudentRepository(String fileName) {
        super(fileName);
    }

    @Override
    public Student extractEntity(List<String> attributes) {
        int id = Integer.parseInt(attributes.get(0).split("=")[1]);
        String nume = attributes.get(1).split("=")[1];
        String email = attributes.get(2).split("=")[1];

        Student student = new Student(id, nume, email);
        return student;
    }
}
