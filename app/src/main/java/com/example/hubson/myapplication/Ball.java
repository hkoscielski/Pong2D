package com.example.hubson.myapplication;

import android.graphics.RectF;

public class Ball {
    private RectF mRect;
    private float mStartVelocity;
    private float mXVelocity;
    private float mYVelocity;
    private float mBallWidth;
    private float mBallHeight;

    public Ball(int screenX, int screenY) {
        mBallWidth = screenX / 50;
        mBallHeight = mBallWidth;

        mYVelocity = - screenX / 4;
        mXVelocity = mYVelocity;
        mStartVelocity = mXVelocity;

        mRect = new RectF();
    }

    public RectF getRect(){
        return mRect;
    }

    public void update(long fps){
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);
        mRect.right = mRect.left + mBallWidth;
        mRect.bottom = mRect.top - mBallHeight;
    }

    public void reverseYVelocity(){
        mYVelocity = -mYVelocity;
    }

    public void reverseXVelocity(){
        mXVelocity = -mXVelocity;
    }

    public void increaseVelocity(){
        mXVelocity = mXVelocity + mXVelocity / 20;
        mYVelocity = mYVelocity + mYVelocity / 20;
    }

    public void clearObstacleY(float y){
        mRect.bottom = y;
        mRect.top = y - mBallHeight;
    }

    public void clearObstacleX(float x){
        mRect.left = x;
        mRect.right = x + mBallWidth;
    }

    public void reset(int x, int y){
        mRect.left = x / 2;
        mRect.top = y - 20;
        mRect.right = x / 2 + mBallWidth;
        mRect.bottom = y - 20 - mBallHeight;
        mXVelocity = mStartVelocity;
        mYVelocity = mStartVelocity;
    }

    public float getBallHeight() {
        return mBallHeight;
    }

    public float getBallWidth() {
        return mBallWidth;
    }
}