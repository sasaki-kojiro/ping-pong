package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Texture texture;
    int x, y;
    int velocityX = 10 , velocityY = 10;
    final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;
    int ballStartFrameCounter;
    int ballFlyFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_SPEEDING_UP_BALL = 100;


    public Ball() {
        texture = new Texture("ball_small.png");
    }

    void move(Paddle paddle) {
        //if the ball already flies
        if (ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START) {
            x += velocityX;
            y += velocityY;
            ballFlyFrameCounter++;
            speedUpBallIfNeeded();
        } else {    // if the ball doesn't fly yet it moves around with the paddle
            x = paddle.x + paddle.texture.getWidth() / 2 - texture.getHeight() / 2;
        }
    }

    private void speedUpBallIfNeeded() {
        if (ballFlyFrameCounter == FRAMES_TO_WAIT_BEFORE_SPEEDING_UP_BALL) {
            if (velocityX >= 0) {
                velocityX++;
            } else {
                velocityX--;
            }
            if (velocityY >= 0) {
                velocityY++;
            } else {
                velocityY--;
            }
            ballFlyFrameCounter = 0;
        }
    }

    void restart(Paddle paddle) {
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getHeight() / 2;
        y = paddle.y + paddle.texture.getHeight();
        ballStartFrameCounter = 0;
    }

    void dispose () {
        texture.dispose();
    }

    void draw (SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}


//only fields can be outside of methods
