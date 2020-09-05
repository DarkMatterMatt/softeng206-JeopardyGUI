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

        // reset button view
        ImageView reset = new ImageView(new Image(getClass().getResourceAsStream("assets/reset.png")));
        reset.setFitHeight(32);
        reset.setPreserveRatio(true);
        reset.setSmooth(true);
        reset.setCache(true);

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

        // add background panes for each button so it detects clicks on the transparent areas
        Pane exitContainer = new Pane(exit);
        exitContainer.setOnMouseClicked(e -> Platform.exit());
        exitContainer.getStyleClass().add("btn");
        Tooltip.install(exitContainer, new Tooltip("Quit"));

        Pane resetContainer = new Pane(reset);
        resetContainer.setOnMouseClicked(e -> model.reset());
        resetContainer.getStyleClass().add("btn");
        Tooltip.install(resetContainer, new Tooltip("Reset game"));

        Pane volumeContainer = new Pane(volume);
        volumeContainer.setOnMouseClicked(e -> model.reset());
        volumeContainer.getStyleClass().add("btn");
        Tooltip.install(volumeContainer, model.isSoundEnabled() ? volumeMuteTooltip : volumeTooltip);

        _container.getStyleClass().add("taskbar");
        _container.getChildren().addAll(volumeContainer, resetContainer, exitContainer);

        // toggle mute/unmute icons
        volumeContainer.setOnMouseClicked(e -> {
            if (volume.getImage().equals(volumeImage)) {
                // sound was enabled, muting sound
                volume.setImage(volumeMuteImage);
                Tooltip.uninstall(volumeContainer, volumeMuteTooltip);
                Tooltip.install(volumeContainer, volumeTooltip);
                model.disableSound();
            }
            else {
                // sound was muted, enabling sound
                volume.setImage(volumeImage);
                Tooltip.uninstall(volumeContainer, volumeTooltip);
                Tooltip.install(volumeContainer, volumeMuteTooltip);
                model.enableSound();
            }
        });
    }

    public Pane getView() {
        return _container;
    }
}
