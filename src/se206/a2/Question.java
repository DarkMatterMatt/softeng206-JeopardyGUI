package se206.a2;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Question implements Comparable<Question>, Serializable {
    private final String _answer;
    private final Category _category;
    private final String _question;
    private final int _value;
    private transient ObjectProperty<Status> _status;

    public Question(Category category, int value, String question, String answer) {
        this(category, value, question, answer, Status.UNATTEMPTED);
    }

    public Question(Category category, int value, String question, String answer, Status status) {
        _answer = answer.trim();
        _category = category;
        _question = question.trim();
        _value = value;
        _status = new SimpleObjectProperty<>(status);
    }

    public boolean checkAnswer(String answer) {
        boolean correct = _answer.equalsIgnoreCase(answer.trim());
        _status.set(correct ? Status.CORRECT : Status.INCORRECT);
        return correct;
    }

    @Override
    public int compareTo(Question o) {
        return o._value - _value;
    }

    public String getAnswer() {
        return _answer;
    }

    public Category getCategory() {
        return _category;
    }

    public String getQuestion() {
        return _question;
    }

    public Status getStatus() {
        return _status.get();
    }

    public ObjectProperty<Status> getStatusProperty() {
        return _status;
    }

    public int getValue() {
        return _value;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        _status = new SimpleObjectProperty<>((Status) s.readObject());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(_status.get());
    }

    public enum Status {
        UNATTEMPTED,
        CORRECT,
        INCORRECT,
    }
}
