package se206.a2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GameModel implements Serializable {
    private final transient GameModelDataSource _dataSource;
    private final transient GameModelPersistence _persistence;
    private List<Category> _categories;
    private transient ObjectProperty<Question> _currentQuestion = new SimpleObjectProperty<>(null);
    private transient IntegerProperty _score = new SimpleIntegerProperty();
    private transient ObjectProperty<State> _state = new SimpleObjectProperty<>(State.SELECT_QUESTION);

    public GameModel(GameModelDataSource dataSource, GameModelPersistence persistence) {
        _dataSource = dataSource;
        _persistence = persistence;
        load();
    }

    public void answerQuestion(String answer) {
        if (_state.get() != State.ANSWER_QUESTION) {
            throw new IllegalStateException("Previous state should be ANSWER_QUESTION, found " + _state.get());
        }
        boolean correct = _currentQuestion.get().checkAnswer(answer);
        _score.set(_score.get() + (correct ? 1 : -1) * _currentQuestion.get().getValue());
        _state.set(correct ? State.CORRECT_ANSWER : State.INCORRECT_ANSWER);
        save();
    }

    public void askQuestion(Question question) {
        if (_state.get() != State.SELECT_QUESTION) {
            throw new IllegalStateException("Previous state should be SELECT_QUESTION, found " + _state.get());
        }
        _currentQuestion.set(question);
        _state.set(State.ANSWER_QUESTION);
        save();
    }

    public void finishQuestion() {
        if (_state.get() != State.CORRECT_ANSWER && _state.get() != State.INCORRECT_ANSWER) {
            throw new IllegalStateException("Previous state should be CORRECT_ANSWER or INCORRECT_ANSWER, found " + _state.get());
        }
        _currentQuestion = null;
        _state.set(State.SELECT_QUESTION);
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(_categories);
    }

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

    public boolean hasUnattemptedQuestions() {
        return _categories.stream().anyMatch(Category::hasUnattemptedQuestions);
    }

    private void load() {
        if (_persistence != null) {
            GameModel old = _persistence.load();
            if (old != null) {
                _categories = old._categories;
                _currentQuestion = old._currentQuestion;
                _score.set(old._score.get());
                return;
            }
        }
        reset();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        _score = new SimpleIntegerProperty(s.readInt());
        _state = new SimpleObjectProperty<>((State) s.readObject());
        _currentQuestion = new SimpleObjectProperty<>((Question) s.readObject());
    }

    public void reset() {
        _categories = _dataSource.loadCategories();
        _currentQuestion.setValue(null);
        _score.set(0);
        _state.set(State.RESET); // trigger any RESET listeners
        _state.set(State.SELECT_QUESTION);
    }

    private void save() {
        if (_persistence != null) {
            _persistence.save(this);
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(_score.intValue());
        s.writeObject(_state.get());
        s.writeObject(_currentQuestion.get());
    }

    enum State {
        RESET,
        SELECT_QUESTION,
        ANSWER_QUESTION,
        CORRECT_ANSWER,
        INCORRECT_ANSWER,
    }
}
