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
        ImageView exit = createButton(new Image(getClass().getResourceAsStream("assets/exit.png")));
        exit.setOnMouseClicked(e -> Platform.exit());
        Tooltip.install(exit, new Tooltip("Quit"));

        // reset button view
        ImageView reset = createButton(new Image(getClass().getResourceAsStream("assets/reset.png")));
        reset.setOnMouseClicked(e -> model.reset());
        Tooltip.install(reset, new Tooltip("Reset game"));

        // volume mute/unmute image & tooltips
        Image volumeImage = new Image(getClass().getResourceAsStream("assets/volume.png"));
        Image volumeMuteImage = new Image(getClass().getResourceAsStream("assets/volume-mute.png"));
        Tooltip volumeTooltip = new Tooltip("Enable sound");
        Tooltip volumeMuteTooltip = new Tooltip("Disable sound");

        // volume mute/unmute view
        ImageView volume = createButton(model.isSoundEnabled() ? volumeImage : volumeMuteImage);
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

    private static ImageView createButton(Image img) {
        ImageView v = new ImageView(img);
        v.setFitHeight(32);
        v.setPreserveRatio(true);
        v.setSmooth(true);
        v.setCache(true);
        v.setPickOnBounds(true);
        v.getStyleClass().add("btn");
        return v;
    }

    public Pane getView() {
        return _container;
    }
}
