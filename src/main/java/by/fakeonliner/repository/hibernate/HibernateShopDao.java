package by.fakeonliner.repository.hibernate;

import by.fakeonliner.entity.shop.Shop;
import by.fakeonliner.repository.ShopDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernateShopDao implements ShopDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        session.save(shop);
    }

    @Override
    public boolean existByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        boolean exist = session.createQuery("select count(*) from Shop where email= :email")
                .setParameter("email", email).list().isEmpty();
        return exist;
    }

    @Override
    public Shop getShopByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (Shop) session.createQuery("from Shop where email= :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public List<Shop> getShopList() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Shop", Shop.class)
                .getResultList();
    }

    @Override
    public void edit(Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        session.update(shop);
    }

    @Override
    public void delete(Shop shop) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(shop);
    }
}
