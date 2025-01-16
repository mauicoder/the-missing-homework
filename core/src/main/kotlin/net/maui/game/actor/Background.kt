package net.maui.game.actor

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage

class Background(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {
    val scienceLab: Animation<TextureRegion>
    val classroom: Animation<TextureRegion>
    val hallway: Animation<TextureRegion>
    val library: Animation<TextureRegion>

    init {
        hallway = loadTexture("assets/bg-hallway.jpg");
        classroom = loadTexture("assets/bg-classroom.jpg");
        scienceLab = loadTexture("assets/bg-science-lab.jpg");
        library = loadTexture("assets/bg-library.jpg");
        setSize(800f,600f);
    }

}
