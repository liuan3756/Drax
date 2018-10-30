package drax.liuan.com.drax.display;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import drax.liuan.com.drax.R;

public class PersonDisplayAnimHelper {
    private static final int MAX_COUNT = 3;
    private static final long REFRESH_DELAY = 1000;
    private static final long AUTO_CLOSE_DELAY_MILLIS = 3000;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private int count = 0;
    private boolean isTransit;

    private Random random = new Random();
    private int index = 0;

    private ArrayList<Execute> executeList = new ArrayList<>();

    private MyHandler myHandler;
    private Timer timer;
    private TimerTask timerTask;

    public PersonDisplayAnimHelper(@NonNull LinearLayout linearLayout) {
        this.mLinearLayout = linearLayout;
        this.mContext = linearLayout.getContext();
        initLoadAnim();
        init();

        myHandler = new MyHandler(this);
    }

    private void initLoadAnim() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.left);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_REVERSE);
        //设置控件显示间隔时间；
        lac.setDelay(1);
        //为ListView设置LayoutAnimationController属性；
        mLinearLayout.setLayoutAnimation(lac);
    }

    private void init() {
        LayoutTransition mLayoutTransition = new LayoutTransition();
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, getAppearingAnimation());
        mLayoutTransition.setDuration(LayoutTransition.APPEARING, 200);
        mLayoutTransition.setStartDelay(LayoutTransition.APPEARING, 0);//源码中带有默认300毫秒的延时，需要移除，不然view添加效果不好！！

        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, getDisappearingAnimation());
        mLayoutTransition.setDuration(LayoutTransition.DISAPPEARING, 200);

        mLayoutTransition.setDuration(LayoutTransition.CHANGE_APPEARING, 200);
        mLayoutTransition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, 200);

        mLayoutTransition.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        mLayoutTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);//源码中带有默认300毫秒的延时，需要移除，不然view添加效果不好！！
        mLayoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                isTransit = true;
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                isTransit = false;
                if (transitionType == LayoutTransition.APPEARING || transitionType == LayoutTransition.DISAPPEARING) {
                    refreshExecuteList();
                }
            }
        });
        this.mLinearLayout.setLayoutTransition(mLayoutTransition);
        isTransit = false;
    }


    private Animator getAppearingAnimation() {
        AnimatorSet mSet = new AnimatorSet();
        mSet.playTogether(
                ObjectAnimator.ofFloat(null, "ScaleX", 0.2f, 0.6f, 1.0f),
                ObjectAnimator.ofFloat(null, "ScaleY", 0.2f, 0.6f, 1.0f),
                ObjectAnimator.ofFloat(null, "Alpha", 0.2f, 1.0f),
                ObjectAnimator.ofFloat(null, "translationX", 200, 0));
        return mSet;
    }

    private Animator getDisappearingAnimation() {
        AnimatorSet mSet = new AnimatorSet();
        mSet.playTogether(
                ObjectAnimator.ofFloat(null, "ScaleX", 1.0f, 0f),
                ObjectAnimator.ofFloat(null, "ScaleY", 1.0f, 0f),
                ObjectAnimator.ofFloat(null, "Alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(null, "translationX", 0, -200));
        return mSet;
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(MyHandler.CODE_CLOSE);
            }
        };
        timer.schedule(timerTask, AUTO_CLOSE_DELAY_MILLIS);
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }


    public void addItem(ArrayList<String> stringList) {
        resetTimer();
        for (int i = 0; i < stringList.size(); i++) {
            if (MAX_COUNT - count == 0) {
                removeAllItem();
            }
            Execute execute = new Execute();
            execute.executeType = Execute.ADD;
            execute.data = stringList.get(i);
            executeList.add(execute);
            count++;
        }


//        int currentCount = count;
//        for (int i = 0; i < stringList.size(); i++) {
//            System.out.println("@@@@ i " + i);
//            System.out.println("@@@@ currentCount " + currentCount);
//            if (currentCount != 0 && (i + currentCount) % MAX_COUNT == 0) {
//                removeAllItem();
//            }
//            Execute execute = new Execute();
//            execute.executeType = Execute.ADD;
//            execute.data = stringList.get(i);
//            executeList.add(execute);
//            count++;
//        }

        refreshExecuteList();
    }

    private void removeAllItem() {
        if (count != 0) {
            Execute execute = new Execute();
            execute.executeType = Execute.PAUSE;
            executeList.add(execute);
        }
        for (int j = 0; j < count; j++) {
            Execute execute1 = new Execute();
            execute1.executeType = Execute.REMOVE;
            execute1.removeIndex = 0;
            executeList.add(execute1);
//            count--;
        }
        count = 0;
    }

    private void refreshExecuteList() {
        if (isTransit) return;
        if (executeList.isEmpty()) {
            startTimer();
            return;
        }
//
//        if (executeList.isEmpty() || isTransit) return;
        String ddd = "";
        for (Execute execute : executeList) {
            if (execute.executeType == Execute.ADD) {
                ddd += " +";
            } else if (execute.executeType == Execute.REMOVE) {
                ddd += " -";
            } else if (execute.executeType == Execute.PAUSE) {
                ddd += " P";
            }
        }
        System.out.println("@@@@  ddd " + ddd);

        Execute execute = executeList.get(0);
        if (execute.executeType == Execute.ADD) {
            executeAddView(execute.data);
        } else if (execute.executeType == Execute.REMOVE) {
            executeRemoveView(execute.removeIndex);
        } else if (execute.executeType == Execute.PAUSE) {
            myHandler.sendEmptyMessageDelayed(MyHandler.CODE_PAUSE, REFRESH_DELAY);
        }
        executeList.remove(0);
    }

    private void executeAddView(String data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_person_display_list, null);
        ImageView imgView = view.findViewById(R.id.img_item_person_display_head);
        imgView.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        TextView textView = view.findViewById(R.id.tv_item_person_display_name);
        textView.setText(index + "");
        ImageView imageViewCheck = view.findViewById(R.id.img_item_person_display_check);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(null, "ScaleX", 0.7f, 1.5f, 1.0f),
                ObjectAnimator.ofFloat(null, "ScaleY", 0.7f, 1.5f, 1.0f));
        animatorSet.setTarget(imageViewCheck);
        animatorSet.setDuration(800);
        animatorSet.setStartDelay(200);
        mLinearLayout.addView(view);

        animatorSet.start();
        index++;

    }

    private void executeRemoveView(int index) {
        mLinearLayout.removeViewAt(index);
    }


    private class Execute {
        static final int ADD = 1;
        static final int REMOVE = 2;
        static final int PAUSE = 3;
        private int executeType;
        private String data;
        private int removeIndex;
    }

    private static class MyHandler extends Handler {
        static final int CODE_PAUSE = 1;
        static final int CODE_CLOSE = 2;
        private PersonDisplayAnimHelper personDisplayAnimHelper;

        MyHandler(PersonDisplayAnimHelper personDisplayAnimHelper) {
            WeakReference<PersonDisplayAnimHelper> personDisplayAnimHelperWeakReference = new WeakReference<>(personDisplayAnimHelper);
            this.personDisplayAnimHelper = personDisplayAnimHelperWeakReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_PAUSE:
                    personDisplayAnimHelper.refreshExecuteList();
                    break;
                case CODE_CLOSE:
                    System.out.println("@@@@ 接收到close ");
                    personDisplayAnimHelper.removeAllItem();
                    personDisplayAnimHelper.refreshExecuteList();
                    personDisplayAnimHelper.resetTimer();
                    break;
            }
        }
    }
}
