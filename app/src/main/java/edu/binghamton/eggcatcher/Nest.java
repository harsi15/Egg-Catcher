package edu.binghamton.eggcatcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Nest {
    Context context;
    Bitmap nest;
    int ox, oy;
    Random random;

    public Nest(Context context) {
        this.context = context;
        nest = BitmapFactory.decodeResource(context.getResources(), R.drawable.chicken);
        random = new Random();
        ox = random.nextInt(EggCatcher.screenWidth);
        oy = EggCatcher.screenHeight - nest.getHeight();
    }

    public Bitmap getNest(){
        return nest;
    }

    int getNestWidth(){
        return nest.getWidth();
    }
}
