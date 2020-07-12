package Repo;

import Domain.StudentiEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DBRepo {
    public static void main(String[] args) {
       Configuration c = new Configuration().configure("/hibernate.cfg.xml");
       SessionFactory sessionFactory = c.buildSessionFactory();
       Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();

        StudentiEntity studentiEntity = new StudentiEntity();
        studentiEntity.setID(9);
        studentiEntity.setNume("mihai");
        studentiEntity.setEmail("yyy");
        studentiEntity.setGrupa(228);
        studentiEntity.setPrezent(false);

        s.save(studentiEntity);
        transaction.commit();
    }
}
