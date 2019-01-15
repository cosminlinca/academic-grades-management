package Repository;

import Domain.HasID;

import java.util.HashMap;

public class InMemoryCrudRepository<ID, E extends HasID<ID>> implements CrudRepository<ID,E> {

    private HashMap<ID, E> hashMapEntities = new HashMap<>();
    //private Validator<E> eValidator;
    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return null if the entity was not found, or the Entity if ID=id
     */
    @Override
    public E findOne(ID id) {
        if(id == null)
            throw new IllegalArgumentException();
        return hashMapEntities.get(id);
    }
    /**
     *
     * @return Entities
     */
    @Override
    public Iterable<E> findAll() {
        return hashMapEntities.values();
    }

    /**
     *
     * @param entity
     * entity must be not null
     * @return null, if the Entity was succesfully saved, else returns the current value
     */
    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        return hashMapEntities.putIfAbsent(entity.getID(), entity);
    }

    /**
     *
     * @param id
     * @return null, if the id was not found, or the Entity that was deleted, otherwise
     */
    @Override
    public E delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("entity must be not null");
        return hashMapEntities.remove(id);
    }

    /**
     *
     * @param entity
     * entity must not be null
     * @return null, if the entity was replaced, or the Entity, if the id of entity E was not found
     */
    @Override
    public E update(E entity){

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        return hashMapEntities.replace(entity.getID(), entity);
    }
}
