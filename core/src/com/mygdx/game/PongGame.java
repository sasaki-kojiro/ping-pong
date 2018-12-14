package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Font;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	private SoundManager soundManager;
	Ball ball;
	Paddle paddle;
	int score;
	final int CATCH_BALL_BONUS = 100;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(5);
		ball = new Ball();
		paddle = new Paddle();
		paddle.x = (Gdx.graphics.getWidth() - paddle.texture.getWidth()) / 2;
		ball.restart(paddle);
		soundManager = new SoundManager();
		soundManager.loadSounds();
	}


	@Override
	public void render () {
		//input block
		paddle.move();

		//logic block
		ball.ballStartFrameCounter++;
		ball.move(paddle);

		collideBall();

		//if we lose the ball
		if (ball.y + ball.texture.getHeight() < 0) {
			ball.restart(paddle);
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "Score: " + score + "   VelocityX " + ball.velocityX, 0, Gdx.graphics.getHeight());
		ball.draw(batch);
		paddle.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		ball.dispose();
		paddle.dispose();
		soundManager.dispose();
	}

	private void collideBall () {
		//if ball collides with right edge
		if (ball.x >= Gdx.graphics.getWidth() - ball.texture.getWidth()) {
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}

		//if ball collides with the top
		if (ball.y >= Gdx.graphics.getHeight() - ball.texture.getHeight()) {
			ball.velocityY = -ball.velocityY;
			soundManager.playRandomBounceSound();
		}

		//if ball collides with the left edge
		if (ball.x < 0) {
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}

		//if ball collides with the top of the paddle
		if (ball.x + ball.texture.getWidth() / 2 >= paddle.x &&
				ball.x < paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2) {
			if (ball.y < paddle.y + paddle.texture.getHeight() && ball.y > 0) {
				ball.velocityY = -ball.velocityY;
				soundManager.playRandomBounceSound();
				score += CATCH_BALL_BONUS;
			}
		}
	}


}
