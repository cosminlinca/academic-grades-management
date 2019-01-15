package Validation;

import Domain.Tema;
import Domain.TemeEntity;

import java.util.ArrayList;
import java.util.List;

public class TemaValidator implements Validator<TemeEntity> {

    /**
     * Validate data for a Tema entity
     * @param entity
     * @throws ValidationException
     */
    @Override
    public void validate(TemeEntity entity){
        List<String> errorMessages = new ArrayList<>();
        if(entity.getID() < 0) errorMessages.add("Invalid ID");
        if(entity.getDescriere().equals("")) errorMessages.add("Invalid description");
        if(entity.getDeadline() < 0|| entity.getDeadline() > 20) errorMessages.add("Invalid deadline");
        if(entity.getPrimire() < 0 || entity.getPrimire() > 20) errorMessages.add("Invalid data for receive the task");
        if(entity.getPrimire() >= entity.getDeadline()) errorMessages.add("Invalid period");

        if(errorMessages.size() > 0) throw new ValidationException(errorMessages);
    }
}
