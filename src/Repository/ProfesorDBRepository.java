package Repository;

import Domain.ProfesoriEntity;
import Domain.StudentiEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class ProfesorDBRepository extends AbstractDBRepository<Integer, ProfesoriEntity> {
    @Override
    public void loadData() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Domain.ProfesoriEntity");
        List names = query.list();
        Iterator iterator = names.iterator();
        while(iterator.hasNext()) {
            ProfesoriEntity profesoriEntity = (ProfesoriEntity) iterator.next();
            super.saveInMemory(profesoriEntity);
        }
    }

    @Override
    protected void updateDB(ProfesoriEntity entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        ProfesoriEntity profesoriEntity = s.load(ProfesoriEntity.class, new Integer(entity.getID()));
        transaction.commit();
        profesoriEntity.setNume(entity.getNume());
        profesoriEntity.setEmail(entity.getEmail());
        profesoriEntity.setDomeniu(entity.getDomeniu());
        Transaction transaction2 = s.beginTransaction();
        s.update(profesoriEntity);
        transaction2.commit();
    }
}
