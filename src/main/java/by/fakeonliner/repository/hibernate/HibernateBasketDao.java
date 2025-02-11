package by.fakeonliner.repository.hibernate;

import by.fakeonliner.dto.BasketProductDto;
import by.fakeonliner.entity.user.User;
import by.fakeonliner.repository.BasketDao;
import by.fakeonliner.repository.hibernate_query_constant.HBasketQueryConstant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HibernateBasketDao implements BasketDao {

    private static final String USER_ID = "userId";
    private static final String PRODUCT_ID = "productId";

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addProduct(BasketProductDto product) {
        Session session = sessionFactory.openSession();
        session.save(product);
        session.close();
    }

    @Override
    public void deleteProductDb(BasketProductDto product) {
        Session session = sessionFactory.openSession();
        session.delete(product);
        session.close();
    }

    @Override
    public List<BasketProductDto> getBasketProducts(User user) {
        Session session = sessionFactory.openSession();
        Query<BasketProductDto> query = session.createQuery(HBasketQueryConstant.GET_BASKET_PRODUCTS_QUERY,
                                                        BasketProductDto.class);
        query.setParameter(USER_ID, user.getId());
        List<BasketProductDto> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    @Override
    public int getProductAmount(BasketProductDto product) {
        Session session = sessionFactory.openSession();
        Query<BasketProductDto> query = session.createQuery(HBasketQueryConstant.GET_PRODUCT_AMOUNT_QUERY,
                                                        BasketProductDto.class);
        query.setParameter(USER_ID, product.getUser().getId());
        query.setParameter(PRODUCT_ID, product.getProduct().getId());
        BasketProductDto basketProductDto = query.getSingleResult();
        session.close();
        return basketProductDto.getAmount();
    }

    @Override
    public void updateProduct(BasketProductDto product) {
        Session session = sessionFactory.openSession();
        session.update(product);
        session.close();
    }

    @Override
    public boolean existByProductDto(BasketProductDto product) {
        Session session = sessionFactory.openSession();
        Query<BasketProductDto> query = session.createQuery(HBasketQueryConstant.EXIST_BY_PRODUCT_DTO_QUERY, BasketProductDto.class);
        query.setParameter(USER_ID, product.getUser().getId());
        query.setParameter(PRODUCT_ID, product.getUser().getId());
        return (query.uniqueResult() != null);
    }
}