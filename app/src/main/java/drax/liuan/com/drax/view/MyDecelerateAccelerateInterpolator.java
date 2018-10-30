package drax.liuan.com.drax.view;

import android.animation.TimeInterpolator;

public class MyDecelerateAccelerateInterpolator implements TimeInterpolator {
    private static final float factor = 1f;

    @Override
    public float getInterpolation(float input) {
//        return input * input;
//        return input;

        if (factor == 1f) {
            return (1 - (1 - input) * (1 - input));

        } else {
            return (float) (1 - Math.pow((1 - input), 2 * factor));

        }
    }
}
