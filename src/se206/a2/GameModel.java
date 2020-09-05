package se206.a2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import se206.a2.dino.DinoModel;
import se206.a2.dino.IGameComplete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Logic & data for Jeopardy!
 */
public class GameModel implements Serializable, IGameComplete {
    private final transient GameModelDataSource _dataSource;
    private final transient DinoModel _dinoModel = new DinoModel(this);
    private final transient GameModelPersistence _persistence;
    private List<Category> _categories;
    private transient ObjectProperty<Question> _currentQuestion = new SimpleObjectProperty<>(null);
    private transient IntegerProperty _score = new SimpleIntegerProperty();
    private transient ObjectProperty<State> _state = new SimpleObjectProperty<>(State.SELECT_QUESTION);
    private TextToSpeech _textToSpeech = TextToSpeech.getInstance();

    public GameModel(GameModelDataSource dataSource, GameModelPersistence persistence) {
        _dataSource = dataSource;
        _persistence = persistence;
        load();
    }

    /**
     * Submit an answer for the current question
     */
    public void answerQuestion(String answer) {
        if (_state.get() != State.ANSWER_QUESTION) {
            throw new IllegalStateException("Previous state should be ANSWER_QUESTION, found " + _state.get());
        }
        boolean correct = _currentQuestion.get().checkAnswer(answer);
        _score.set(_score.get() + (correct ? 1 : -1) * _currentQuestion.get().getValue());
        _state.set(correct ? State.CORRECT_ANSWER : State.INCORRECT_ANSWER);
        save();
        _textToSpeech.speak(correct ? "Correct!" : "Incorrect");
    }

    /**
     * Ask the specified question
     */
    public void askQuestion(Question question) {
        if (_state.get() != State.SELECT_QUESTION) {
            throw new IllegalStateException("Previous state should be SELECT_QUESTION, found " + _state.get());
        }
        _currentQuestion.set(question);
        _state.set(State.ANSWER_QUESTION);
        save();
        _textToSpeech.speak(question.getQuestion());
    }

    public void disableSound() {
        _textToSpeech.setMuted(true);
        save();
    }

    public void enableSound() {
        _textToSpeech.setMuted(false);
        save();
    }

    /**
     * Return to question selection screen
     */
    public void finishQuestion() {
        if (_state.get() != State.CORRECT_ANSWER && _state.get() != State.INCORRECT_ANSWER) {
            throw new IllegalStateException("Previous state should be CORRECT_ANSWER or INCORRECT_ANSWER, found " + _state.get());
        }
        _currentQuestion.set(null);
        _state.set(hasUnattemptedQuestions() ? State.SELECT_QUESTION : State.GAME_OVER);
        save();
    }

    @Override
    public void gameComplete() {
        // dino game finished
        reset();
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(_categories);
    }

    /**
     * Get category with specified name
     */
    public Category getCategory(String categoryName) {
        return _categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findAny()
                .orElse(null);
    }

    public Question getCurrentQuestion() {
        return _currentQuestion.get();
    }

    public ObjectProperty<Question> getCurrentQuestionProperty() {
        return _currentQuestion;
    }

    public DinoModel getDinoModel() {
        return _dinoModel;
    }

    /**
     * Get question with specified value in category
     */
    public Question getQuestion(String categoryName, int value) {
        Category category = getCategory(categoryName);
        if (category == null) {
            return null;
        }
        return category.getQuestion(value);
    }

    public int getScore() {
        return _score.get();
    }

    public IntegerProperty getScoreProperty() {
        return _score;
    }

    public State getState() {
        return _state.get();
    }

    public ObjectProperty<State> getStateProperty() {
        return _state;
    }

    /**
     * @return true if any category has at least one unattempted question
     */
    public boolean hasUnattemptedQuestions() {
        return _categories.stream().anyMatch(Category::hasUnattemptedQuestions);
    }

    public boolean isSoundEnabled() {
        return !_textToSpeech.isMuted();
    }

    /**
     * Load save data from persistence
     */
    private void load() {
        if (_persistence != null) {
            GameModel old = _persistence.load();
            if (old != null) {
                _categories = old._categories;
                _currentQuestion.set(old._currentQuestion.get());
                _score.set(old._score.get());
                _textToSpeech = old._textToSpeech;

                // don't restore dino-game state
                if (old._state.get() == State.GAME_OVER) {
                    reset();
                }
                else {
                    _state.set(old._state.get());
                }
                return;
            }
        }
        reset();
    }

    /**
     * Handle key presses depending on current state
     */
    public void onKeyPress(KeyEvent ev) {
        switch (getState()) {
            case CORRECT_ANSWER:
            case INCORRECT_ANSWER:
                finishQuestion();
                break;
            case GAME_OVER:
                _dinoModel.onKeyPress(ev);
                break;
        }
    }

    /**
     * Handle key releases depending on current state
     */
    public void onKeyRelease(KeyEvent ev) {
        switch (getState()) {
            case GAME_OVER:
                _dinoModel.onKeyRelease(ev);
                break;
        }
    }

    /**
     * Custom deserialization for Serializable
     */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        _score = new SimpleIntegerProperty(s.readInt());
        _state = new SimpleObjectProperty<>((State) s.readObject());
        _currentQuestion = new SimpleObjectProperty<>((Question) s.readObject());
    }

    /**
     * Reset game. Loads categories, zeros score, starts question selection screen
     */
    public void reset() {
        _categories = _dataSource.loadCategories();
        _currentQuestion.setValue(null);
        _score.set(0);
        _state.set(State.RESET); // trigger any RESET listeners
        _state.set(State.SELECT_QUESTION);
    }

    /**
     * Save the current game state to persistence
     */
    private void save() {
        if (_persistence != null) {
            _persistence.save(this);
        }
    }

    /**
     * Custom serialization for Serializable
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(_score.intValue());
        s.writeObject(_state.get());
        s.writeObject(_currentQuestion.get());
    }

    /**
     * Game state
     */
    enum State {
        RESET,
        SELECT_QUESTION,
        ANSWER_QUESTION,
        CORRECT_ANSWER,
        INCORRECT_ANSWER,
        GAME_OVER,
    }
}
