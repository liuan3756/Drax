package drax.liuan.com.drax;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import drax.liuan.com.drax.display.PersonDisplayAnimHelper;
import drax.liuan.com.drax.view.StaffClockInView;
import drax.liuan.com.drax.visitor.VisitorListAdapter;

public class MainActivity extends Activity {
    private LinearLayout llDisplay;
    private LinearLayout llVipDisplayWrapper;
    private Button btnSpread, btnClose;
    private Button btnAdd1, btnAdd2, btnAdd3, btnAdd4;
    private Button btnSpreadVisitor, btnCloseVisitor;
    private Button btnStop;
    private Button btnAddVisitor1, btnAddVisitor2, btnAddVisitor3, btnAddVisitor5;

    private StaffClockInView staffClockInView;
    private RecyclerView recyclerViewVisitor;
    private PersonDisplayAnimHelper personDisplayAnimHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.llDisplay = findViewById(R.id.llDisplay);

        this.staffClockInView = findViewById(R.id.staffClockInView);
        this.recyclerViewVisitor = findViewById(R.id.recyclerViewVisitor);

        this.llVipDisplayWrapper = findViewById(R.id.llVipDisplayWrapper);

        this.btnSpread = findViewById(R.id.btnSpread);
        this.btnClose = findViewById(R.id.btnClose);
        this.btnAdd1 = findViewById(R.id.btnAdd);
        this.btnAdd2 = findViewById(R.id.btnAdd2);
        this.btnAdd3 = findViewById(R.id.btnAdd3);
        this.btnAdd4 = findViewById(R.id.btnAdd4);
        this.btnSpreadVisitor = findViewById(R.id.btnSpreadVisitor);
        this.btnCloseVisitor = findViewById(R.id.btnCloseVisitor);
        this.btnStop = findViewById(R.id.btnStop);
        this.btnAddVisitor1 = findViewById(R.id.btnAddVisitor1);
        this.btnAddVisitor2 = findViewById(R.id.btnAddVisitor2);
        this.btnAddVisitor3 = findViewById(R.id.btnAddVisitor3);
        this.btnAddVisitor5 = findViewById(R.id.btnAddVisitor5);

        this.personDisplayAnimHelper = new PersonDisplayAnimHelper(this.llDisplay);

        initButton();
        initVisitorRecyclerView();

        this.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                staffClockInView.addItem(new StaffClockInView.Person());

            }
        });

        this.btnAddVisitor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.btnAddVisitor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.btnAddVisitor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.btnAddVisitor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void initButton() {

        this.btnSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffClockInView.startSpreadAnim();

            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffClockInView.startCloseAnim();
            }
        });
        this.btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });

    }

    private void initVisitorRecyclerView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        final VisitorListAdapter visitorListAdapter = new VisitorListAdapter(this, list);
        this.recyclerViewVisitor.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewVisitor.setAdapter(visitorListAdapter);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.visitor_left_animation);
        animation.setStartOffset(180);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac.setDelay(1);
        this.recyclerViewVisitor.setLayoutAnimation(lac);

        final ObjectAnimator animator = ObjectAnimator.ofFloat(this.recyclerViewVisitor, "translationX", -700, 0);
        animator.setDuration(200);


        this.btnSpreadVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
                visitorListAdapter.notifyDataSetChanged();
                recyclerViewVisitor.scheduleLayoutAnimation();
            }
        });
        this.btnCloseVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.reverse();
            }
        });
    }


    private void initVipDisplay() {

    }
}
