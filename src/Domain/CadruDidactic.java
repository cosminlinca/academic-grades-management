package Domain;

public class CadruDidactic implements HasID<Integer>{

    private int idProf;
    private String name;
    private String domeniu;

    public CadruDidactic(int idProf ,String name, String domeniu) {
        this.idProf = idProf;
        this.name = name;
        this.domeniu = domeniu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    @Override
    public Integer getID() {
        return this.idProf;
    }

    @Override
    public void setID(Integer integer) {
        this.idProf = integer;

    }


}
