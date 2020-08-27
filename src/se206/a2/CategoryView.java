package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CategoryView {
    private final VBox _container = new VBox();

    public CategoryView(GameModel model, Category category) {
        String capitalizedName = category.getName().substring(0, 1).toUpperCase() + category.getName().substring(1);
        Label nameLabel = new Label(capitalizedName);
        nameLabel.getStyleClass().add("name");

        _container.setSpacing(8);
        _container.getStyleClass().add("category");
        _container.getChildren().add(nameLabel);

        int currentHeight = 0;
        int questionHeight = 50;
        LadderCalc lb = new LadderCalc(questionHeight * category.size(), 160, 200);

        for (Question question : category.getQuestions()) {
            int topWidth = lb.getXAtY(currentHeight);
            currentHeight += questionHeight;
            int bottomWidth = lb.getXAtY(currentHeight);
            currentHeight += 8; // spacing

            QuestionView view = new QuestionView(model, question, questionHeight, topWidth, bottomWidth);
            _container.getChildren().add(view.getView());
        }
    }

    public VBox getView() {
        return _container;
    }
}
