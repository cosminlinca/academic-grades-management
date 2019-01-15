package Repository;

import Domain.ConturiEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class AccountDBRepository extends AbstractDBRepository<Integer, ConturiEntity> {

    @Override
    public void loadData() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Domain.ConturiEntity");
        List names = query.list();
        Iterator iterator = names.iterator();
        while(iterator.hasNext()) {
            ConturiEntity conturiEntity = (ConturiEntity) iterator.next();
            super.saveInMemory(conturiEntity);
        }
    }

    @Override
    protected void updateDB(ConturiEntity entity) {
        throw new NotYetImplementedException();
    }
}
