package Repository;

import Domain.StudentiEntity;
import Domain.Tema;
import Domain.TemeEntity;
import Utils.HibernateUtil;
import Utils.TemaChangeEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class TemaDBRepository extends AbstractDBRepository<Integer, TemeEntity> {
    @Override
    public void loadData() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Domain.TemeEntity");
        List names = query.list();
        Iterator iterator = names.iterator();
        while(iterator.hasNext()) {
            TemeEntity temeEntity = (TemeEntity) iterator.next();
            super.saveInMemory(temeEntity);
        }
    }

    @Override
    protected void updateDB(TemeEntity entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        TemeEntity temeEntity = s.load(TemeEntity.class, new Integer(entity.getID()));
        transaction.commit();
        temeEntity.setDescriere(entity.getDescriere());
        temeEntity.setDeadline(entity.getDeadline());
        temeEntity.setPrimire(entity.getPrimire());
        Transaction transaction2 = s.beginTransaction();
        s.update(temeEntity);
        transaction2.commit();
    }
}
