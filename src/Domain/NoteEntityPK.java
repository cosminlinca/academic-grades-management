package Domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class NoteEntityPK implements Serializable {
    private int idStudent;
    private int idTema;

    @Column(name = "id_student")
    @Id
    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    @Column(name = "id_tema")
    @Id
    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntityPK that = (NoteEntityPK) o;
        return idStudent == that.idStudent &&
                idTema == that.idTema;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent, idTema);
    }
}
