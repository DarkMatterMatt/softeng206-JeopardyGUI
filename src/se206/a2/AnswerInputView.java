package se206.a2;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AnswerInputView {
    private final HBox _container = new HBox();

    public AnswerInputView(GameModel model) {
        _container.getStyleClass().add("input-container");

        TextField answerInput = new TextField();
        answerInput.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        answerInput.setOnAction(e -> model.answerQuestion(answerInput.getText()));
        HBox.setHgrow(answerInput, Priority.ALWAYS);

        ImageView submit = new ImageView(new Image(getClass().getResourceAsStream("assets/submit.png")));
        submit.setFitWidth(32);
        submit.setPreserveRatio(true);
        submit.setSmooth(true);
        submit.setCache(true);
        submit.getStyleClass().addAll("submit");

        VBox submitContainer = new VBox(submit);
        submitContainer.setOnMouseClicked(e -> model.answerQuestion(answerInput.getText()));
        submitContainer.getStyleClass().addAll("submit-container", "btn");
        submitContainer.prefWidthProperty().bind(answerInput.heightProperty());

        _container.getChildren().addAll(answerInput, submitContainer);
    }

    public HBox getView() {
        return _container;
    }
}
