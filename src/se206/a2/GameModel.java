package se206.a2;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GameModel implements Serializable {
    private final transient GameModelDataSource _dataSource;
    private final transient GameModelPersistence _persistence;
    private List<Category> _categories;
    private Question _currentQuestion;
    private int _score;

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
        _score += (correct ? 1 : -1) * _currentQuestion.getValue();
        _currentQuestion = null;
        return correct;
    }

    public Question askQuestion(String category, int value) {
        Question question = getQuestion(category, value);
        if (question == null) {
            throw new IllegalArgumentException("No such question exists.");
        }
        _currentQuestion = question;
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

    public int getScore() {
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
                _score = old._score;
                return;
            }
        }
        reset();
    }

    public void reset() {
        _categories = _dataSource.loadCategories();
        _currentQuestion = null;
        _score = 0;
    }

    private void save() {
        if (_persistence != null) {
            _persistence.save(this);
        }
    }
}
