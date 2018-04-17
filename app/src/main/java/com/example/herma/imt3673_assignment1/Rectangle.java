package com.example.herma.imt3673_assignment1;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 *  An instance of square will be treated as obstacles
 *  that the player needs to avoid
 */

public class Rectangle {
    public int xPos;
    public int yPos;
    public final int yVel;
    public int width;
    public int height;
    public final Paint colour;

    public Rectangle(int x, int y, int wd, int ht, Paint col){
        this.xPos = x;
        this.yPos = y;
        this.yVel = 1;
        this.width = wd;
        this.height = ht;
        this.colour = col;
    }

    public void update(float frameTime){
        this.yPos += yVel;
    }

    public void reset(){
        this.xPos = 0;
        // this.yPos = rand screen height
    }

    public void draw(Canvas canvas){
        canvas.drawRect(this.xPos, this.yPos, this.xPos + this.width, this. yPos + this.height, this.colour);
    }


}
