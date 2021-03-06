package com.app.kent.animationtestv2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animView.startAnimation();
            }
        });
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        AnimatorSet animation = null;
        private float mDensity;

        public MyAnimationView(Context context) {
            super(context);

            mDensity = getContext().getResources().getDisplayMetrics().density;
            Log.e(TAG, "mDensity = " + mDensity); // 2.0

            ShapeHolder ball0 = addBall(50f, 125f);
            ShapeHolder ball1 = addBall(150f, 125f);
            ShapeHolder ball2 = addBall(250f, 125f);
            ShapeHolder ball3 = addBall(350f, 125f);
            ShapeHolder ball4 = addBall(450f, 125f);
//            ShapeHolder ball5 = addBall(550f, 25f);
//            ShapeHolder ball6 = addBall(650f, 25f);
        }

        private void createAnimation() {
            if (animation == null) {
                Log.e(TAG, "getHeight() = " + getHeight()); // 1022
                Log.e(TAG, "balls.get(0).getHeight() = " + balls.get(0).getHeight()); // 100.0


                /** 1 */
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(balls.get(0), "y",
                        125f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
                //ObjectAnimator anim2 = anim1.clone();
                //anim2.setTarget(balls.get(1)); //anim2 = anim1
                anim1.setInterpolator(new AnticipateInterpolator());
                anim1.setRepeatCount(3);
                anim1.addUpdateListener(this);

                /** 2 */
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(balls.get(1), "y",
                        125f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim2.setInterpolator(new LinearInterpolator());
                anim2.setInterpolator(new AnticipateInterpolator(0.5f));
                anim2.setRepeatCount(3);
                anim2.addUpdateListener(this);

                /** 3 */
                ObjectAnimator anim3 = ObjectAnimator.ofFloat(balls.get(2), "y",
                        125f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim3.setInterpolator(new AccelerateInterpolator());
                anim3.setInterpolator(new AnticipateInterpolator(0.8f));
                anim3.setRepeatCount(3);
                anim3.addUpdateListener(this);

                /** 4 */
                ObjectAnimator anim4 = ObjectAnimator.ofFloat(balls.get(3), "y",
                        125f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim4.setInterpolator(new DecelerateInterpolator());
                anim4.setInterpolator(new AnticipateInterpolator(1.0f));
                anim4.setRepeatCount(3);
                anim4.addUpdateListener(this);

                /** 5 */
                ObjectAnimator anim5 = ObjectAnimator.ofFloat(balls.get(4), "y",
                        125f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim5.setInterpolator(new AccelerateDecelerateInterpolator());
                anim5.setInterpolator(new AnticipateInterpolator(2f));
                anim5.setRepeatCount(3);
                anim5.addUpdateListener(this);
//
//                ObjectAnimator anim6 = ObjectAnimator.ofFloat(balls.get(5), "y",
//                        0f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim6.setInterpolator(new BounceInterpolator());
//                anim6.addUpdateListener(this);
//
//
//                ObjectAnimator anim7 = ObjectAnimator.ofFloat(balls.get(6), "y",
//                        0f, getHeight() - balls.get(0).getHeight()).setDuration(2000);
//                anim6.setInterpolator(new CycleInterpolator(null, null));
//                anim6.addUpdateListener(this);

//                ShapeHolder ball2 = balls.get(2);
//                ObjectAnimator animDown = ObjectAnimator.ofFloat(ball2, "y",
//                        0f, getHeight() - ball2.getHeight()).setDuration(500);
//                animDown.setInterpolator(new AccelerateInterpolator());
//
//                ObjectAnimator animUp = ObjectAnimator.ofFloat(ball2, "y",
//                        getHeight() - ball2.getHeight(), 0f).setDuration(500);
//                animUp.setInterpolator(new DecelerateInterpolator());
//
//                AnimatorSet s1 = new AnimatorSet();
//                s1.playSequentially(animDown, animUp);
//                animDown.addUpdateListener(this);
//                animUp.addUpdateListener(this);
//                AnimatorSet s2 = (AnimatorSet) s1.clone();
//                s2.setTarget(balls.get(3));
//
                animation = new AnimatorSet();
                animation.playTogether(anim1, anim2, anim3, anim4, anim5);
                //animation.playSequentially(s1, s2);
            }
        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(50f * mDensity, 50f * mDensity);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25f);
            shapeHolder.setY(y - 25f);

            int red = (int)(100 + Math.random() * 155);
            int green = (int)(100 + Math.random() * 155);
            int blue = (int)(100 + Math.random() * 155);

            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);

            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < balls.size(); ++i) {
                ShapeHolder shapeHolder = balls.get(i);
                canvas.save();
                canvas.translate(shapeHolder.getX(), shapeHolder.getY());
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
        }

        public void startAnimation() {
            createAnimation();
            animation.start();
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }

    }
}
