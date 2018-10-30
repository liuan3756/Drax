package drax.liuan.com.drax.visitor;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;

public class VisitorListAnimator extends BaseItemAnimator {
    public VisitorListAnimator() {

    }

    public VisitorListAnimator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    @Override
    protected void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
//        ViewCompat.setTranslationX(holder.itemView, holder.itemView.getRootView().getWidth());
    }

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .translationX(-holder.itemView.getRootView().getWidth())
                .setDuration(getRemoveDuration())
                .setInterpolator(mInterpolator)
                .setListener(new BaseItemAnimator.DefaultRemoveVpaListener(holder))
                .setStartDelay(getRemoveDelay(holder))
                .start();
//        ViewCompat.animate(holder.itemView)
//                .alpha(0)
//                .setDuration(getRemoveDuration())
//                .setInterpolator(mInterpolator)
//                .setListener(new DefaultRemoveVpaListener(holder))
//                .setStartDelay(getRemoveDelay(holder))
//                .start();
    }

    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, -holder.itemView.getRootView().getWidth());
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .translationX(0)
                .setDuration(getAddDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultAddVpaListener(holder))
                .setStartDelay(getAddDelay(holder))
                .start();
    }
}
