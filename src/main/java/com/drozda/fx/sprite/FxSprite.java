package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.GameUnit;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            if (!oldValue && newValue) {
                animationSet.forEach(Transition::pause);
            }
            if (oldValue && !newValue) {
                animationSet.forEach(Transition::play);
            }
        });
        gameUnit.currentStateProperty().addListener((observable, oldValue, newValue) -> {
                    handleCurrentStateChange(newValue);
                }
        );
        initSprite();
    }


    protected void toCreatingState() {
        turnOnAnimation(AnimationType.ANIMATION_CREATING);
        turnOffAnimation(AnimationType.ANIMATION_ACTIVE, AnimationType.ANIMATION_HELMET, AnimationType.ANIMATION_EXPLODING);
    }

    protected void toActiveState() {
        turnOnAnimation(AnimationType.ANIMATION_ACTIVE);
        turnOffAnimation(AnimationType.ANIMATION_CREATING, AnimationType.ANIMATION_HELMET, AnimationType.ANIMATION_EXPLODING);

    }

    protected void toExplodingState() {
        turnOffAnimation(AnimationType.ANIMATION_ACTIVE, AnimationType.ANIMATION_HELMET, AnimationType.ANIMATION_CREATING);
        turnOnAnimation(AnimationType.ANIMATION_EXPLODING);
    }

    protected void toDeadState() {
        //allAnimations.stop();
        //((Pane) this.getParent()).getChildren().removeAll(this); // todo REPLACE AS Consumer???
        turnOffAnimation(AnimationType.values());
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
        handleCurrentStateChange(gameUnit.getCurrentState());
    }

    protected void handleCurrentStateChange(GameUnit.State state) {
        if (state == null) return;
        switch (state) {
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

    protected void turnOnAnimation(AnimationType... animationType) {
        for (AnimationType aType : animationType) {
            animationSet.stream()
                    .filter(tSpriteAnimation -> tSpriteAnimation.getAnimationType() == aType)
                    .forEach(tSpriteAnimation -> {
                        tSpriteAnimation.imageView.setVisible(true);
                        tSpriteAnimation.play();
                    });
        }
    }

    protected void turnOffAnimation(AnimationType... animationType) {
        for (AnimationType aType : animationType) {
            animationSet.stream()
                    .filter(tSpriteAnimation -> tSpriteAnimation.getAnimationType() == aType)
                    .forEach(tSpriteAnimation -> {
                        tSpriteAnimation.imageView.setVisible(false);
                        tSpriteAnimation.pause();
                    });
        }
    }

    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
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

        protected abstract AnimationType getAnimationType();

    }
}
