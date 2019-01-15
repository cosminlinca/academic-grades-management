package Domain;

import javafx.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota implements HasID<Pair<Integer, Integer>>{

    private Integer idStudent;
    private Integer idTema;
    private Pair<Integer, Integer> idNota;
    private Integer idCadruDidactic;
    private Double valoare;
    private LocalDate data;
    private String feedback;

    public Nota(Integer idStudent, Integer idTema, Integer idCadruDidactic, Double valoare, LocalDate data, String feedback) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.idCadruDidactic = idCadruDidactic;
        this.valoare = valoare;
        this.data = data;
        this.feedback = feedback;

        this.idNota = new Pair<>(idStudent, idTema);
    }

    public Nota(Integer idStudent, Integer idTema, Pair<Integer, Integer> idNota, Integer idCadruDidactic, LocalDate data, String feedback) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.idNota = idNota;
        this.idCadruDidactic = idCadruDidactic;
        this.data = data;
        this.feedback = feedback;
    }


    public Nota(Integer idStudent, Integer idTema, Integer idCadruDidactic, Double valoare, LocalDate data) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.idNota = idNota;
        this.idCadruDidactic = idCadruDidactic;
        this.valoare = valoare;
        this.data = data;
        this.feedback = "";

        this.idNota = new Pair<>(idStudent, idTema);
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getIdTema() {
        return idTema;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }

    public Pair<Integer, Integer> getIdNota() {
        return idNota;
    }

    public void setIdNota(Pair<Integer, Integer> idNota) {
        this.idNota = idNota;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public Pair<Integer, Integer> getID() {
        return idNota;
    }

    @Override
    public void setID(Pair<Integer, Integer> integerIntegerPair) {
        this.idNota = integerIntegerPair;
    }

    public Integer getIdCadruDidactic() {
        return idCadruDidactic;
    }

    public void setIdCadruDidactic(Integer idCadruDidactic) {
        this.idCadruDidactic = idCadruDidactic;
    }

    public static DateTimeFormatter dateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter;
    }

    @Override
    public String toString() {
        return  "idStudent=" + idStudent + "|" +
                "idTema=" + idTema + "|" +
                "idCadruDidactic=" + idCadruDidactic + "|" +
                "valoare=" + valoare + "|" +
                "data=" + data.format(dateTimeFormatter());
    }
}
