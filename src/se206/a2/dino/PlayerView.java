package se206.a2.dino;

public class PlayerView extends GameObjectView {
    public PlayerView(DinoModel model) {
        super(model, model.getPlayer());
    }
}
