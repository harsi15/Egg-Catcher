package edu.binghamton.eggcatcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Egg {
    Bitmap egg;
    Context context;
    int shx, shy;

    public Egg(Context context, int shx, int shy) {
        this.context = context;
        egg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.egg);
        this.shx = shx;
        this.shy = shy;
    }
    public Bitmap getEgg(){
        return egg;
    }
    public int getEggWidth() {
        return egg.getWidth();
    }
    public int getEggHeight() {
        return egg.getHeight();
    }
}
