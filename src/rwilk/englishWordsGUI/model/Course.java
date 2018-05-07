package rwilk.englishWordsGUI.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="sequence_courses", sequenceName="sequence_courses", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_courses")
    private long id;

    @Column(name = "english_name", nullable = false, unique = true)
    private String englishName;

    @Column(name = "polish_name", nullable = false, unique = true)
    private String polishName;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Lesson> lessonList = new ArrayList<>();

    public Course() {
    }

    public Course(long id){
        this.id = id;
    }

    public Course(String englishName, String polishName) {
        this.englishName = englishName;
        this.polishName = polishName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public String toString() {
        return String.valueOf(getId()) + ". " + getPolishName() + " = " + getEnglishName();
    }
}