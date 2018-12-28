package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	private SoundManager soundManager;
	Ball ball;
	Paddle paddle;
	int score;
	final int CATCH_BALL_BONUS = 100;
	BitmapFont font;
	final int INITIAL_LIVES_COUNT = 1;
	int livesCount = INITIAL_LIVES_COUNT;
	Texture gameOverTexture;
	boolean isGameOver;
	Button closeBtn;
	Button replayBtn;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(5);
		ball = new Ball();
		paddle = new Paddle();
		paddle.center();
		ball.restart(paddle);
		soundManager = new SoundManager();
		soundManager.loadSounds();
		closeBtn = new Button("close_btn.png");
		closeBtn.y = 10;
		closeBtn.x = Gdx.graphics.getWidth() - closeBtn.texture.getWidth();
		replayBtn = new Button ("replay_btn.png");
		replayBtn.y = 10;
		gameOverTexture = new Texture("game_over_logo.jpg");
	}


	@Override
	public void render () {
		//input block

		checkIfButtonsAreClicked();

		//logic block
		if (! isGameOver) {
			paddle.move();
			ball.move(paddle);
		}
		collideBall();

		//if we lose the ball
		if (ball.y + ball.texture.getHeight() < 0) {
			ball.restart(paddle);
			livesCount--;
			soundManager.loseBallSound.play();
			if (livesCount == 0) {
				isGameOver = true;
			}
		}

		draw();
	}

	private void checkIfButtonsAreClicked() {
		if (isGameOver) {
			if (closeBtn.isClicked()) {
				System.exit(0);
			}
			if (replayBtn.isReleased()) {
				restartGame();
			}
		}
	}

	private void restartGame() {
		score = 0;
		livesCount = INITIAL_LIVES_COUNT;
		isGameOver = false;
		paddle.center();
		ball.restart(paddle);
		ball.velocityY = ball.INITIAL_BALL_SPEED;
		ball.velocityX = ball.INITIAL_BALL_SPEED;
	}

	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		font.draw(batch, "Score: " + score + "   Lives " + livesCount, 0, Gdx.graphics.getHeight());
		ball.draw(batch);
		paddle.draw(batch);
		if (isGameOver) {
			batch.draw(gameOverTexture, (Gdx.graphics.getWidth() - gameOverTexture.getWidth()) / 2,
					(Gdx.graphics.getHeight() - gameOverTexture.getHeight()) / 2);
			closeBtn.draw(batch);
			replayBtn.draw(batch);
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		ball.dispose();
		paddle.dispose();
		closeBtn.dispose();
		replayBtn.dispose();
		soundManager.dispose();
		gameOverTexture.dispose();
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
				score += CATCH_BALL_BONUS * Math.abs(ball.velocityX);
			}
		}

		//if ball collides with the left side of the paddle
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
		//if ball collides with the right side of the paddle
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
	}


}
