package Validation;

import Domain.ProfesoriEntity;

import java.util.ArrayList;
import java.util.List;

public class ProfessorValidator implements Validator<ProfesoriEntity> {
    @Override
    public void validate(ProfesoriEntity entity) throws ValidationException {
        List<String> errorMessages = new ArrayList<>();
        if(entity.getNume().equals("")) errorMessages.add("Invalid name");
        if(entity.getDomeniu().equals("")) errorMessages.add("Invalid domain");
        if(entity.getEmail().equals("")) errorMessages.add("Invalid email");
        if(errorMessages.size() > 0) throw new ValidationException(errorMessages);
    }
}
