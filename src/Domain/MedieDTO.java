package Domain;

public class MedieDTO {
    String name;
    Integer group;
    Double average;

    public MedieDTO(String name, Integer group, Double average) {
        this.name = name;
        this.group = group;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
