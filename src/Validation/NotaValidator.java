package Validation;

import Domain.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NotaValidator implements Validator<NoteEntity> {
    @Override
    public void validate(NoteEntity entity){
        List<String> errorMessages = new ArrayList<>();
        if(entity.getIdStudent() < 0) errorMessages.add("ID Student invalid");
        if(entity.getIdTema() < 0) errorMessages.add("ID Tema invalid");
        if(entity.getIdProfesor() < 0) errorMessages.add("ID Cadru didactic invalid");
        if(entity.getValoare() < 1 || entity.getValoare() > 10) errorMessages.add("Nota invalida");


        if(errorMessages.size() > 0) throw new ValidationException(errorMessages);
    }
}
