package rwilk.englishWordsGUI.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "words")
public class Word implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name="sequence_words", sequenceName="sequence_words", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_words")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_lesson", nullable = false)
    private Lesson lesson;

    @Column(name = "polish_word", nullable = false)
    private String polishWord;

    @Column(name = "english_word", nullable = false)
    private String englishWord;

    @Column(name = "part_of_speech")
    private String partOfSpeech;

    @Column(name = "article")
    private String article;

    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_sentence", nullable = true, referencedColumnName = "id")
    private Sentence sentence;*/

    @Column(name = "english_sentence")
    private String englishSentence;

    @Column(name = "polish_sentence")
    private String polishSentence;

    public Word (){}

    public Word(Lesson lesson, String polishWord, String englishWord, String partOfSpeech, String article, String englishSentence, String polishSentence) {
        this.lesson = lesson;
        this.polishWord = polishWord;
        this.englishWord = englishWord;
        this.partOfSpeech = partOfSpeech;
        this.article = article;
        this.englishSentence = englishSentence;
        this.polishSentence = polishSentence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getPolishWord() {
        return polishWord;
    }

    public void setPolishWord(String polishWord) {
        this.polishWord = polishWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getEnglishSentence() {
        return englishSentence;
    }

    public void setEnglishSentence(String englishSentence) {
        this.englishSentence = englishSentence;
    }

    public String getPolishSentence() {
        return polishSentence;
    }

    public void setPolishSentence(String polishSentence) {
        this.polishSentence = polishSentence;
    }

    @Override
    public String toString() {
        return String.valueOf(getId()) + ". " + getPolishWord() + " = " + getEnglishWord();
    }
}