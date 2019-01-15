package Domain;

import java.time.LocalDate;

public class NotaDTO {
    private String name;
    private Integer group;
    private String task;
    private Double grade;
    private LocalDate date;

    public NotaDTO(String name, Integer group, String task, Double grade, LocalDate date) {
        this.name = name;
        this.group = group;
        this.task = task;
        this.grade = grade;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public Double getGrade() {
        return grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getGroup() {
        return group;
    }
}
