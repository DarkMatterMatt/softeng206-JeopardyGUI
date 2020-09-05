package se206.a2;

import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * View for taskbar. Contains mute/unmute, reset & quit buttons
 */
public class TaskbarView {
    private final HBox _container = new HBox();

    public TaskbarView(GameModel model) {
        // exit button view
        ImageView exit = new ImageView(new Image(getClass().getResourceAsStream("assets/exit.png")));
        exit.setFitHeight(32);
        exit.setPreserveRatio(true);
        exit.setSmooth(true);
        exit.setCache(true);
        exit.setPickOnBounds(true);
        exit.setOnMouseClicked(e -> Platform.exit());
        exit.getStyleClass().add("btn");
        Tooltip.install(exit, new Tooltip("Quit"));

        // reset button view
        ImageView reset = new ImageView(new Image(getClass().getResourceAsStream("assets/reset.png")));
        reset.setFitHeight(32);
        reset.setPreserveRatio(true);
        reset.setSmooth(true);
        reset.setCache(true);
        reset.setPickOnBounds(true);
        reset.setOnMouseClicked(e -> model.reset());
        reset.getStyleClass().add("btn");
        Tooltip.install(reset, new Tooltip("Reset game"));

        // volume mute/unmute image
        Image volumeImage = new Image(getClass().getResourceAsStream("assets/volume.png"));
        Image volumeMuteImage = new Image(getClass().getResourceAsStream("assets/volume-mute.png"));
        Tooltip volumeTooltip = new Tooltip("Enable sound");
        Tooltip volumeMuteTooltip = new Tooltip("Disable sound");

        // volume mute/unmute view
        ImageView volume = new ImageView(model.isSoundEnabled() ? volumeImage : volumeMuteImage);
        volume.setFitHeight(32);
        volume.setPreserveRatio(true);
        volume.setSmooth(true);
        volume.setCache(true);
        volume.setPickOnBounds(true);
        volume.setOnMouseClicked(e -> model.reset());
        volume.getStyleClass().add("btn");
        Tooltip.install(volume, model.isSoundEnabled() ? volumeMuteTooltip : volumeTooltip);

        _container.getStyleClass().add("taskbar");
        _container.getChildren().addAll(volume, reset, exit);

        // toggle mute/unmute icons
        volume.setOnMouseClicked(e -> {
            if (volume.getImage().equals(volumeImage)) {
                // sound was enabled, muting sound
                volume.setImage(volumeMuteImage);
                Tooltip.uninstall(volume, volumeMuteTooltip);
                Tooltip.install(volume, volumeTooltip);
                model.disableSound();
            }
            else {
                // sound was muted, enabling sound
                volume.setImage(volumeImage);
                Tooltip.uninstall(volume, volumeTooltip);
                Tooltip.install(volume, volumeMuteTooltip);
                model.enableSound();
            }
        });
    }

    public Pane getView() {
        return _container;
    }
}
