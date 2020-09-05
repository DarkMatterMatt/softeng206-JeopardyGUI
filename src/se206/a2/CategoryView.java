package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * View for a single category
 */
public class CategoryView {
    private final VBox _container = new VBox();

    public CategoryView(GameModel model, Category category) {
        // category name is automatically capitalized
        String capitalizedName = category.getName().substring(0, 1).toUpperCase() + category.getName().substring(1);
        Label nameLabel = new Label(capitalizedName);
        nameLabel.getStyleClass().add("name");

        _container.setSpacing(8);
        _container.getStyleClass().add("category");
        _container.getChildren().add(nameLabel);

        int currentHeight = 0;
        int questionHeight = 50;
        LadderCalc ladder = new LadderCalc(questionHeight * category.size(), 160, 200);

        // add each question to the ladder (high value questions are physically larger than low value questions
        for (Question question : category.getQuestions()) {
            int topWidth = ladder.getXAtY(currentHeight);
            currentHeight += questionHeight;
            int bottomWidth = ladder.getXAtY(currentHeight);
            currentHeight += 8; // spacing

            QuestionView view = new QuestionView(model, question, questionHeight, topWidth, bottomWidth);
            _container.getChildren().add(view.getView());
        }
    }

    public VBox getView() {
        return _container;
    }
}
