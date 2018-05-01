package com.example.herma.imt3673_assignment1;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 *  An instance of square will be treated as obstacles
 *  that the player needs to avoid
 */

public class Rectangle {
    public float xPos;
    public float yPos;
    private float yVel;
    public int width;
    public int height;
    private int mod;
    public final Paint colour;



    public Rectangle(int x, int y, int wd, int ht, Paint col){
        this.xPos = x;
        this.yPos = y;
        this.yVel = 4.0f;
        this.width = wd;
        this.height = ht;
        this.colour = col;
        this.mod = 2;
    }

    public void update(float frameTime){
        this.yPos += yVel;
    }

    public void increaseSpeed(int spd){
        if(spd % mod == 0) {
            yVel += 0.3f;
        }
    }

    public void reset(int vw, int vh){
        this.xPos = (float)vw;
        this.yPos = (float)vh;

    }

    public void draw(Canvas canvas){
        canvas.drawRect(this.xPos, this.yPos, this.xPos + this.width, this. yPos + this.height, this.colour);
    }


}
