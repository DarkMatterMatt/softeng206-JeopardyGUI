package se206.a2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GameModel implements Serializable {
    private final transient GameModelDataSource _dataSource;
    private final transient BooleanProperty _needsBigUglyReset = new SimpleBooleanProperty();
    private final transient GameModelPersistence _persistence;
    private List<Category> _categories;
    private Question _currentQuestion;
    private transient IntegerProperty _score = new SimpleIntegerProperty();

    public GameModel(GameModelDataSource dataSource, GameModelPersistence persistence) {
        _dataSource = dataSource;
        _persistence = persistence;
        load();
    }

    public boolean answerQuestion(String answer) {
        if (_currentQuestion == null) {
            throw new IllegalStateException("Cannot answer question. No question is active.");
        }
        boolean correct = _currentQuestion.checkAnswer(answer);
        _score.set(_score.get() + (correct ? 1 : -1) * _currentQuestion.getValue());
        _currentQuestion = null;
        save();
        return correct;
    }

    public Question askQuestion(String category, int value) {
        Question question = getQuestion(category, value);
        if (question == null) {
            throw new IllegalArgumentException("No such question exists.");
        }
        _currentQuestion = question;
        save();
        return question;
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
        return _currentQuestion;
    }

    public Question getQuestion(String categoryName, int value) {
        Category category = getCategory(categoryName);
        if (category == null) {
            return null;
        }
        return category.getQuestion(value);
    }

    public BooleanProperty getNeedsResetProperty() {
        return _needsBigUglyReset;
    }

    public int getScore() {
        return _score.get();
    }

    public IntegerProperty getScoreProperty() {
        return _score;
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
    }

    public void reset() {
        _categories = _dataSource.loadCategories();
        _currentQuestion = null;
        _score.set(0);
        _needsBigUglyReset.setValue(true);
        _needsBigUglyReset.setValue(false);
    }

    private void save() {
        if (_persistence != null) {
            _persistence.save(this);
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(_score.intValue());
    }
}
