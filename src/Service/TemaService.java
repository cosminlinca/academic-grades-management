package Service;

import Domain.Student;
import Domain.Tema;
import Domain.TemeEntity;
import Repository.CrudRepository;
import Utils.*;
import Validation.TemaValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TemaService implements Observable<TemaChangeEvent> {

    private CrudRepository<Integer, TemeEntity> temaCrudRepository;
    private TemaValidator temaValidator;

    public TemaService(CrudRepository temaCrudRepository, TemaValidator temaValidator) {
        this.temaCrudRepository = temaCrudRepository;
        this.temaValidator = temaValidator;
    }


    public TemeEntity addTema(int idTema, String descriere, int deadline, int primireTema) {
        TemeEntity tema = new TemeEntity(idTema, descriere, deadline, primireTema);
        temaValidator.validate(tema);

        TemeEntity valid = temaCrudRepository.save(tema);
        if(valid == null)
            notifyObservers(new TemaChangeEvent(EventType.ADD,tema));

        return valid;
    }

    /**
     *
     * @param integer
     * @return 1, if the homework was extended by one week, or -1, if the homework can't be extended
     */
    public int extendsDeadlineWithOneWeek(Integer integer){
        int currentWeek = Time.getCurrentUniversityWeek();
        TemeEntity tema = temaCrudRepository.findOne(integer);
        //temaValidator.validate(tema);
        if(currentWeek < tema.getDeadline()) {
            TemeEntity tema2 = new TemeEntity(tema.getID(), tema.getDescriere(), tema.getDeadline() + 1, tema.getPrimire());
            temaCrudRepository.update(tema2);
            notifyObservers(new TemaChangeEvent(EventType.EXTEND,tema2));
            return 1;
        }
        else
            return -1; //can't extend the deadline;
    }

    /**
     *
     * @return the number of tasks
     */
    public int getNumberOfTasks() {
        Iterator<TemeEntity> iterator = this.temaCrudRepository.findAll().iterator();
        AtomicInteger size = new AtomicInteger(0);
        iterator.forEachRemaining(it -> size.getAndIncrement());
        return size.get();
    }

    /**
     *
     * @return an iterator for the tasks entities
     */
    public Iterator getTemaIterator() {
        Iterator<TemeEntity> iterator = this.temaCrudRepository.findAll().iterator();
        return iterator;
    }

    public TemeEntity getTemaById(Integer id) {
        return this.temaCrudRepository.findOne(id);
    }


    private List<Observer<TemaChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<TemaChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<TemaChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(TemaChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public TemeEntity findByName(String task) {
        Iterator<TemeEntity> temaIterator = this.temaCrudRepository.findAll().iterator();
        while(temaIterator.hasNext()) {
            TemeEntity tema = temaIterator.next();
            if(tema.getDescriere().equalsIgnoreCase(task)) return tema;
        }
        return null;
    }

}
