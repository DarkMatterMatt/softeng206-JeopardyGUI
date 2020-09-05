package se206.a2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logic & details for a single category
 */
public class Category implements Comparable<Category>, Serializable {
    private final String _name;
    private final List<Question> _questions;

    public Category(String name) {
        this(name, new ArrayList<>());
    }

    public Category(String name, List<Question> questions) {
        _name = name;
        _questions = questions;
    }

    /**
     * Add question to category
     */
    public void addQuestion(Question question) {
        if (_questions.stream().anyMatch(q -> q.getValue() == question.getValue())) {
            throw new IllegalArgumentException("Category " + _name + " already has a question of value " + question.getValue());
        }
        _questions.add(question);
        Collections.sort(_questions);
    }

    @Override
    public int compareTo(Category o) {
        return o._name.compareTo(_name);
    }

    public String getName() {
        return _name;
    }

    /**
     * Get question with matching value
     *
     * @param value the value to search for
     */
    public Question getQuestion(int value) {
        return _questions.stream()
                .filter(q -> q.getValue() == value)
                .findAny()
                .orElse(null);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(_questions);
    }

    /**
     * @return true if at least one question has not been attempted
     */
    public boolean hasUnattemptedQuestions() {
        return _questions.stream().anyMatch(q -> q.getStatus() == Question.Status.UNATTEMPTED);
    }

    /**
     * @return number of categories
     */
    public int size() {
        return _questions.size();
    }
}
