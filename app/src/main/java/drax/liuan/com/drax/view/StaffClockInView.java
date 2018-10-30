package drax.liuan.com.drax.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import drax.liuan.com.drax.MySlideAnimator;
import drax.liuan.com.drax.R;
import drax.liuan.com.drax.RecyclerViewAdapter;

public class StaffClockInView extends RelativeLayout {
    private Context mContext;
    private RelativeLayout relativeLayoutWrapper;
    private HexagonView hexagonView;
    private LinearLayout linearLayoutContent;
    private LinearLayout linearLayoutContentHeader;
    private RecyclerView recyclerViewStaff;

    private RecyclerViewAdapter adapter;
    private ArrayList<Person> list = new ArrayList<>();

    public StaffClockInView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initContentView();
        initHexagonView();
    }

    private void initContentView() {
        this.relativeLayoutWrapper = (RelativeLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.layout_staff_clock_in_content, null);
        LayoutParams layoutParamsRlWrapper = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.relativeLayoutWrapper.setGravity(Gravity.END);
        addView(this.relativeLayoutWrapper, layoutParamsRlWrapper);
        this.relativeLayoutWrapper.setVisibility(GONE);

        this.linearLayoutContent = this.relativeLayoutWrapper.findViewById(R.id.llContent);
        this.linearLayoutContentHeader = this.relativeLayoutWrapper.findViewById(R.id.llHeader);
        this.recyclerViewStaff = this.relativeLayoutWrapper.findViewById(R.id.recyclerViewStaff);

        this.recyclerViewStaff.setLayoutManager(new LinearLayoutManager(mContext));
        this.recyclerViewStaff.setItemAnimator(new MySlideAnimator());
        this.adapter = new RecyclerViewAdapter(mContext, list);
        this.recyclerViewStaff.setAdapter(adapter);
    }

    private void initHexagonView() {
        this.hexagonView = new HexagonView(mContext);
        LayoutParams layoutParamsHexagonView = new LayoutParams(dp2px(250), dp2px(40));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParamsHexagonView.addRule(ALIGN_PARENT_END);
        }
        layoutParamsHexagonView.addRule(ALIGN_PARENT_RIGHT);
        layoutParamsHexagonView.rightMargin = dp2px(20);
        addView(this.hexagonView, layoutParamsHexagonView);
        this.hexagonView.setOnAnimStateChangeListener(new HexagonView.OnAnimStateChangeListener() {
            @Override
            public void onSpreadAnimStart() {
                relativeLayoutWrapper.setVisibility(GONE);
            }

            @Override
            public void onSpreadAnimEnd(Point extensionLineStartPoint, Point extensionLineEndPoint) {
                startSpreadContentAnim(extensionLineStartPoint, extensionLineEndPoint);
            }

            @Override
            public void onCloseAnimStart() {
                relativeLayoutWrapper.setVisibility(GONE);
            }

            @Override
            public void onCloseAnimEnd() {

            }
        });
    }


    private void startSpreadContentAnim(Point extensionLineStartPoint, Point extensionLineEndPoint) {
        int itemVisibleWidth = extensionLineEndPoint.x - extensionLineStartPoint.x - 50;
        int itemTotalWidth = (int) (itemVisibleWidth * 2f);
        ViewGroup.LayoutParams layoutParams = relativeLayoutWrapper.getLayoutParams();
        layoutParams.width = itemTotalWidth;
        relativeLayoutWrapper.setLayoutParams(layoutParams);

        float ttrax = extensionLineStartPoint.x + hexagonView.getX() - itemVisibleWidth * 1f + 50;
        relativeLayoutWrapper.setTranslationX(ttrax);
        relativeLayoutWrapper.setTranslationY(extensionLineStartPoint.y + hexagonView.getY());


        ViewGroup.LayoutParams layoutParamsHeader = linearLayoutContentHeader.getLayoutParams();
        layoutParamsHeader.width = itemVisibleWidth;
        linearLayoutContentHeader.setLayoutParams(layoutParamsHeader);


        relativeLayoutWrapper.setVisibility(View.VISIBLE);
        adapter.setItemVisibleWidth(itemVisibleWidth);


//        float curTranslationY = llContent.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                linearLayoutContent, "translationY", -600, 0);

        animator.setDuration(900);
        animator.start();
    }

    public void startSpreadAnim() {
        hexagonView.startSpreadAnimation();
    }

    public void startCloseAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                linearLayoutContent, "translationY", 0, -600);

        animator.setDuration(900);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hexagonView.startCloseAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void addItem(Person person) {
        list.add(person);
        adapter.notifyItemInserted(0);
        if (list.size() >= 6) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
            adapter.notifyItemRemoved(lastIndex);
        }
    }
//
//    public void addItems(ArrayList<Person> newList) {
//        newList.add(position, "" + position);
//        adapter.notifyItemInserted(position);
//        if (newList.size() >= 6) {
//            int lastIndex = newList.size() - 1;
//            newList.remove(lastIndex);
//            adapter.notifyItemRemoved(lastIndex);
//        }
//
//        this.list.addAll(newList);
//    }

    public static class Person {
        public String name;
        public int time;
    }

    public int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, this.mContext.getResources().getDisplayMetrics());
    }
}
