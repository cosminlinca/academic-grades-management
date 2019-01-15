package Validation;

/**
 * It throws an exception if a mark is added for a student that is absent
 */
public class StudentPresentException extends RuntimeException {
    private String error;

    public StudentPresentException(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
