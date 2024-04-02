package edu.binghamton.eggcatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class EggCatcher extends View {
    Context context;
    Bitmap background;
    Handler handler;
    Bitmap lifeImage;

    int life = 3;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    Paint scorePaint;
    int TEXT_SIZE = 60;
    boolean paused = false;
    Nest nest;
    Chicken chicken;
    Random random;
    ArrayList<Egg> chickenEggs;
    ArrayList<Stone> stones;
    ArrayList<Bomb> bombs;
    boolean chickenEggAction = false;

    boolean dropEggNext = true;
    boolean dropStoneNext = false;

    boolean dropBombNext = false;

    boolean stoneAction = false;

    boolean bombAction = false;
    private Handler delayHandler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           invalidate();
        }
    };


    public EggCatcher(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        chickenEggs = new ArrayList<>();
        stones = new ArrayList<>();
        bombs = new ArrayList<>();
        nest = new Nest(context);
        chicken = new Chicken(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.farm4);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.lifenest1);
        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float chickenSpeedMultiplier = 1.0f;
        float eggDropSpeedMultiplier = 1.0f;
        float stoneDropSpeedMultiplier = 1.0f;

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("  Score: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=life; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }
        if(points >= 35){
            life++;
        }
        if(life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        if(points >= 20 && points < 40) {
            chickenSpeedMultiplier = 1.2f;
            eggDropSpeedMultiplier = 1.5f;
            stoneDropSpeedMultiplier = 1.2f;
        } else if (points >= 40 && points < 60) {
            chickenSpeedMultiplier = 1.5f;
            eggDropSpeedMultiplier = 2.0f;
            stoneDropSpeedMultiplier = 1.5f;
        } else if (points >= 60 && points < 80) {
            chickenSpeedMultiplier = 1.5f;
            eggDropSpeedMultiplier = 2.5f;
            stoneDropSpeedMultiplier = 2.0f;
        }
        int numChickens = 1;
        for (int i = 0; i < numChickens; i++) {
            //chicken.ex = screenWidth / (numChickens + 1) * (i + 1);
            chicken.ex += chicken.velocity * chickenSpeedMultiplier;
            if(chicken.ex + chicken.getChickenWidth() >= screenWidth){
                chicken.velocity *= -1;
            }
            if(chicken.ex <= 0){
                chicken.velocity *= -1;
            }
            canvas.drawBitmap(chicken.getChicken(), chicken.ex, chicken.ey, null);
        }


    if(dropEggNext) {
        if (chickenEggAction == false) {
            if (chicken.ex >= 200 + random.nextInt(400)) {
                dropEgg();
                //dropEggNext = false;
                dropStoneNext = true;

            }
            if (chicken.ex >= 400 + random.nextInt(800)) {
                //dropEgg();
                //dropEggNext = false;
                //dropStoneNext = true;
                dropBombNext  = true;
            } else {
                dropEgg();
                //dropEggNext = false;
                //dropStoneNext = true;
            }
        }
    }
    if(dropStoneNext){
        if (stoneAction == false) {
            if (chicken.ex >= 200 + random.nextInt(400)) {
                //dropStone();
                dropStoneNext = false;
                //dropEggNext = true;
                dropBombNext = true;
            }
            if (chicken.ex >= 400 + random.nextInt(800)) {
                dropStone();
                dropStoneNext = false;
                dropEggNext = true;
            } else {
                dropStone();
                dropStoneNext = false;
                dropEggNext = true;
                //dropBombNext = true;

            }
        }
    }
        if(dropBombNext){
            if (bombAction == false) {
                if (chicken.ex >= 200 + random.nextInt(400)) {
                    //dropBomb();
                    dropBombNext = false;
                    dropEggNext = true;
                }
                if (chicken.ex >= 400 + random.nextInt(800)) {
                    //dropBomb();
                    dropBombNext = false;
                    dropEggNext = true;
                } else {
                    dropBomb();
                    dropBombNext = false;
                    dropEggNext = true;
                }
            }
        }


        //canvas.drawBitmap(chicken.getChicken(), chicken.ex, chicken.ey, null);
        if(nest.ox > screenWidth - nest.getNestWidth()){
            nest.ox = screenWidth - nest.getNestWidth();
        }else if(nest.ox < 0){
            nest.ox = 0;
        }
        canvas.drawBitmap(nest.getNest(), nest.ox, nest.oy, null);

        for(int i=0; i < chickenEggs.size(); i++){
            chickenEggs.get(i).shy += 15 * eggDropSpeedMultiplier;
            canvas.drawBitmap(chickenEggs.get(i).getEgg(), chickenEggs.get(i).shx, chickenEggs.get(i).shy, null);
            if((chickenEggs.get(i).shx >= nest.ox)
                && chickenEggs.get(i).shx <= nest.ox + nest.getNestWidth()
                && chickenEggs.get(i).shy >= nest.oy
                && chickenEggs.get(i).shy <= screenHeight){
                points++;
                chickenEggs.remove(i);
            }else if(chickenEggs.get(i).shy >= screenHeight){
                life--;
                chickenEggs.remove(i);
            }
            if(chickenEggs.size() < 1){
                chickenEggAction = false;
            }
        }
        for(int i=0; i < stones.size(); i++){
            stones.get(i).shy += 10 * stoneDropSpeedMultiplier;
            canvas.drawBitmap(stones.get(i).getStone(), stones.get(i).shx, stones.get(i).shy, null);
            if((stones.get(i).shx >= nest.ox)
                    && stones.get(i).shx <= nest.ox + nest.getNestWidth()
                    && stones.get(i).shy >= nest.oy
                    && stones.get(i).shy <= screenHeight){
                life--;
                stones.remove(i);
            }else if(stones.get(i).shy >= screenHeight){
                stones.remove(i);
            }
            if(stones.size() < 1){
                stoneAction = false;
            }
        }
        for(int i=0; i < bombs.size(); i++){
            bombs.get(i).shy += 8 * stoneDropSpeedMultiplier;
            canvas.drawBitmap(bombs.get(i).getBomb(), bombs.get(i).shx, bombs.get(i).shy, null);
            if((bombs.get(i).shx >= nest.ox)
                    && bombs.get(i).shx <= nest.ox + nest.getNestWidth()
                    && bombs.get(i).shy >= nest.oy
                    && bombs.get(i).shy <= screenHeight){
                life = 0;
                bombs.remove(i);
            }else if(bombs.get(i).shy >= screenHeight){
                bombs.remove(i);
            }
            if(bombs.size() < 1){
                bombAction = false;
            }
        }
        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            nest.ox = touchX;
        }
       if(event.getAction() == MotionEvent.ACTION_MOVE){
            nest.ox = touchX;
        }
        return true;
    }

    private void dropEgg() {
        Egg chickenEgg = new Egg(context, chicken.ex + chicken.getChickenWidth() / 2, chicken.ey);
        chickenEggs.add(chickenEgg);
        chickenEggAction = true;
    }
    private void dropStone() {
        Stone stone = new Stone(context, chicken.ex + chicken.getChickenWidth() / 2, chicken.ey);
        stones.add(stone);
        stoneAction = true;
    }

    private void dropBomb() {
        Bomb bomb = new Bomb(context, chicken.ex + chicken.getChickenWidth() / 2, chicken.ey);
        bombs.add(bomb);
        bombAction = true;
    }
}
