package Domain;

import java.time.LocalDate;

public class NotaStDTO {
    private String task;
    private Double grade;
    private LocalDate localDate;

    public NotaStDTO(String task, Double grade, LocalDate localDate) {
        this.task = task;
        this.grade = grade;
        this.localDate = localDate;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
