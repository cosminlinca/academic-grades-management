package Domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Teme", schema = "dbo", catalog = "Catalog")
public class TemeEntity implements HasID<Integer>{
    private int idTema;
    private String descriere;
    private int deadline;
    private int primire;

    public TemeEntity(int idTema, String descriere, int deadline, int primireTema) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.primire = primireTema;
    }

    public TemeEntity() {

    }

    @Override
    @Id
    @Column(name = "id_tema")
    public Integer getID() {
        return idTema;
    }

    @Override
    public void setID(Integer idTema) {
        this.idTema = idTema;
    }

    @Basic
    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Basic
    @Column(name = "deadline")
    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "primire")
    public int getPrimire() {
        return primire;
    }

    public void setPrimire(int primire) {
        this.primire = primire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemeEntity that = (TemeEntity) o;
        return idTema == that.idTema &&
                deadline == that.deadline &&
                primire == that.primire &&
                Objects.equals(descriere, that.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTema, descriere, deadline, primire);
    }


    @Override
    public String toString() {
        return "id=" + idTema + "|" +
                "descriere=" + descriere + "|" +
                "deadline=" + deadline + "|" +
                "primire=" + primire;
    }
}
