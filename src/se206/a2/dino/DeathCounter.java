package se206.a2.dino;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DeathCounter {
    private final HBox _container = new HBox();
    private final Label _deathsLabel = new Label();

    public DeathCounter(DinoModel model) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("assets/skull.png")));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setFitWidth(32);

        updateDeaths(model.getDeaths());
        model.getDeathsProperty().addListener((obs, oldVal, newVal) -> updateDeaths(newVal.intValue()));

        _container.getStyleClass().add("death-counter");
        _container.getChildren().addAll(_deathsLabel, imageView);
    }

    public HBox getView() {
        return _container;
    }

    public void setContainerWidth(double width) {
        _container.setPrefWidth(width);
    }

    private void updateDeaths(int deaths) {
        _deathsLabel.setText(Integer.toString(deaths));
        _container.setVisible(deaths > 0);
    }
}
