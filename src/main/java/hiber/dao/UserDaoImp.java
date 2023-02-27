package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findUserByCar(String car_model, int car_series) {
      TypedQuery<Car> typedQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :car_model and series = :car_series")
              .setParameter("car_model", car_model)
              .setParameter("car_series", car_series);
      Car car = typedQuery.getSingleResult();
      User user = car.getUser();
      return user;
   }

   @Override
   public void deleteUserFromTable() {
      List<User> users = listUsers();
      for(User user : users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }
}
