import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbUtilities {
    private static Configuration conf;
    private static SessionFactory f;
    private static Session s;

    public static Session getSession() {
        if (s == null) {
            conf = new Configuration().configure();
            f = conf.buildSessionFactory();
            s = f.openSession();
        }
        return s;
    }

    public static void closeSession() {
        s.close();
        f.close();
    }
}
