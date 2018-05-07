package rwilk.englishWordsGUI.tool;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import rwilk.englishWordsGUI.model.Course;
import rwilk.englishWordsGUI.model.Lesson;
import rwilk.englishWordsGUI.model.Word;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Set settings
            Map<String, String> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "org.postgresql.Driver");
            settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/englishApp");
            settings.put(Environment.USER, System.getenv("PostgreSQL_User"));
            settings.put(Environment.PASS, System.getenv("PostgreSQL_Password"));
            settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
            settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
            settings.put(Environment.SHOW_SQL, "true");
            //settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            // Apply settings
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
            registryBuilder.applySettings(settings);
            // Create registry
            StandardServiceRegistry registry = registryBuilder.build();
            // Create MetadataSources and add model's classes to mapping
            MetadataSources sources = new MetadataSources(registry)
                    .addPackage("rwilk.englishWordsGUI.model")
                    .addAnnotatedClass(Word.class)
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Lesson.class);
                    //.addAnnotatedClass(Sentence.class);
            // Create Metadata
            Metadata metadata = sources.getMetadataBuilder().build();
            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            System.err.println(ex.toString());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
