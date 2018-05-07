package rwilk.englishWordsGUI.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import rwilk.englishWordsGUI.tool.HibernateUtil;

import java.util.List;

abstract class DAOMethods {

    @SuppressWarnings("unchecked")
    static <T> T getObjectById(T object, long id) {
        if (object != null) {
            try (Session session = HibernateUtil.getSession()) {
                Query query = session.createQuery("FROM " + object.getClass().getName() + " as c WHERE c.id =:id");

                query.setParameter("id", id);
                return (T) query.uniqueResult();
            } catch (HibernateException h) {
                System.err.println(h.toString());
            }
        }
        // Return null only when object is null or exception will appear
        return null;
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> getObjectsList(T object) {
        if (object != null) {
            try (Session session = HibernateUtil.getSession()) {
                Query query = session.createQuery("FROM " + object.getClass().getName());
                return (List<T>) query.list();
            } catch (HibernateException h) {
                System.err.println(h.toString());
            }
        }
        // Return null only when object is null or exception will appear
        return null;
    }

    static <T> T saveObject(T object) {
        if (object != null) {
            try (Session session = HibernateUtil.getSession()) {
                session.beginTransaction();
                session.save(object);
                session.getTransaction().commit();
                return object;
            } catch (HibernateException h) {
                System.err.println(h.toString());
            }
        }
        // Return null only when object is null or exception will appear
        return null;
    }

    static <T> T editObject(T object) {
        if (object != null) {
            try (Session session = HibernateUtil.getSession()) {
                session.beginTransaction();
                session.update(object);
                session.getTransaction().commit();
                return object;
            } catch (HibernateException h) {
                System.err.println(h.toString());
            }
        }
        return null;
    }

    static <T> boolean deleteObject(T object, long id){
        if (object != null){
            try (Session session = HibernateUtil.getSession()) {
                T objectToDelete = getObjectById(object, id);
                if (objectToDelete != null) {
                    session.beginTransaction();
                    session.delete(objectToDelete);
                    session.getTransaction().commit();
                    return true;
                }
            } catch (HibernateException h){
                System.err.println(h.toString());
            }
        }
        return false;
    }

}
