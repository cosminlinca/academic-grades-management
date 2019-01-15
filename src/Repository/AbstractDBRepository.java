package Repository;

import Domain.HasID;
import Domain.StudentiEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractDBRepository<ID, E extends HasID<ID>> extends InMemoryCrudRepository<ID, E> {
    public AbstractDBRepository() {
        loadData();
    }
    public abstract void loadData();

    @Override
    public E save(E entity) {
        E e = super.save(entity);
        if(e == null) {
            addToDB(entity);
        }
        return e;
    }
    public E saveInMemory(E entity) {
        return super.save(entity);
    }

    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        if(e != null) {
            deleteFromDB(e);
        }
        return e;
    }
    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if(e != null) {
            updateDB(entity);
        }
        return e;
    }

    protected abstract void updateDB(E entity);

    private void deleteFromDB(E e) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        s.delete(e);
        transaction.commit();
    }

    private void addToDB(E e) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        s.save(e);
        transaction.commit();
    }
}
