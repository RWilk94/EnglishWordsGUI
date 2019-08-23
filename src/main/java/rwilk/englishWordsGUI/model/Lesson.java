package rwilk.englishWordsGUI.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name="sequence_lessons", sequenceName="sequence_lessons", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_lessons")
    private long id;

    @Column(name = "english_name", nullable = false)
    private String englishName;

    @Column(name = "polish_name", nullable = false)
    private String polishName;

    @Column(name = "lesson_image")
    private String lessonImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_course", nullable = false, referencedColumnName = "id")
    private Course course;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) //lesson is name of column declared in Word model.
    private List<Word> wordList = new ArrayList<>();

    public Lesson(){}

    public Lesson(String englishName, String polishName, Course course) {
        this.englishName = englishName;
        this.polishName = polishName;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPolishName() {
        return polishName;
    }

    public void setPolishName(String polishName) {
        this.polishName = polishName;
    }

    public String getLessonImage() {
        return lessonImage;
    }

    public void setLessonImage(String lessonImage) {
        this.lessonImage = lessonImage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public String toString() {
        return String.valueOf(getId()) + ". " + getPolishName() + " = " + getEnglishName();
    }
}
