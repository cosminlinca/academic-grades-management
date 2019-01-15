package Repository;

import Domain.Student;
import Domain.StudentiEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class StudentDBRepository extends AbstractDBRepository<Integer, StudentiEntity> {
    @Override
    public void loadData() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Domain.StudentiEntity");
        List names = query.list();
        Iterator iterator = names.iterator();
        while(iterator.hasNext()) {
            StudentiEntity studentiEntity = (StudentiEntity) iterator.next();
            super.saveInMemory(studentiEntity);
        }
    }

    @Override
    protected void updateDB(StudentiEntity entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        StudentiEntity studentiEntity = s.load(StudentiEntity.class, new Integer(entity.getID()));
        transaction.commit();
        studentiEntity.setNume(entity.getNume());
        studentiEntity.setGrupa(entity.getGrupa());
        studentiEntity.setEmail(entity.getEmail());
        studentiEntity.setPrezent(entity.isPrezent());
        Transaction transaction2 = s.beginTransaction();
        s.update(studentiEntity);
        transaction2.commit();
    }
}
