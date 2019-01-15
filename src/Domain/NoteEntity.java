package Domain;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "Note", schema = "dbo", catalog = "Catalog")
@IdClass(NoteEntityPK.class)
public class NoteEntity implements HasID<String>{
    private String idNota;
    private Integer idStudent;
    private Integer idTema;
    private Integer idProfesor;
    private Double valoare;
    private LocalDate data;
    private String feedback;

    public NoteEntity() {

    }

    public NoteEntity(Integer idStudent, Integer idTema, Integer idCadruDidactic, Double valoare, LocalDate localDate) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.idProfesor = idCadruDidactic;
        this.valoare = valoare;
        this.data = localDate;

        this.idNota = idStudent + "/" + idTema;
    }
    public NoteEntity(Integer idStudent, Integer idTema, Integer idCadruDidactic, Double valoare, LocalDate predareTema, String feedback) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.idProfesor = idCadruDidactic;
        this.valoare = valoare;
        this.data = predareTema;
        this.feedback = feedback;

        this.idNota = idStudent + "/" + idTema;
    }

    @Override
    @Basic
    @Column(name = "id_nota")
    public String getID() {
        return idNota;
    }

    @Override
    public void setID(String idNota) {
        this.idNota = idNota;
    }

    @Id
    @Column(name = "id_student")
    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    @Id
    @Column(name = "id_tema")
    public Integer getIdTema() {
        return idTema;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }

    @Basic
    @Column(name = "id_profesor")
    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    @Basic
    @Column(name = "valoare")
    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    @Basic
    @Column(name = "data")
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Basic
    @Column(name = "feedback")
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity that = (NoteEntity) o;
        return idStudent == that.idStudent &&
                idTema == that.idTema &&
                idProfesor == that.idProfesor &&
                Double.compare(that.valoare, valoare) == 0 &&
                Objects.equals(idNota, that.idNota) &&
                Objects.equals(data, that.data) &&
                Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNota, idStudent, idTema, idProfesor, valoare, data, feedback);
    }


    public static DateTimeFormatter dateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter;
    }

    @Override
    public String toString() {
        return  "idStudent=" + idStudent + "|" +
                "idTema=" + idTema + "|" +
                "idCadruDidactic=" + idProfesor + "|" +
                "valoare=" + valoare + "|" +
                "data=" + data.format(dateTimeFormatter());
    }
}
