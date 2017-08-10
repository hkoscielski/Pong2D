package com.example.hubson.myapplication;

import android.content.Context;
import android.graphics.RectF;

public class Paddle {
    private RectF mRect;

    private float mLength;
    private float mHeight;

    private float mXCoord;
    private float mYCoord;

    private float mPaddleSpeed;

    private OrientationData orientationData;

    private int mScreenX;
    private int mScreenY;

    public Paddle(Context ctx, int x, int y){
        orientationData = new OrientationData(ctx);
        orientationData.register();
        mScreenX = x;
        mScreenY = y;

        mLength = mScreenX / 8;
        mHeight = mScreenY / 25;

        mXCoord = mScreenX / 2;
        mYCoord = mScreenY - 20;

        mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength, mYCoord + mHeight);

        mPaddleSpeed = mScreenX;
    }


    public RectF getRect(){
        return mRect;
    }


    public void update(long fps){

        int elapsedTime = (int) fps;
        if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
            float pitch = orientationData.getOrientation()[1];
            mPaddleSpeed = pitch * mScreenX / 100f;
        }

        mXCoord -= Math.abs(mPaddleSpeed * elapsedTime) > 5 ? mPaddleSpeed * elapsedTime : 0;


        if(mRect.left < 0) {
            mXCoord = 0;
        }

        if(mRect.right > mScreenX) {
            mXCoord = mScreenX - (mRect.right - mRect.left);
        }

        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }

    public void reset() {
        mXCoord = mScreenX / 2;
        mYCoord = mScreenY - 20;
        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }
}
