import entity.Post;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration conf = new Configuration().configure();
        SessionFactory factory = conf.buildSessionFactory();
        Session s = factory.openSession();

        User x = s.get(User.class, 0);
        x.setNameToUpperCase();
        s.beginTransaction();
        s.update(x);
        s.getTransaction().commit();
        System.out.println(x);

        /*s.beginTransaction();
       s.save(user);
        s.save(post);*/

        //s.getTransaction().commit();

        System.out.println(s.createCriteria(User.class).list());

        s.close();
        // factory.close();
    }
}
