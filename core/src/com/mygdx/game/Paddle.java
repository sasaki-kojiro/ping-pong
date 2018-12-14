package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
    Texture texture;

    int x, y = 10;

    Paddle () {
        texture = new Texture("paddle.bmp");
    }

    void draw (SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    void move () {
        if (Gdx.input.isTouched()) {
            x = Gdx.input.getX() - texture.getWidth() / 2;
            //preventing the paddle from moving past the left wall
            if (x < 0) {
                x = 0;
            }
            //preventing the paddle from moving past the right wall
            if (x > Gdx.graphics.getWidth() - texture.getWidth()) {
                x = Gdx.graphics.getWidth() - texture.getWidth();
            }
        }
    }
    void dispose () {
        texture.dispose();
    }
}
