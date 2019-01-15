package Domain;

import org.omg.PortableInterceptor.INACTIVE;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Objects;

@Entity
@Table(name = "Profesori", schema = "dbo", catalog = "Catalog")
public class ProfesoriEntity implements HasID<Integer> {
    private Integer idProf;
    private String nume;
    private String domeniu;
    private String email;


    public ProfesoriEntity() {

    }

    public ProfesoriEntity(Integer id, String name, String domain, String email) {
        this.idProf = id;
        this.nume = name;
        this.domeniu = domain;
        this.email = email;
    }

    @Override
    @Id
    @Column(name = "id_prof")
    public Integer getID() {
        return idProf;
    }

    @Override
    public void setID(Integer idProf) {
        this.idProf = idProf;
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
    @Column(name = "domeniu")
    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfesoriEntity that = (ProfesoriEntity) o;
        return idProf == that.idProf &&
                Objects.equals(nume, that.nume) &&
                Objects.equals(domeniu, that.domeniu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProf, nume, domeniu);
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
