package rwilk.englishWordsGUI.dao;

import rwilk.englishWordsGUI.model.Lesson;

import java.util.List;

public class LessonDAO {

    public Lesson insertLesson(Lesson lesson) {
        return DAOMethods.saveObject(lesson);
    }

    public Lesson getLessonById(long id) {
        return DAOMethods.getObjectById(new Lesson(), id);
    }

    public List<Lesson> getLessonList() {
        return DAOMethods.getObjectsList(new Lesson());
    }

    public boolean deleteLesson(long id) {
        return DAOMethods.deleteObject(new Lesson(), id);
    }

    public Lesson editLesson(Lesson lesson){
        return DAOMethods.editObject(lesson);
    }

}
