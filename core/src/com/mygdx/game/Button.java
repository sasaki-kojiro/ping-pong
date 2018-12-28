package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    Texture texture;
    int x, y;
    boolean hasBeenClicked;

    Button(String textureName) {
        texture = new Texture(textureName);
    }

    void draw (SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    boolean isClicked () {
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (touchX >= x && touchX < x + texture.getWidth() && touchY >= y && touchY < y + texture.getHeight()) {
                return true;
            }
        }
        return false;
    }

    boolean isReleased () {
        if (isClicked()){
            hasBeenClicked = true;
        }
        if (hasBeenClicked && ! Gdx.input.isTouched()) {
            hasBeenClicked = false;
            return true;
        }
        return false;
    }

    void dispose () {
        texture.dispose();
    }
}
