package drax.liuan.com.drax.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class HexagonView extends View {
    private static final float COS_30 = (float) Math.cos(Math.toRadians(30));

    private Paint paintHexagonLine;
    private Paint paintHexagonPoint;
    private Paint paintExtensionLine;
    private Paint paintHexagonCenter;
    //六边形边长或称半径
    private int radiusHexagon;
    //六个圆点的半径
    private int radiusPoint;
    //中心实心六边形的边长或称半径
    private int radiusHexagonCenter;
    //六边形中心坐标X
    private int centerX;
    //六边形中心坐标Y
    private int centerY;
    //延长线长度
    private int extensionLineLength = 1000;
    //延长线起点
    private Point extensionLineStartPoint = new Point();
    //延长线终点
    private Point extensionLineEndPoint = new Point();
    //中心六边形轨迹
    private Path pathCenterHexagon;
    //是否画延长线
    private boolean isDrawExtensionLine;
    //是否展开
    private boolean isSpread;
    //顺时针旋转六边形动画
    private ValueAnimator animatorHexagon;
    //展开延长线动画
    private ValueAnimator animatorExtensionLine;

    private OnAnimStateChangeListener mOnAnimStateChangeListener = null;

    private ArrayList<HexagonPoint> hexagonPointList = new ArrayList<>();

    public HexagonView(Context context) {
        super(context);
        init();
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnAnimStateChangeListener(OnAnimStateChangeListener onAnimStateChangeListener) {
        this.mOnAnimStateChangeListener = onAnimStateChangeListener;
    }

    private void init() {
        paintHexagonLine = new Paint();
        paintHexagonLine.setColor(Color.RED);
        paintHexagonLine.setStyle(Paint.Style.STROKE);
        paintHexagonLine.setAntiAlias(true);

        paintHexagonPoint = new Paint();
        paintHexagonPoint.setColor(Color.RED);
        paintHexagonPoint.setStyle(Paint.Style.FILL);
        paintHexagonPoint.setAntiAlias(true);

        paintExtensionLine = new Paint();
        paintExtensionLine.setColor(Color.RED);
        paintExtensionLine.setStyle(Paint.Style.STROKE);
        paintExtensionLine.setAntiAlias(true);

        paintHexagonCenter = new Paint();
        paintHexagonCenter.setColor(Color.RED);
        paintHexagonCenter.setStyle(Paint.Style.FILL);
        paintHexagonCenter.setAntiAlias(true);

        pathCenterHexagon = new Path();

        initHexagonRotationAnimation();
        initExtensionLineAnimation();
    }

    /**
     * 旋转动画设定
     */
    private void initHexagonRotationAnimation() {
        animatorHexagon = ValueAnimator.ofFloat(0f, 360f);
        animatorHexagon.setDuration(700);
        animatorHexagon.setRepeatMode(ValueAnimator.RESTART);
        animatorHexagon.setInterpolator(new MyDecelerateAccelerateInterpolator());

        animatorHexagon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float radio = -animation.getAnimatedFraction() * 360;
                for (int i = 0; i < hexagonPointList.size(); i++) {
                    float radio4Point = radio + 60 * i;
                    HexagonPoint point = hexagonPointList.get(i);
                    point.x = (float) (radiusHexagon * Math.sin(Math.toRadians(radio4Point)));
                    point.y = (float) (radiusHexagon * Math.cos(Math.toRadians(radio4Point)));
                }
                invalidate();
            }
        });
        animatorHexagon.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isDrawExtensionLine = false;
                mOnAnimStateChangeListener.onSpreadAnimStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isSpread) {
                    animatorExtensionLine.start();
                } else {
                    mOnAnimStateChangeListener.onCloseAnimEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 延长线动画设定
     */
    private void initExtensionLineAnimation() {
        PointEvaluator pointEvaluator = new PointEvaluator();
        animatorExtensionLine = ValueAnimator.ofObject(
                pointEvaluator,
                extensionLineStartPoint,
                extensionLineEndPoint);
        animatorExtensionLine.setDuration(400);
        animatorExtensionLine.setInterpolator(new MyDecelerateAccelerateInterpolator());
        animatorExtensionLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                extensionLineEndPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animatorExtensionLine.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!isSpread) {
                    mOnAnimStateChangeListener.onCloseAnimStart();
                }
                isDrawExtensionLine = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isSpread) {
                    mOnAnimStateChangeListener.onSpreadAnimEnd(extensionLineStartPoint, extensionLineEndPoint);
                } else {
                    animatorHexagon.reverse();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void initHexagonPointList() {
        hexagonPointList.clear();

        HexagonPoint point1 = new HexagonPoint();
        point1.x = 0;
        point1.y = -radiusHexagon;
        hexagonPointList.add(point1);

        HexagonPoint point2 = new HexagonPoint();
        point2.x = radiusHexagon * COS_30;
        point2.y = -radiusHexagon / 2;
        hexagonPointList.add(point2);

        HexagonPoint point3 = new HexagonPoint();
        point3.x = radiusHexagon * COS_30;
        point3.y = radiusHexagon / 2;
        hexagonPointList.add(point3);

        HexagonPoint point4 = new HexagonPoint();
        point4.x = 0;
        point4.y = radiusHexagon;
        hexagonPointList.add(point4);

        HexagonPoint point5 = new HexagonPoint();
        point5.x = -radiusHexagon * COS_30;
        point5.y = radiusHexagon / 2;
        hexagonPointList.add(point5);

        HexagonPoint point6 = new HexagonPoint();
        point6.x = -radiusHexagon * COS_30;
        point6.y = -radiusHexagon / 2;
        hexagonPointList.add(point6);
    }

    private void initCenterHexagonPath() {
        pathCenterHexagon.moveTo(centerX, centerY - radiusHexagonCenter);
        pathCenterHexagon.lineTo(centerX + (int) (radiusHexagonCenter * COS_30), centerY - radiusHexagonCenter / 2);
        pathCenterHexagon.lineTo(centerX + (int) (radiusHexagonCenter * COS_30), centerY + radiusHexagonCenter / 2);
        pathCenterHexagon.lineTo(centerX, centerY + radiusHexagonCenter);
        pathCenterHexagon.lineTo(centerX - (int) (radiusHexagonCenter * COS_30), centerY + radiusHexagonCenter / 2);
        pathCenterHexagon.lineTo(centerX - (int) (radiusHexagonCenter * COS_30), centerY - radiusHexagonCenter / 2);
        pathCenterHexagon.close();
    }

    private void resetExtensionLine() {
        extensionLineStartPoint.x = (int) (centerX + hexagonPointList.get(1).x);
        extensionLineStartPoint.y = (int) (centerY + hexagonPointList.get(1).y);
        extensionLineEndPoint.x = extensionLineStartPoint.x + extensionLineLength;
        extensionLineEndPoint.y = extensionLineStartPoint.y;
    }

    public void startSpreadAnimation() {
        isSpread = true;
        animatorHexagon.start();
    }

    public void startCloseAnimation() {
        isSpread = false;
        animatorExtensionLine.reverse();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();

        if (width > height) {
            radiusHexagon = height / 2;

            paintHexagonLine.setStrokeWidth((float) (height * 0.01));
        } else {
            radiusHexagon = width / 2;
            paintHexagonLine.setStrokeWidth((float) (width * 0.01));
        }
        if (paintHexagonLine.getStrokeWidth() < 2) {
            paintHexagonLine.setStrokeWidth(2);
        }
        paintHexagonPoint.setStrokeWidth(paintHexagonLine.getStrokeWidth());
        paintExtensionLine.setStrokeWidth(paintHexagonLine.getStrokeWidth());

        radiusPoint = (int) (paintHexagonLine.getStrokeWidth() * 2);
        radiusHexagon -= (radiusPoint + paintHexagonLine.getStrokeWidth());
        radiusHexagonCenter = radiusHexagon / 4;

        centerX = radiusHexagon;
        centerY = height / 2;

        extensionLineLength = width - radiusHexagon * 2;

        initCenterHexagonPath();
        initHexagonPointList();
        resetExtensionLine();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < hexagonPointList.size(); i++) {
            float point1X = centerX + hexagonPointList.get(i).x;
            float point1Y = centerY + hexagonPointList.get(i).y;
            float point2X;
            float point2Y;
            if (i == hexagonPointList.size() - 1) {
                point2X = centerX + hexagonPointList.get(0).x;
                point2Y = centerY + hexagonPointList.get(0).y;
            } else {
                point2X = centerX + hexagonPointList.get(i + 1).x;
                point2Y = centerY + hexagonPointList.get(i + 1).y;
            }

            //画六个点
            canvas.drawCircle(point1X, point1Y, radiusPoint, paintHexagonPoint);
            //画边线
            canvas.drawLine(point1X, point1Y, point2X, point2Y, paintHexagonLine);
            //画中心
            canvas.drawPath(pathCenterHexagon, paintHexagonCenter);
        }
        if (isDrawExtensionLine) {
            //画延长线
            canvas.drawLine(
                    extensionLineStartPoint.x, extensionLineStartPoint.y,
                    extensionLineEndPoint.x, extensionLineEndPoint.y,
                    paintExtensionLine);
        }

    }

    public interface OnAnimStateChangeListener {
        void onSpreadAnimStart();

        void onSpreadAnimEnd(Point extensionLineStartPoint, Point extensionLineEndPoint);

        void onCloseAnimStart();

        void onCloseAnimEnd();
    }


    private class HexagonPoint {
        private float x;
        private float y;
    }
}
