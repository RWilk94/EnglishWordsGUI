package rwilk.englishWordsGUI.dao;

import rwilk.englishWordsGUI.model.Course;

import java.util.List;

public class CourseDAO {

    public Course insertCourse(Course course) {
        return DAOMethods.saveObject(course);
    }

    public Course getCourseById(long id) {
        return DAOMethods.getObjectById(new Course(), id);
    }

    public List<Course> getCourseList() {
        return DAOMethods.getObjectsList(new Course());
    }

    public Course editCourse(Course course) {
        return DAOMethods.editObject(course);
    }

    public boolean deleteCourse(long id) {
        return DAOMethods.deleteObject(new Course(), id);
    }

}
