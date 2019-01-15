package Domain;

import java.io.Serializable;

public class Student implements HasID<Integer>, Serializable {

    private int idStudent;
    private String nume;
    private Integer grupa;
    private String email;
    //private CadruDidactic cadruDidactic;
    private boolean prezent = true;


    public Student(int idStudent, String nume, String email) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.email = email;

        this.grupa = 0;
    }

    public Student(int idStudent, String nume, Integer grupa, String email) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
    }



    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getPrezent() {
        return this.prezent;
    }

    public void setPrezent(boolean prezent) {
        this.prezent = prezent;
    }

    @Override
    public Integer getID() {
        return idStudent;
    }

    @Override
    public void setID(Integer integer) {
        this.idStudent = integer;
    }

    @Override
    public String toString() {
        return "id=" + idStudent + "|" +
                "nume=" + nume + "|" +
                "email=" + email;
    }
}
