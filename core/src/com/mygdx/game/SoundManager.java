package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class SoundManager {
    Sound bounceSound1, bounceSound2, bounceSound3, loseBallSound;
    Random random;
    SoundManager () {
        random = new Random();
    }

    public void loadSounds() {
        bounceSound1 = Gdx.audio.newSound(Gdx.files.internal("bounce1.ogg"));
        bounceSound2 = Gdx.audio.newSound(Gdx.files.internal("bounce2.ogg"));
        bounceSound3 = Gdx.audio.newSound(Gdx.files.internal("bounce3.ogg"));
        loseBallSound = Gdx.audio.newSound(Gdx.files.internal("lose_ball.ogg"));
    }

    public void dispose () {
        bounceSound1.dispose();
        bounceSound2.dispose();
        bounceSound3.dispose();
        loseBallSound.dispose();
    }

    public void playRandomBounceSound () {
        int randomNum = random.nextInt(3);
        if (randomNum == 0) {
            bounceSound1.play();
        } else if (randomNum == 1) {
            bounceSound2.play();
        }else if (randomNum == 2) {
            bounceSound3.play();
        }
    }
}
