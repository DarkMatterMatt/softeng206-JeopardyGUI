package se206.a2;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * View for input field for answer screen
 */
public class AnswerInputView {
    private final HBox _container = new HBox();

    public AnswerInputView(GameModel model) {
        _container.getStyleClass().add("input-container");

        // user input
        TextField answerInput = new TextField();
        answerInput.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        answerInput.setOnAction(e -> model.answerQuestion(answerInput.getText()));
        HBox.setHgrow(answerInput, Priority.ALWAYS);

        // submit button
        ImageView submit = new ImageView(new Image(getClass().getResourceAsStream("assets/submit.png")));
        submit.setFitWidth(32);
        submit.setPreserveRatio(true);
        submit.setSmooth(true);
        submit.setCache(true);
        submit.getStyleClass().addAll("submit");

        // square background for submit button
        VBox submitContainer = new VBox(submit);
        submitContainer.setOnMouseClicked(e -> model.answerQuestion(answerInput.getText()));
        submitContainer.getStyleClass().addAll("submit-container", "btn");
        submitContainer.prefWidthProperty().bind(answerInput.heightProperty());

        _container.getChildren().addAll(answerInput, submitContainer);

        model.getCurrentQuestionProperty().addListener((observable, oldVal, newVal) -> answerInput.clear());
    }

    public HBox getView() {
        return _container;
    }
}
