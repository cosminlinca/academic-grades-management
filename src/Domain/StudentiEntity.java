package Domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Studenti", schema = "dbo", catalog = "Catalog")
public class StudentiEntity implements HasID<Integer> {
    private int idStudent;
    private String nume;
    private Integer grupa;
    private String email;
    private boolean prezent = true;

    public StudentiEntity() {

    }

    public StudentiEntity(int idStudent, String nume, String email) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.email = email;
        this.grupa = 0;
    }

    public StudentiEntity(int idStudent, String nume, Integer grupa, String email) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
    }

    @Override
    @Id
    @Column(name = "id_student")
    public Integer getID() {
        return idStudent;
    }

    @Override
    public void setID(Integer idStudent) {
        this.idStudent = idStudent;
    }

    @Basic
    @Column(name = "nume")
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Basic
    @Column(name = "grupa")
    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "prezent")
    public boolean isPrezent() {
        return prezent;
    }

    public void setPrezent(boolean prezent) {
        this.prezent = prezent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentiEntity that = (StudentiEntity) o;
        return idStudent == that.idStudent &&
                prezent == that.prezent &&
                Objects.equals(nume, that.nume) &&
                Objects.equals(grupa, that.grupa) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent, nume, grupa, email, prezent);
    }

    @Override
    public String toString() {
        return "id=" + idStudent + "|" +
                "nume=" + nume + "|" +
                "email=" + email;
    }
}
