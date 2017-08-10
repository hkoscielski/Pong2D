package com.example.hubson.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread mGameThread = null;
    private SurfaceHolder mOurHolder;

    private volatile boolean mPlaying;
    private boolean mPaused = true;

    private Canvas mCanvas;
    private Paint mPaint;

    private long mFPS;

    private int mScreenX;
    private int mScreenY;

    private Paddle mPaddle;
    private Ball mBall;

    private int mScore = 0;
    private int mLives = 3;

    private Activity activity;

    public GameView(Context context, int x, int y) {
        super(context);

        mScreenX = x;
        mScreenY = y;

        mOurHolder = getHolder();
        mPaint = new Paint();
        activity = (Activity) context;

        mPaddle = new Paddle(context, mScreenX, mScreenY);
        mBall = new Ball(mScreenX, mScreenY);

        setupAndRestart();
    }

    public void setupAndRestart(){
        mBall.reset(mScreenX, mScreenY);
        mPaddle.reset();
        if(mLives == 0) {
            mScore = 0;
            mLives = 3;
        }
    }

    @Override
    public void run() {
        while (mPlaying) {
            long startFrameTime = System.currentTimeMillis();
            if(!mPaused)
                update();
            draw();
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1)
                mFPS = 1000 / timeThisFrame;
        }
    }

    public void update() {
        mPaddle.update(mFPS);
        mBall.update(mFPS);

        if(mPaddle.getRect().top <= mBall.getRect().bottom && mPaddle.getRect().right >= mBall.getRect().left && mPaddle.getRect().left <= mBall.getRect().right) {
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mPaddle.getRect().top - mFPS);
            mBall.increaseVelocity();
            mScore++;
        }

        if(mBall.getRect().bottom >= mScreenY) {
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mScreenY);
            mLives--;
            if(mLives == 0){
                mPaused = true;
                setupAndRestart();
            }
            else {
                mBall.reset(mScreenX, mScreenY);
                mPaddle.reset();
                mPaused = true;
            }
        }

        if(mBall.getRect().top <= 0) {
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mBall.getBallHeight());
        }

        if(mBall.getRect().left <= 0) {
            mBall.reverseXVelocity();
            mBall.clearObstacleX(0);
        }

        if(mBall.getRect().right >= mScreenX) {
            mBall.reverseXVelocity();
            mBall.clearObstacleX(mScreenX - mBall.getBallWidth());
        }
    }

    public void draw() {
        if (mOurHolder.getSurface().isValid()) {
            mCanvas = mOurHolder.lockCanvas();

            Bitmap bitmapBack = BitmapFactory.decodeResource(activity.getResources(), R.drawable.forest);
            bitmapBack = Bitmap.createScaledBitmap(bitmapBack, mScreenX, mScreenY, true);
            mCanvas.drawBitmap(bitmapBack, 0, 0, null);

            mPaint.setColor(Color.argb(255, 255, 255, 255));

            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.paddle);
            mCanvas.drawBitmap(bitmap, null, mPaddle.getRect(), mPaint);
            mCanvas.drawOval(mBall.getRect(), mPaint);

            mPaint.setTextSize(mScreenY * 0.07f);
            mCanvas.drawText(String.format("Score: %s   Lives: %s", mScore, mLives), mScreenX * 0.025f, mScreenY * 0.1f, mPaint);

            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(mPaused)
                    mPaused = false;
                else
                    mPaused = true;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
