package rwilk.englishWordsGUI.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import rwilk.englishWordsGUI.controller.DikiScrapper;
import rwilk.englishWordsGUI.controller.TatoebaScrapper;
import rwilk.englishWordsGUI.controller.WiktionaryScrapper;
import rwilk.englishWordsGUI.dao.CourseDAO;
import rwilk.englishWordsGUI.dao.LessonDAO;
import rwilk.englishWordsGUI.dao.WordDAO;
import rwilk.englishWordsGUI.model.Course;
import rwilk.englishWordsGUI.model.Lesson;
import rwilk.englishWordsGUI.model.Word;
import rwilk.englishWordsGUI.util.AlertDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class HomeController implements Initializable {

    //Words table
    public TableView tableViewWords;
    public TableColumn columnWordId;
    public TableColumn columnWordEnglishWord;
    public TableColumn columnWordPolishWord;
    public TableColumn columnWordPartOfSpeech;
    public TableColumn columnWordEnglishSentence;
    public TableColumn columnWordPolishSentence;
    public TableColumn columnWordLesson;
    //Lesson table
    public TableView tableViewLessons;
    public TableColumn columnLessonId;
    public TableColumn columnLessonEnglishName;
    public TableColumn columnLessonPolishName;
    public TableColumn columnLessonCourse;
    public TableColumn columnLessonImage;
    //Course table
    public TableView tableViewCourses;
    public TableColumn columnCourseId;
    public TableColumn columnCourseEnglishName;
    public TableColumn columnCoursePolishName;
    public TableColumn columnCourseImage;
    //Status Bar
    public Text textStatus;
    //Course
    public TextField textFieldCoursePolishName;
    public TextField textFieldCourseEnglishName;
    public TextField textFieldCourseId;
    public CheckBox checkBoxConfirmDeleteCourse;
    //Lesson
    public TextField textFieldLessonPolishName;
    public TextField textFieldLessonEnglishName;
    public TextField textFieldLessonId;
    public ComboBox comboBoxLessonCourse;
    public CheckBox checkBoxConfirmDeleteLesson;
    //Word
    public CheckBox checkBoxConfirmDeleteWord;
    public TextField textFieldWordId;
    public TextField textFieldWordEnglishWord;
    public TextField textFieldWordPolishWord;
    public ComboBox comboBoxWordLesson;
    public TextField textFieldWordEnglishSentence;
    public TextField textFieldWordPolishSentence;
    public ComboBox comboBoxWordPartOfSpeech;

    //Translate
    public TextField textFieldTextToTranslate;
    @FXML
    public Button buttonTranslate;
    public ListView listViewTranslateSentence;
    public ListView listViewTranslateWord;

    //Filter
    public ComboBox comboBoxWordFilterCourse;
    public ComboBox comboBoxWordFilterLesson;

    //WebScrappers
    public RadioButton radioButtonDiki;
    public RadioButton radioButtonWiktionary;
    public RadioButton radioButtonTatoeba;
    private DikiScrapper dikiScrapper = new DikiScrapper();
    private WiktionaryScrapper wiktionaryScrapper = new WiktionaryScrapper();
    private TatoebaScrapper tatoebaScrapper = new TatoebaScrapper();

    public CheckBox checkBoxCopyToClipboard;
    public CheckBox checkBoxTranslateOnClick;

    //Language resource
    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize language resources
        this.bundle = resources;
        //Set welcome status
        setTextStatusText(bundle.getString("applicationStartedSuccessfully"));
        //Create views
        createTableView();
        fillInTableViewCourses();
        fillInTableViewLessons();
        fillInTableViewWords();
        initializeComboBoxLessonCourse();
        initializePartOfSpeechComboBox();
        initializeRadioButtons();
        initializeCheckBoxesOnStatusBar();
        buttonTranslate.setDefaultButton(true);
    }

    private void createTableView() {
        //Set columns width according to width of tableView
        columnWordId.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.05));
        columnWordEnglishWord.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.2));
        columnWordPolishWord.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.2));
        columnWordPartOfSpeech.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.15));
        columnWordEnglishSentence.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.15));
        columnWordPolishSentence.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.15));
        columnWordLesson.prefWidthProperty().bind(tableViewWords.widthProperty().multiply(0.1));

        columnLessonId.prefWidthProperty().bind(tableViewLessons.widthProperty().multiply(0.05));
        columnLessonEnglishName.prefWidthProperty().bind(tableViewLessons.widthProperty().multiply(0.3));
        columnLessonPolishName.prefWidthProperty().bind(tableViewLessons.widthProperty().multiply(0.3));
        columnLessonCourse.prefWidthProperty().bind(tableViewLessons.widthProperty().multiply(0.25));
        columnLessonImage.prefWidthProperty().bind(tableViewLessons.widthProperty().multiply(0.1));

        columnCourseId.prefWidthProperty().bind(tableViewCourses.widthProperty().multiply(0.05));
        columnCourseEnglishName.prefWidthProperty().bind(tableViewCourses.widthProperty().multiply(0.4));
        columnCoursePolishName.prefWidthProperty().bind(tableViewCourses.widthProperty().multiply(0.4));
        columnCourseImage.prefWidthProperty().bind(tableViewCourses.widthProperty().multiply(0.15));
    }

    private void initializePartOfSpeechComboBox() {
        List<String> partOfSpeechList = new ArrayList<>();
        partOfSpeechList.add("Noun = Rzeczownik");
        partOfSpeechList.add("Verb = Czasownik");
        partOfSpeechList.add("Adjective = Przymiotnik");
        partOfSpeechList.add("Other = Inny");
        partOfSpeechList.add("Sentence = Zdanie");

        comboBoxWordPartOfSpeech.setItems(FXCollections.observableArrayList(partOfSpeechList));
    }

    private void fillInTableViewCourses() {
        tableViewCourses.setItems(FXCollections.observableArrayList(new CourseDAO().getCourseList()));
    }

    private void fillInTableViewLessons() {
        tableViewLessons.setItems(FXCollections.observableArrayList(new LessonDAO().getLessonList()));
    }

    private void fillInTableViewWords() {
        if (comboBoxWordFilterCourse.getSelectionModel().getSelectedItem() == null
                && comboBoxWordFilterLesson.getSelectionModel().getSelectedItem() == null) {
            tableViewWords.setItems(FXCollections.observableArrayList(new WordDAO().getWordList()));
        } else if (comboBoxWordFilterLesson.getSelectionModel().getSelectedItem() == null) {
            tableViewWords.setItems(FXCollections.observableArrayList(
                    new WordDAO().getWordListByCourse((Course) comboBoxWordFilterCourse.getSelectionModel().getSelectedItem())));
        } else if (comboBoxWordFilterCourse.getSelectionModel().getSelectedItem() == null) {
            tableViewWords.setItems(FXCollections.observableArrayList(
                    new WordDAO().getWordListByLesson((Lesson) comboBoxWordFilterLesson.getSelectionModel().getSelectedItem())));
        } else {
            tableViewWords.setItems(FXCollections.observableArrayList(
                    new WordDAO().getWordListByLessonAndCourse(
                            (Lesson) comboBoxWordFilterLesson.getSelectionModel().getSelectedItem(),
                            (Course) comboBoxWordFilterCourse.getSelectionModel().getSelectedItem())
            ));
        }
    }

    private void refreshTableViewCourses() {
        fillInTableViewCourses();
    }

    private void refreshTableViewLessons() {
        fillInTableViewLessons();
    }

    private void refreshTableViewWords() {
        fillInTableViewWords();
    }

    private void initializeComboBoxLessonCourse() {
        comboBoxLessonCourse.getItems().clear();
        comboBoxLessonCourse.getItems().addAll(new CourseDAO().getCourseList());
    }

    private void initializeComboBoxWordLesson() {
        comboBoxWordLesson.getItems().clear();
        comboBoxWordLesson.getItems().addAll(new LessonDAO().getLessonList());

        comboBoxWordFilterLesson.getItems().clear();
        comboBoxWordFilterLesson.getItems().addAll(new LessonDAO().getLessonList());

        comboBoxWordFilterCourse.getItems().clear();
        comboBoxWordFilterCourse.getItems().addAll(new CourseDAO().getCourseList());
    }

    private void initializeRadioButtons() {
        ToggleGroup group = new ToggleGroup();
        radioButtonDiki.setToggleGroup(group);
        radioButtonWiktionary.setToggleGroup(group);
        radioButtonTatoeba.setToggleGroup(group);
        radioButtonDiki.setSelected(true);
    }

    private void initializeCheckBoxesOnStatusBar() {
        checkBoxTranslateOnClick.selectedProperty().setValue(true);
        checkBoxCopyToClipboard.selectedProperty().setValue(true);
    }

    @SuppressWarnings("unused")
    public void menuItemAboutOnAction(ActionEvent event) {
        System.out.println("About");
    }

    @SuppressWarnings("unused")
    public void menuItemCloseOnAction(ActionEvent event) {
        Platform.exit();
    }

    @SuppressWarnings("unused")
    public void buttonAddCourseOnAction(ActionEvent event) {
        if (isNotEmptyOrWhitespaceCourseTextFields()) {
            Course course = new Course(
                    textFieldCourseEnglishName.getText(),
                    textFieldCoursePolishName.getText()
            );
            course = new CourseDAO().insertCourse(course);
            if (course != null) {
                //Set status text and all parameters of inserted course
                setTextStatusText(bundle.getString("courseAddedSuccessfully"));
                setCourseTextFields(course);
                refreshTableViewCourses();
            } else {
                setTextStatusText(bundle.getString("errorWhileAddingCourse"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileAddingCourse"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileAddingCourse"),
                    bundle.getString("emptyCourseName"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonAddLessonOnAction(ActionEvent event) {
        if (isNotEmptyOrWhitespaceLessonTextFields()
                && comboBoxLessonCourse.getSelectionModel().getSelectedItem() != null) {
            // && comboBoxLessonCourse.getSelectionModel().getSelectedItem().getValue() != null) {
            Lesson lesson = new Lesson(
                    textFieldLessonEnglishName.getText(),
                    textFieldLessonPolishName.getText(),
                    (Course) comboBoxLessonCourse.getSelectionModel().getSelectedItem()//.getValue()
            );

            lesson = new LessonDAO().insertLesson(lesson);
            if (lesson != null) {
                setTextStatusText(bundle.getString("lessonAddedSuccessfully"));
                setLessonTextFields(lesson);
                refreshTableViewLessons();
            } else {
                setTextStatusText(bundle.getString("errorWhileAddingLesson"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileAddingLesson"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileAddingLesson"),
                    bundle.getString("emptyLessonName"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonAddWordOnAction(ActionEvent event) {
        if (isNotEmptyOrWhitespaceWordTextFields()
                && comboBoxWordPartOfSpeech.getSelectionModel().getSelectedItem() != null
                && comboBoxWordLesson.getSelectionModel().getSelectedItem() != null) {

            String partOfSpeech = (String) comboBoxWordPartOfSpeech.getSelectionModel().getSelectedItem();
            // int index = partOfSpeech.indexOf("=");
            // partOfSpeech = partOfSpeech.substring(0, index);

            Word word;
            if (isNotEmptyOrWhitespaceWordExampleSentenceTextFields()) {
                word = new Word(
                        (Lesson) comboBoxWordLesson.getSelectionModel().getSelectedItem(),
                        textFieldWordPolishWord.getText(),
                        textFieldWordEnglishWord.getText(),
                        partOfSpeech,
                        null,
                        textFieldWordEnglishSentence.getText(),
                        textFieldWordPolishSentence.getText()
                );
            } else {
                word = new Word(
                        (Lesson) comboBoxWordLesson.getSelectionModel().getSelectedItem(),
                        textFieldWordPolishWord.getText(),
                        textFieldWordEnglishWord.getText(),
                        partOfSpeech,
                        null,
                        null,
                        null
                );
            }
            word = new WordDAO().insertWord(word);
            if (word != null) {
                setTextStatusText(bundle.getString("wordAddedSuccessfully"));
                setWordTextFields(word);
                refreshTableViewWords();
            } else {
                setTextStatusText(bundle.getString("errorWhileAddingWord"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileAddingWord"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileAddingWord"),
                    bundle.getString("emptyWordName"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonEditCourseOnAction(ActionEvent event) {
        if (!textFieldCourseId.getText().isEmpty()) {
            if (isNotEmptyOrWhitespaceCourseTextFields()) {
                Course course = new CourseDAO().getCourseById(Long.valueOf(textFieldCourseId.getText()));
                course.setEnglishName(textFieldCourseEnglishName.getText());
                course.setPolishName(textFieldCoursePolishName.getText());
                //Edit course in database
                course = new CourseDAO().editCourse(course);
                if (course != null) {
                    setTextStatusText(bundle.getString("courseEditingSuccessfully"));
                    setCourseTextFields(course);
                    refreshTableViewCourses();
                } else {
                    setTextStatusText(bundle.getString("errorWhileEditingCourse"));
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileEditingCourse"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingCourse"),
                        bundle.getString("emptyCourseName"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileEditingCourse"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingCourse"),
                    bundle.getString("emptyCourseIdTextField"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonEditLessonOnAction(ActionEvent event) {
        if (!textFieldLessonId.getText().isEmpty()) {
            if (isNotEmptyOrWhitespaceLessonTextFields()) {
                Lesson lesson = new LessonDAO().getLessonById(Long.valueOf(textFieldLessonId.getText()));
                lesson.setEnglishName(textFieldLessonEnglishName.getText());
                lesson.setPolishName(textFieldLessonPolishName.getText());
                lesson.setCourse((Course) comboBoxLessonCourse.getSelectionModel().getSelectedItem());
                lesson = new LessonDAO().editLesson(lesson);
                if (lesson != null) {
                    setTextStatusText(bundle.getString("lessonEditingSuccessfully"));
                    setLessonTextFields(lesson);
                    refreshTableViewLessons();
                } else {
                    setTextStatusText(bundle.getString("errorWhileEditingLesson"));
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileEditingLesson"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingLesson"),
                        bundle.getString("emptyLessonName"));
            }
        } else {
            setTextStatusText(bundle.getString("emptyLessonIdTextField"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingLesson"),
                    bundle.getString("emptyLessonIdTextField"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonEditWordOnAction(ActionEvent event) {
        if (!textFieldWordId.getText().isEmpty()) {
            if (isNotEmptyOrWhitespaceWordTextFields()) {
                Word word = new WordDAO().getWordById(Long.valueOf(textFieldWordId.getText()));
                word.setEnglishWord(textFieldWordEnglishWord.getText());
                word.setPolishWord(textFieldWordPolishWord.getText());
                word.setLesson((Lesson) comboBoxWordLesson.getSelectionModel().getSelectedItem());
                if (isNotEmptyOrWhitespaceWordExampleSentenceTextFields()) {
                    word.setEnglishSentence(textFieldWordEnglishSentence.getText());
                    word.setPolishSentence(textFieldWordPolishSentence.getText());
                }

                String partOfSpeech = (String) comboBoxWordPartOfSpeech.getSelectionModel().getSelectedItem();
                int index = partOfSpeech.indexOf("=");
                partOfSpeech = partOfSpeech.substring(0, index);
                word.setPartOfSpeech(partOfSpeech);

                word = new WordDAO().editWord(word);
                if (word != null) {
                    setTextStatusText(bundle.getString("wordEditingSuccessfully"));
                    setWordTextFields(word);
                    refreshTableViewWords();
                } else {
                    setTextStatusText(bundle.getString("errorWhileEditingWord"));
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileEditingWord"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingWord"),
                        bundle.getString("emptyWordName"));
            }
        } else {
            setTextStatusText(bundle.getString("emptyWordIdTextField"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileEditingWord"),
                    bundle.getString("emptyWordIdTextField"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonDeleteCourseOnAction(ActionEvent event) {
        if (!textFieldCourseId.getText().isEmpty()) {
            Course course = new CourseDAO().getCourseById(Long.valueOf(textFieldCourseId.getText()));
            if (course != null) {
                if (checkBoxConfirmDeleteCourse.selectedProperty().get()) {
                    String contentText = "Id: " + String.valueOf(course.getId()) + "\n"
                            + "Polish Name: " + course.getPolishName() + "\n"
                            + "English Name: " + course.getEnglishName() + "\n"
                            + bundle.getString("areYouSure");

                    boolean result = AlertDialog.showConfirmationDialog(bundle.getString("deleteCourseTitle"),
                            contentText);
                    if (result) {
                        deleteCourse(course.getId());
                    } else {
                        setTextStatusText(bundle.getString("deletingCourseCanceledByUser"));
                    }
                } else {
                    //deleting without confirm
                    deleteCourse(course.getId());
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileDeletingCourse"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingCourse"),
                        bundle.getString("courseNotFound"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingCourse"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingCourse"),
                    bundle.getString("emptyCourseIdTextField"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonDeleteLessonOnAction(ActionEvent event) {
        if (!textFieldLessonId.getText().isEmpty()) {
            Lesson lesson = new LessonDAO().getLessonById(Long.valueOf(textFieldLessonId.getText()));
            if (lesson != null) {
                if (checkBoxConfirmDeleteLesson.selectedProperty().get()) {
                    String contentText = "Id: " + String.valueOf(lesson.getId()) + "\n"
                            + "Polish Name: " + lesson.getPolishName() + "\n"
                            + "English Name: " + lesson.getEnglishName() + "\n"
                            + bundle.getString("areYouSure");
                    boolean result = AlertDialog.showConfirmationDialog(bundle.getString("deleteLessonTitle"),
                            contentText);
                    if (result) {
                        deleteLesson(lesson.getId());
                    } else {
                        setTextStatusText(bundle.getString("deletingLessonCanceledByUser"));
                    }
                } else {
                    //deleting without confirm
                    deleteLesson(lesson.getId());
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileDeletingLesson"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingLesson"),
                        bundle.getString("lessonNotFound"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingLesson"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingLesson"),
                    bundle.getString("emptyLessonIdTextField"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonDeleteWordOnAction(ActionEvent event) {
        if (!textFieldWordId.getText().isEmpty()) {
            Word word = new WordDAO().getWordById(Long.valueOf(textFieldWordId.getText()));
            if (word != null) {
                if (checkBoxConfirmDeleteWord.selectedProperty().get()) {
                    String contentText = "Id: " + String.valueOf(word.getId()) + "\n"
                            + "Polish Word: " + word.getPolishWord() + "\n"
                            + "English Word: " + word.getEnglishWord() + "\n"
                            + bundle.getString("areYouSure");
                    boolean result = AlertDialog.showConfirmationDialog(bundle.getString("deleteWordTitle"),
                            contentText);
                    if (result) {
                        deleteWord(word.getId());
                    } else {
                        setTextStatusText(bundle.getString("deletingWordCanceledByUser"));
                    }
                } else {
                    //deleting without confirm
                    deleteWord(word.getId());
                }
            } else {
                setTextStatusText(bundle.getString("errorWhileDeletingWord"));
                AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingWord"),
                        bundle.getString("wordNotFound"));
            }
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingWord"));
            AlertDialog.showErrorAlert(bundle.getString("errorWhileDeletingWord"),
                    bundle.getString("emptyWordIdTextField"));
        }
    }

    private void deleteCourse(long courseId) {
        if (new CourseDAO().deleteCourse(courseId)) {
            setTextStatusText(bundle.getString("courseDeletedSuccessfully"));
            buttonClearCourseOnAction(new ActionEvent());
            refreshTableViewCourses();
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingCourse"));
            AlertDialog.showErrorAlert(bundle.getString("deleteCourseTitle"),
                    bundle.getString("errorWhileDeletingCourse"));
        }
    }

    private void deleteLesson(long lessonId) {
        if (new LessonDAO().deleteLesson(lessonId)) {
            setTextStatusText(bundle.getString("lessonDeletedSuccessfully"));
            buttonClearLessonOnAction(null);
            refreshTableViewLessons();
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingLesson"));
            AlertDialog.showErrorAlert(bundle.getString("deleteLessonTitle"),
                    bundle.getString("errorWhileDeletingLesson"));
        }
    }

    private void deleteWord(long wordId) {
        if (new WordDAO().deleteWord(wordId)) {
            setTextStatusText(bundle.getString("wordDeletedSuccessfully"));
            buttonClearWordOnAction(null);
            refreshTableViewWords();
        } else {
            setTextStatusText(bundle.getString("errorWhileDeletingWord"));
            AlertDialog.showErrorAlert(bundle.getString("deleteWordTitle"),
                    bundle.getString("errorWhileDeletingWord"));
        }
    }

    @SuppressWarnings("unused")
    public void buttonClearCourseOnAction(ActionEvent event) {
        textFieldCourseId.clear();
        textFieldCourseEnglishName.clear();
        textFieldCoursePolishName.clear();
    }

    @SuppressWarnings("unused")
    public void buttonClearLessonOnAction(ActionEvent event) {
        comboBoxLessonCourse.getSelectionModel().select(null);
        textFieldLessonEnglishName.clear();
        textFieldLessonPolishName.clear();
        textFieldLessonId.clear();
    }

    @SuppressWarnings("unused")
    public void buttonClearWordOnAction(ActionEvent event) {
        textFieldWordId.clear();
        textFieldWordEnglishWord.clear();
        textFieldWordPolishWord.clear();
        comboBoxWordLesson.getSelectionModel().select(null);
        textFieldWordEnglishSentence.clear();
        textFieldWordPolishSentence.clear();
        comboBoxWordPartOfSpeech.getSelectionModel().select(null);
    }

    @SuppressWarnings("unused")
    public void tableViewCoursesOnMouseClicked(MouseEvent mouseEvent) {
        Course selectedCourse = (Course) tableViewCourses.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            setCourseTextFields(selectedCourse);
        }
    }

    @SuppressWarnings("unused")
    public void tableViewLessonsOnMouseClicked(MouseEvent mouseEvent) {
        Lesson selectedLesson = (Lesson) tableViewLessons.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            setLessonTextFields(selectedLesson);
        }
    }

    @SuppressWarnings("unused")
    public void tableViewWordsOnMouseClicked(MouseEvent mouseEvent) {
        Word selectedWord = (Word) tableViewWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            setWordTextFields(selectedWord);

            if (checkBoxTranslateOnClick.selectedProperty().get()) {
                textFieldTextToTranslate.setText(selectedWord.getEnglishWord());
                getTranslate();
            }
        }
    }

    private void setCourseTextFields(Course course) {
        textFieldCourseId.setText(String.valueOf(course.getId()));
        textFieldCourseEnglishName.setText(course.getEnglishName());
        textFieldCoursePolishName.setText(course.getPolishName());
    }

    private void setLessonTextFields(Lesson lesson) {
        textFieldLessonId.setText(String.valueOf(lesson.getId()));
        textFieldLessonPolishName.setText(lesson.getPolishName());
        textFieldLessonEnglishName.setText(lesson.getEnglishName());
        comboBoxLessonCourse.getSelectionModel().select(lesson.getCourse()/*getCourseKeyValuePair(lesson.getCourse())*/);
    }

    private void setWordTextFields(Word word) {
        textFieldWordId.setText(String.valueOf(word.getId()));
        textFieldWordEnglishSentence.setText(word.getEnglishSentence());
        textFieldWordPolishSentence.setText(word.getPolishSentence());
        textFieldWordPolishWord.setText(word.getPolishWord());
        textFieldWordEnglishWord.setText(word.getEnglishWord());
        comboBoxWordLesson.getSelectionModel().select(word.getLesson());
        comboBoxWordPartOfSpeech.getSelectionModel().select(word.getPartOfSpeech());
    }

    private void setTextStatusText(String text) {
        textStatus.setText(text);
    }

    private boolean isNotEmptyOrWhitespaceCourseTextFields() {
        return !textFieldCoursePolishName.getText().isEmpty() && !textFieldCourseEnglishName.getText().isEmpty()
                && !textFieldCoursePolishName.getText().trim().isEmpty()
                && !textFieldCourseEnglishName.getText().trim().isEmpty();
    }

    private boolean isNotEmptyOrWhitespaceLessonTextFields() {
        return !textFieldLessonPolishName.getText().isEmpty() && !textFieldLessonEnglishName.getText().isEmpty()
                && !textFieldLessonPolishName.getText().trim().isEmpty()
                && !textFieldLessonEnglishName.getText().trim().isEmpty();
    }

    private boolean isNotEmptyOrWhitespaceWordTextFields() {
        return !textFieldWordEnglishWord.getText().isEmpty() && !textFieldWordEnglishWord.getText().isEmpty()
                && !textFieldWordEnglishWord.getText().trim().isEmpty()
                && !textFieldWordEnglishWord.getText().trim().isEmpty();
    }

    private boolean isNotEmptyOrWhitespaceWordExampleSentenceTextFields() {
        return !textFieldWordPolishSentence.getText().isEmpty() && !textFieldWordEnglishSentence.getText().isEmpty()
                && !textFieldWordPolishSentence.getText().trim().isEmpty()
                && !textFieldWordEnglishSentence.getText().trim().isEmpty();
    }

    @SuppressWarnings("unused")
    public void checkBoxConfirmDeleteCourseOnAction(ActionEvent event) {
        if (checkBoxConfirmDeleteCourse.selectedProperty().get()) {
            checkBoxConfirmDeleteCourse.selectedProperty().setValue(true);
        } else {
            checkBoxConfirmDeleteCourse.selectedProperty().setValue(false);
        }
    }

    @SuppressWarnings("unused")
    public void checkBoxConfirmDeleteLessonOnAction(ActionEvent event) {
        if (checkBoxConfirmDeleteLesson.selectedProperty().get()) {
            checkBoxConfirmDeleteLesson.selectedProperty().setValue(true);
        } else {
            checkBoxConfirmDeleteLesson.selectedProperty().setValue(false);
        }
    }

    @SuppressWarnings("unused")
    public void checkBoxConfirmDeleteWordOnAction(ActionEvent event) {
        if (checkBoxConfirmDeleteWord.selectedProperty().get()) {
            checkBoxConfirmDeleteWord.selectedProperty().setValue(true);
        } else {
            checkBoxConfirmDeleteWord.selectedProperty().setValue(false);
        }
    }

    public void tabWordsOnSelectionChanged(Event event) {
        String tabText = ((Tab) event.getSource()).getText();
        if (tabText.equals("Words")) {
            refreshWordsTab();
        }
    }

    private void refreshWordsTab() {
        refreshTableViewWords();
        initializeComboBoxWordLesson();
    }

    public void tabLessonsOnSelectionChanged(Event event) {
        String tabText = ((Tab) event.getSource()).getText();
        if (tabText.equals("Lessons")) {
            refreshLessonsTab();
        }
    }

    private void refreshLessonsTab() {
        initializeComboBoxLessonCourse();
        refreshTableViewLessons();
    }

    public void tabCoursesOnSelectionChanged(Event event) {
        String tabText = ((Tab) event.getSource()).getText();
        if (tabText.equals("Courses")) {
            refreshTableViewCourses();
        }
    }

    @SuppressWarnings("unused")
    public void buttonTranslateOnMouseClick(MouseEvent mouseEvent) {
        textFieldWordPolishWord.clear();
        textFieldWordEnglishWord.clear();
        textFieldWordEnglishSentence.clear();
        textFieldWordPolishSentence.clear();
        getTranslate();
    }

    private void getTranslate() {
        setTextStatusText(bundle.getString("searchingForTranslations"));

        if (!textFieldTextToTranslate.getText().isEmpty()) {
            dikiScrapper.webScraps(textFieldTextToTranslate.getText());
            wiktionaryScrapper.webScraps(textFieldTextToTranslate.getText());
            new Thread(() -> tatoebaScrapper.webScrap(textFieldTextToTranslate.getText())).start();

//            try {
//                dikiScrapper.webScraps(textFieldTextToTranslate.getText());
//            } catch (Exception e) {
//                e.printStackTrace();
//                setTextStatusText(textStatus.getText() + " | " + bundle.getString("translateError") + " in Diki.");
//            }
//
//            try {
//                wiktionaryScrapper.webScraps(textFieldTextToTranslate.getText());
//            } catch (Exception e) {
//                e.printStackTrace();
//                setTextStatusText(textStatus.getText() + " | " + bundle.getString("translateError") + " in Wiktionary.");
//            }
//
//            try {
//                tatoebaScrapper.webScrap(textFieldTextToTranslate.getText());
//            } catch ()

            if (radioButtonDiki.isSelected()) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(dikiScrapper.getTranslationsList()));
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(dikiScrapper.getExampleSentenceList()));
            } else if (radioButtonWiktionary.isSelected()) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(wiktionaryScrapper.getTranslationsList()));
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(wiktionaryScrapper.getExampleSentenceList()));
            } else if (radioButtonTatoeba.isSelected()) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(dikiScrapper.getTranslationsList())); // TODO check it
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(tatoebaScrapper.getExampleSentenceList()));
            }
        }
        setTextStatusText(textStatus.getText() + " | " + bundle.getString("translationsReady"));
    }

    @SuppressWarnings("unused")
    public void listViewTranslateSentenceOnMouseClicked(MouseEvent mouseEvent) {
        String selectedItem = (String) listViewTranslateSentence.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String englishTranslate = "";
            String polishTranslate = "";
            if (radioButtonDiki.isSelected() || radioButtonTatoeba.isSelected()) {
                int index = selectedItem.indexOf("(");
                int lastIndex = selectedItem.lastIndexOf(")");
                polishTranslate = selectedItem.substring(index + 1, lastIndex);
                englishTranslate = selectedItem.substring(0, index);
            } else if (radioButtonWiktionary.isSelected()) {
                int indexBracket = selectedItem.indexOf(")");
                int index = selectedItem.indexOf("→");
                englishTranslate = selectedItem.substring(indexBracket + 1, index).trim();
                polishTranslate = selectedItem.substring(index + 1).trim();
            }
            textFieldWordPolishSentence.setText(polishTranslate);
            textFieldWordEnglishSentence.setText(englishTranslate);
        }
    }

    @SuppressWarnings("unused")
    public void listViewTranslateWordOnMouseClicked(MouseEvent mouseEvent) {
        String selectedItem = (String) listViewTranslateWord.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (!setPartOfSpeech(selectedItem)) {
                if (radioButtonDiki.isSelected() || radioButtonTatoeba.isSelected()) {
                    textFieldWordEnglishWord.setText(textFieldTextToTranslate.getText());
                    textFieldWordPolishWord.setText(selectedItem);
                } else if (radioButtonWiktionary.isSelected()) {
                    textFieldWordEnglishWord.setText(textFieldTextToTranslate.getText());
                    int index = selectedItem.indexOf(")");
                    int indexDot = selectedItem.lastIndexOf(".");
                    if (indexDot != -1 && indexDot != 2) {
                        textFieldWordPolishWord.setText(selectedItem.substring(indexDot + 1).trim());
                    } else {
                        textFieldWordPolishWord.setText(selectedItem.substring(index + 1).trim());
                    }
                }
            }
            if (checkBoxCopyToClipboard.selectedProperty().get()) {
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                Clipboard.getSystemClipboard().setContent(content);
            }
        }
    }

    private boolean setPartOfSpeech(String word) {
        if (radioButtonDiki.isSelected()) {
            switch (word) {
                case "[rzeczownik]":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(0);
                    break;
                case "[czasownik]":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(1);
                    break;
                case "[przymiotnik]":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(2);
                    break;
                default:
                    return false;
            }
        } else if (radioButtonWiktionary.isSelected()) {
            switch (word) {
                case "rzeczownik":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(0);
                    break;
                case "czasownik":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(1);
                    break;
                case "przymiotnik":
                    comboBoxWordPartOfSpeech.getSelectionModel().select(2);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unused")
    public void buttonClearFilterOnMouseClicked(MouseEvent mouseEvent) {
        comboBoxWordFilterLesson.getSelectionModel().select(null);
        comboBoxWordFilterCourse.getSelectionModel().select(null);
    }

    @SuppressWarnings("unused")
    public void comboBoxWordFilterCourseOnAction(ActionEvent event) {
        fillInTableViewWords();
    }

    @SuppressWarnings("unused")
    public void comboBoxWordFilterLessonOnAction(ActionEvent event) {
        fillInTableViewWords();
    }

    @SuppressWarnings("unused")
    public void radioButtonDikiOnAction(ActionEvent event) {
        if (radioButtonDiki.isSelected()) {
            if (dikiScrapper != null && dikiScrapper.getExampleSentenceList() != null
                    && dikiScrapper.getTranslationsList() != null) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(dikiScrapper.getTranslationsList()));
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(dikiScrapper.getExampleSentenceList()));
            }
        }
    }

    @SuppressWarnings("unused")
    public void radioButtonWiktionaryOnAction(ActionEvent event) {
        if (radioButtonWiktionary.isSelected()) {
            if (wiktionaryScrapper != null && wiktionaryScrapper.getExampleSentenceList() != null
                    && wiktionaryScrapper.getTranslationsList() != null) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(wiktionaryScrapper.getTranslationsList()));
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(wiktionaryScrapper.getExampleSentenceList()));
            }
        }
    }

    @SuppressWarnings("unused")
    public void radioButtonTatoebaOnAction(ActionEvent event) {
        if (radioButtonTatoeba.isSelected()) {
            if (radioButtonTatoeba != null && tatoebaScrapper.getExampleSentenceList() != null
                && dikiScrapper.getTranslationsList() != null) {
                listViewTranslateWord.setItems(FXCollections.observableArrayList(dikiScrapper.getTranslationsList()));
                listViewTranslateSentence.setItems(FXCollections.observableArrayList(tatoebaScrapper.getExampleSentenceList()));
            }
        }
    }

    @SuppressWarnings("unused")
    public void buttonTranslateOnAction(ActionEvent event) {
        buttonTranslateOnMouseClick(null);
    }
}
