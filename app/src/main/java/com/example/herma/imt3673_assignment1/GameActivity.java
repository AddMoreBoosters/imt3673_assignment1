package com.example.herma.imt3673_assignment1;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

/**
 * Example activity that contains a view that reads accelerometer sensor input and
 * translates a circle based on the changes.
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView = null;
    private Vibrator vb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        gameView = new GameView(this);

        //Set our content to a view, not like the traditional setting to a layout
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gameView.onSensorEvent(event);
        }
    }

    public class GameView extends View {

        private final Paint black;
        private final Paint cyan;
        private int viewWidth;
        private int viewHeight;
        private Random rand;
        private Ball ball;
        private Rectangle rect;
        private boolean firstGame;


        public GameView(Context context) {
            super(context);
            viewWidth = 400;
            viewHeight = 400;

            black = new Paint();
            black.setColor(Color.RED);
            cyan = new Paint();
            cyan.setColor(Color.CYAN);
            rand = new Random();
            firstGame = true;
            ball = new Ball(viewWidth/2, viewHeight/2, 50, black);
            rect = new Rectangle(viewWidth/2, 0, 50, 50, cyan);


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
            ball.x = viewWidth/2;
            ball.y = viewHeight/2;
        }



        // HEART OF THE GAME
        public void onSensorEvent (SensorEvent event) {

            ball.update((int) event.values[0], (int) event.values[1] , viewWidth, viewHeight);

            // the game crashes here on first frame. Hence !firstGame
            if(rect.yPos > viewHeight && !firstGame) {
                int n = rand.nextInt(viewWidth - 50 + 1);
                rect.reset(n, 0);
                rect.increaseSpeed(2);
                ball.increaseScore();
            }

            if(ballCollideBox(ball, rect)){
                ball.decreaseLives();
                vb.vibrate(200);

                if(ball.isDead())
                {
                    finish();
                }
                else
                {
                    int n = rand.nextInt(viewWidth - 50 + 1);
                    rect.reset(n, 0);
                    ball.reset(viewWidth/2, viewHeight-ball.radius);
                }
            }

            rect.update(0.66f);

            firstGame = false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            rect.draw(canvas);
            ball.draw(canvas);
            invalidate();
        }


        // collision check (CIRCULAR)
        private boolean ballCollideBox(Ball b, Rectangle r){
            int closeX = b.x;
            int closeY = b.y;

            // where are the closest X and Y on the rect to the ball?
            if(b.x < r.xPos)         closeX = (int)r.xPos;
            if(b.x > r.xPos+r.width) closeX = (int)(r.xPos + r.width);
            if(b.y < r.yPos)         closeY = (int)r.yPos;
            if(b.y > r.yPos+r.height) closeY = (int)(r.yPos + r.height);

            double dis = Math.sqrt( ( (closeX - b.x)*(closeX - b.x) ) +
                                    ( (closeY - b.y)*(closeY - b.y) ) );
            if(dis < b.radius) return true;

            return false;
        }
    }
}