package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.GameUnit;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 18.10.2015.
 */
public abstract class FxSprite<T extends GameUnit> extends ImageView {

    public static Image sprite = YabcSprite.baseImage;
    protected final SpriteAnimation<T> animation;
    protected final T gameUnit;

    BooleanBinding playBinding;

    public FxSprite(T gameUnit) {
        super(sprite);
        this.gameUnit = gameUnit;
        this.animation = createAnimation();
        playBinding = createPlayBinding();
//        playBinding.addListener((observable, oldValue, newValue) -> {
//                    if (animation.isEmbedded()) return;
//                    if ((oldValue == false) && (newValue == true)) {
//                        animation.play();
//                    } else {
//                        animation.pause();
//                    }
//                }
//        );

    }

    protected abstract SpriteAnimation<T> createAnimation();

    protected BooleanBinding createPlayBinding() {
        return Bindings.createBooleanBinding(() ->
                gameUnit.isPause(), gameUnit.pauseProperty());
    }

    protected abstract Rectangle2D nextSprite(int index);

    public Transition getAnimation() {
        return animation;
    }

    protected abstract class SpriteAnimation<T extends GameUnit> extends Transition {

        private final int count;
        protected int lastIndex;

        public SpriteAnimation(
                Duration duration,
                int count,
                int cycleCount) {
            this.count = count;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
            setCycleCount(cycleCount);
        }

        public boolean isEmbedded() {
            return parentProperty().get() != null;// todo check
        }

        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                FxSprite.this.setViewport(nextSprite(index));
                lastIndex = index;
            }
        }
    }
}
