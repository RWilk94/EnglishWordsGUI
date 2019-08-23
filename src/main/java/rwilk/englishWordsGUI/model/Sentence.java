/*package rwilk.englishWordsGUI.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sentences")
public class Sentence implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "sequence_sentences", sequenceName = "sequence_sentences")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_sentences")
    private long id;

    @Column(name = "english_sentence", nullable = false)
    private String englishSentence;

    @Column(name = "polish_sentence", nullable = false)
    private String polishSentence;

    @OneToOne(mappedBy = "sentence", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Word word;

    

}*/
