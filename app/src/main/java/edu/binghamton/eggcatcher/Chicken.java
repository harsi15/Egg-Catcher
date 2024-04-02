package edu.binghamton.eggcatcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Chicken {
    Context context;
    Bitmap chicken;
    int ex, ey;
    int velocity;
    Random random;

    public Chicken(Context context) {
        this.context = context;
        chicken = BitmapFactory.decodeResource(context.getResources(), R.drawable.nest);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        velocity = 14 + random.nextInt(10);
    }

    public Bitmap getChicken(){
        return chicken;
    }

    int getChickenWidth(){
        return chicken.getWidth();
    }

    int getChickenHeight(){
        return chicken.getHeight();
    }
}
