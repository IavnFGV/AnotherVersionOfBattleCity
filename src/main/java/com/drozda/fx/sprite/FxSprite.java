package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.GameUnit;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by GFH on 18.10.2015.
 */
public abstract class FxSprite<T extends GameUnit> extends Group {

    public static Image baseImage = YabcSprite.baseImage;
    public static String NO_ANIMATION = "NO_ANIMATION";
    //    protected final List<SpriteAnimation<T>> animations = new ArrayList<>();
    //  protected final ParallelTransition allAnimations = new ParallelTransition();
    protected final T gameUnit;
    //    protected final List<ImageView> imageViews = new ArrayList<>();
    //    protected ImageView baseImageView = new ImageView(baseImage);
    protected Set<SpriteAnimation<T>> animationSet = new LinkedHashSet<>();

    public FxSprite(T gameUnit) {
        this.gameUnit = gameUnit;

        gameUnit.pauseProperty().addListener((observable, oldValue, newValue) -> {
            // if (allAnimations.get)
            if (!oldValue && newValue) {
                animationSet.forEach(Transition::pause);
            }
            if (oldValue && !newValue) {
                animationSet.forEach(Transition::play);
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
        initSprite();
    }


    protected void toCreatingState() {
        SpriteAnimation helmetAnimation = animationSet.stream().filter(tSpriteAnimation -> tSpriteAnimation
                .getAnimationId().equals("HELMET_TANK_ANIMATION")).findFirst().orElse(null);
        //helmetAnimation.stop();
        helmetAnimation.imageView.setVisible(true);
    }

    protected void toActiveState() {
        SpriteAnimation helmetAnimation = animationSet.stream().filter(tSpriteAnimation -> tSpriteAnimation
                .getAnimationId().equals("HELMET_TANK_ANIMATION")).findFirst().orElse(null);
        helmetAnimation.imageView.setVisible(false);

    }

    protected void toExplodingState() {
    }

    protected void toDeadState() {
        //allAnimations.stop();
        ((Pane) this.getParent()).getChildren().removeAll(this); // todo REPLACE AS Consumer???
    }

    /**
     * Need to be overridden in ancestors and call after preparing animationSet
     */
    protected void initSprite() {
        animationSet.stream().forEach(tSpriteAnimation -> {
            this.getChildren().add(tSpriteAnimation.imageView);
            tSpriteAnimation.imageView.toBack();
            //   allAnimations.getChildren().add(tSpriteAnimation);
        });
    }

    protected Rectangle2D nextViewport(String animationId, int index) {
        throw new RuntimeException("Something wrong! In cant be happened in properly animation hierarchy");
    }

    protected void bindImageViewToGameUnit(ImageView imageView, double dx, double dy) {
        gameUnit.boundsProperty().addListener((observable, oldValue, newValue) -> {
            imageView.xProperty().setValue(newValue.getMinX() + dx);
            imageView.yProperty().setValue(newValue.getMinY() + dy);
        });
        imageView.xProperty().setValue(gameUnit.getBounds().getMinX() + dx);
        imageView.yProperty().setValue(gameUnit.getBounds().getMinY() + dy);
    }

    public enum AnimationType {
        ANIMATION_CREATING,
        ANIMATION_ACTIVE,
        ANIMATION_EXPLODING,
        ANIMATION_HELMET
    }

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

        public SpriteAnimation(ImageView imageView) { // for case when we dont need animation at all
            this.imageView = imageView;
        }

        protected abstract String getAnimationId();

    }
}
