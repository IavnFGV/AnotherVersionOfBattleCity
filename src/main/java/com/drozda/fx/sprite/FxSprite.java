package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.GameUnit;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 18.10.2015.
 */
public abstract class FxSprite<T extends GameUnit> extends Group {

    public static Image sprite = YabcSprite.baseImage;
    protected final List<SpriteAnimation<T>> animations = new ArrayList<>();
    protected final ParallelTransition allAnimations = new ParallelTransition();
    protected final T gameUnit;
    protected final List<ImageView> imageViews = new ArrayList<>();
    protected ImageView baseImageView = new ImageView(sprite);
    BooleanBinding playBinding;

    {
        imageViews.add(baseImageView);
    }

    public FxSprite(T gameUnit) {
        this.gameUnit = gameUnit;
        gameUnit.boundsProperty().addListener((observable, oldValue, newValue) -> {
            baseImageView.xProperty().setValue(newValue.getMinX());
            baseImageView.yProperty().setValue(newValue.getMinY());
        });
        baseImageView.xProperty().setValue(gameUnit.getBounds().getMinX());
        baseImageView.yProperty().setValue(gameUnit.getBounds().getMinY());
        Transition transition = createAnimation(baseImageView);
        if (transition != null) {
            animations.add(createAnimation(baseImageView));
        }
        gameUnit.pauseProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                allAnimations.pause();
            }
            if (oldValue && !newValue) {
                allAnimations.play();
            }
        });
        gameUnit.currentStateProperty().addListener((observable, oldValue, newValue) -> {
                    switch (newValue) {

                        case CREATING:
                            toCreatingState();
                            break;
                        case ACTIVE:
                            toActiveState();
                            break;
                        case EXPLODING:
                            toExplodingState();
                            break;
                        case DEAD:
                            toDeadState();
                            break;
                    }
                }
        );
        playBinding = createPlayBinding();
    }

    protected abstract SpriteAnimation<T> createAnimation(ImageView imageView);

    protected void toCreatingState() {
    }

    protected void toActiveState() {
    }

    protected void toExplodingState() {
    }

    protected void toDeadState() {
        allAnimations.stop();
        ((Pane) this.getParent()).getChildren().removeAll(this); // todo REPLACE AS Consumer???
    }

    protected BooleanBinding createPlayBinding() {
        return Bindings.createBooleanBinding(() ->
                gameUnit.isPause(), gameUnit.pauseProperty());
    }

    protected void initSprite() {
        this.getChildren().addAll(imageViews);
        allAnimations.getChildren().addAll(animations);
    }

    protected abstract Rectangle2D nextSprite(int index);

//    public Transition getAnimation() {
//        return animation;
//    }

    protected abstract class SpriteAnimation<T extends GameUnit> extends Transition {

        protected ImageView imageView;

        public SpriteAnimation(
                Duration duration,
                ImageView imageView
        ) {
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
            this.imageView = imageView;
        }

    }
}
