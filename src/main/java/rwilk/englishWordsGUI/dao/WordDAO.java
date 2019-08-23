package rwilk.englishWordsGUI.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import rwilk.englishWordsGUI.model.Course;
import rwilk.englishWordsGUI.model.Lesson;
import rwilk.englishWordsGUI.model.Word;
import rwilk.englishWordsGUI.tool.HibernateUtil;

import java.util.List;

public class WordDAO {

    public Word insertWord(Word word) {
        return DAOMethods.saveObject(word);
    }

    public Word getWordById(long id) {
        return DAOMethods.getObjectById(new Word(), id);
    }

    public List<Word> getWordList() {
        return DAOMethods.getObjectsList(new Word());
    }

    public boolean deleteWord(long id) {
        return DAOMethods.deleteObject(new Word(), id);
    }

    public Word editWord(Word word) {
        return DAOMethods.editObject(word);
    }

    public List<Word> getWordListByCourse(Course course) {
        if (course != null) {
            try (Session session = HibernateUtil.getSession()) {
                Query query = session.createQuery("FROM Word as w WHERE w.lesson.course.id =:id");
                query.setParameter("id", course.getId());
                return query.list();
            } catch (HibernateException h) {
                System.out.println(h.toString());
            }
        }
        return null;
    }

    public List<Word> getWordListByLesson(Lesson lesson) {
        if (lesson != null) {
            try (Session session = HibernateUtil.getSession()) {
                Query query = session.createQuery("FROM Word as w WHERE w.lesson.id =:id");
                query.setParameter("id", lesson.getId());
                return query.list();
            } catch (HibernateException h) {
                System.out.println(h.toString());
            }
        }
        return null;
    }

    public List<Word> getWordListByLessonAndCourse(Lesson lesson, Course course) {
        if (lesson != null && course != null) {
            try (Session session = HibernateUtil.getSession()) {
                Query query = session.createQuery("FROM Word as w WHERE w.lesson.id =:lessonId " +
                        "and w.lesson.course.id =:courseId");
                query.setParameter("lessonId", lesson.getId());
                query.setParameter("courseId", course.getId());
                return query.list();
            } catch (HibernateException h) {
                System.out.println(h.toString());
            }
        }
        return null;
    }
}
