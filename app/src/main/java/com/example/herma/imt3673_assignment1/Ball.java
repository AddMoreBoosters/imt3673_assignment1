package com.example.herma.imt3673_assignment1;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by askel on 06/03/2018.
 */

public class Ball {
    public int x;
    public int y;
    private int score;
    private int lives;
    public final int radius;
    private final Paint colour;


    //  Instantiate the ball properties
    public Ball(int x, int y, int rad, Paint col){
        this.x = x;
        this.y = y;
        this.radius = rad;
        this.colour = col;
        this.score = 0;
        this.lives = 3;
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, colour);
    }

    public void update(int xEv, int yEv, int viewWidth, int viewHeight){
        x -= xEv;
        y += yEv;

        //Make sure we do not draw outside the bounds of the view.
        //So the max values we can draw to are the bounds + the size of the circle
        if (x <= radius) {
            x = radius;
        }

        if (x >= viewWidth - radius) {
            x = viewWidth - radius;
        }

        if (y <= radius) {
            y = radius;
        }

        if (y >= viewHeight - radius) {
            y = viewHeight - radius;
        }
    }

    public void increaseScore(){
        score++;
    }

    public void decreaseLives(){
        if(lives > 0) {
            lives--;
        }
    }

    public boolean isDead()
    {
        return (lives == 0);
    }

    public int getScore(){ return score; }

    public void reset(int x, int y){
        this.x = x;
        this.y = y;
    }
}
