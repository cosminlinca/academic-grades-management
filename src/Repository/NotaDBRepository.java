package Repository;

import Domain.NoteEntity;
import Utils.HibernateUtil;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class NotaDBRepository extends AbstractDBRepository<String, NoteEntity> {
    @Override
    public void loadData() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Domain.NoteEntity");
        List names = query.list();
        Iterator iterator = names.iterator();
        while(iterator.hasNext()) {
            NoteEntity noteEntity = (NoteEntity) iterator.next();
            super.saveInMemory(noteEntity);
        }
    }

    @Override
    protected void updateDB(NoteEntity entity) {
       throw new NotYetImplementedException();
    }
}
