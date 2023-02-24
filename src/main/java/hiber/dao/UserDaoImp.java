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
   public User findUser(String car_model, int car_series) {
      TypedQuery<Car> typedQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :car_model and series = :car_series")
              .setParameter("car_model", car_model)
              .setParameter("car_series", car_series);
      List<Car> cars = typedQuery.getResultList();
      Car car = cars.get(0);
      List<User> ListUser = listUsers();
      for (User user : ListUser) {
         user.getCar().equals(car);
         return user;
      }
      return null;
   }

   @Override
   public void deleteUserFromTable() {
      List<User> users = listUsers();
      for(User user : users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }
}
