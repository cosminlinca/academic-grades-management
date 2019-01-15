package Domain;

public class Tema implements HasID<Integer> {

    private Integer idTema;
    private String descriere;
    private Integer deadline;
    private Integer primireTema;

    public Tema(int idTema, String descriere, int deadline, int primireTema) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
        this.primireTema = primireTema;
    }

    public Tema(int idTema, String descriere) {
        this.idTema = idTema;
        this.descriere = descriere;

        this.deadline = 0;
        this.primireTema = 0;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public Integer getPrimireTema() {
        return primireTema;
    }

    public void setPrimireTema(int primireTema) {
        this.primireTema = primireTema;
    }


    @Override
    public Integer getID() {
        return this.idTema;
    }

    @Override
    public void setID(Integer integer) {
        this.idTema = integer;
    }

    @Override
    public String toString() {
        return "id=" + idTema + "|" +
                "descriere=" + descriere + "|" +
                "deadline=" + deadline + "|" +
                "primire=" + primireTema;
    }
}
