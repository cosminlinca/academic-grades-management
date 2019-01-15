package Validation;

import java.util.List;

/**
 * RuntimeException !!!
 * Exception - se propaga eroarea -> throws ValidatorException in toate metodele care arunca exceptia
 */
public class ValidationException extends RuntimeException{
    private List<String> errorMessages;

    /**
     *
     * @param errorMessages
     */
    public ValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getError() {
        String errros = "";
        for (String s: errorMessages) {
            errros = errros + "\n" + s;
        }

        return errros;
    }


}
