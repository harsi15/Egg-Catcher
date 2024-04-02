package edu.binghamton.eggcatcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bomb {
    Bitmap bomb;
    Context context;
    int shx, shy;

    public Bomb(Context context, int shx, int shy) {
        this.context = context;
        bomb = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bomb);
        this.shx = shx;
        this.shy = shy;
    }
    public Bitmap getBomb(){
        return bomb;
    }
    public int getBombWidth() {
        return bomb.getWidth();
    }
    public int getBombHeight() {
        return bomb.getHeight();
    }
}
