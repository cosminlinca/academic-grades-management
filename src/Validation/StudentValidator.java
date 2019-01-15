package Validation;

import Domain.Student;
import Domain.StudentiEntity;

import java.util.ArrayList;
import java.util.List;

public class StudentValidator implements Validator<StudentiEntity> {
    /**
     *
     * Validate data for a Student entity
     * @param entity
     * @throws ValidationException
     */
    @Override
    public void validate(StudentiEntity entity){
        List<String> errorMessages = new ArrayList<>();
        if(entity.getID() < 0) errorMessages.add("Invalid ID");
        if(entity.getNume().equals("")) errorMessages.add("Invalid name");
        if(entity.getEmail().equals("")) errorMessages.add("Invalid email");
        if(entity.getGrupa() < 0) errorMessages.add("Invalid grupa");

        if(errorMessages.size() > 0) throw new ValidationException(errorMessages);
    }
}
