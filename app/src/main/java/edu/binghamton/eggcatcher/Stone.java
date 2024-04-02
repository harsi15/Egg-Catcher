package edu.binghamton.eggcatcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Stone {
    Bitmap stone;
    Context context;
    int shx, shy;

    public Stone(Context context, int shx, int shy) {
        this.context = context;
        stone = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.stone);
        this.shx = shx;
        this.shy = shy;
    }
    public Bitmap getStone(){
        return stone;
    }
    public int getStoneWidth() {
        return stone.getWidth();
    }
    public int getStoneHeight() {
        return stone.getHeight();
    }
}
