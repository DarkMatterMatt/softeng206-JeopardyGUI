package se206.a2;

import java.io.Serializable;

public class Question implements Comparable<Question>, Serializable {
    private final String _answer;
    private final String _question;
    private final int _value;
    private Status _status;

    public Question(int value, String question, String answer) {
        this(value, question, answer, Status.UNATTEMPTED);
    }

    public Question(int value, String question, String answer, Status status) {
        _answer = answer.trim();
        _question = question.trim();
        _value = value;
        _status = status;
    }

    public boolean checkAnswer(String answer) {
        boolean correct = _answer.equalsIgnoreCase(answer.trim());
        _status = correct ? Status.CORRECT : Status.INCORRECT;
        return correct;
    }

    @Override
    public int compareTo(Question o) {
        return o._value - _value;
    }

    public String getQuestion() {
        return _question;
    }

    public Status getStatus() {
        return _status;
    }

    public int getValue() {
        return _value;
    }

    public enum Status {
        UNATTEMPTED,
        CORRECT,
        INCORRECT,
    }
}
