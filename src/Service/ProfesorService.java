package Service;

import Domain.ProfesoriEntity;
import Repository.CrudRepository;
import Utils.EventType;
import Utils.Observable;
import Utils.Observer;
import Utils.ProfessorChangeEvent;
import Validation.ProfessorValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfesorService implements Observable<ProfessorChangeEvent> {
    CrudRepository<Integer, ProfesoriEntity> repository;
    private ProfessorValidator professorValidator;
    public ProfesorService(CrudRepository<Integer, ProfesoriEntity> repository, ProfessorValidator professorValidator) {
        this.repository = repository;
        this.professorValidator = professorValidator;
    }

    public ProfesoriEntity addProfesor(String name, String domain, String email) {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader("resources/professor_id.txt"));
            Integer id = Integer.parseInt(br.readLine());
            br.close();

            ProfesoriEntity profesoriEntity = new ProfesoriEntity(id, name, domain, email);
            this.professorValidator.validate(profesoriEntity);
            ProfesoriEntity valid = this.repository.save(profesoriEntity);
            if(valid == null)
                notifyObservers(new ProfessorChangeEvent(EventType.ADD, profesoriEntity));

            id++;
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/professor_id.txt"));
            writer.write(id.toString());
            writer.close();
            return valid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProfesoriEntity getById(Integer id) {
        return this.repository.findOne(id);
    }

    public Iterator<ProfesoriEntity> getProfessorIterator() {
        return this.repository.findAll().iterator();
    }

    public ProfesoriEntity findByName(String name) {
        Iterator<ProfesoriEntity> iterator = this.repository.findAll().iterator();
        while(iterator.hasNext()) {
            ProfesoriEntity profesoriEntity = iterator.next();
            if(profesoriEntity.getNume().equalsIgnoreCase(name))
                return profesoriEntity;
        }
        return null;
    }

    private List<Observer<ProfessorChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<ProfessorChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<ProfessorChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ProfessorChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
