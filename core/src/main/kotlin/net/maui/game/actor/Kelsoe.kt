package net.maui.game.actor

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage

class Kelsoe(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {
    val normal: Animation<TextureRegion>
    internal val sad: Animation<TextureRegion>
    private val lookLeft: Animation<TextureRegion>
    internal val lookRight: Animation<TextureRegion>

    init {
        normal = loadTexture("assets/kelsoe-normal.png")
        sad = loadTexture("assets/kelsoe-sad.png")
        lookLeft = loadTexture("assets/kelsoe-look-left.png")
        lookRight = loadTexture("assets/kelsoe-look-right.png")
    }
}
